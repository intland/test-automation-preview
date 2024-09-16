/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.projectbrowser;

import static org.testng.Assert.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategory;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategoryApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "ProjectBrowserTestCases")
public class ProjectBrowserCategoryTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToClassicUI(activeUser);
	}
	
	@Override
	protected void cleanUpDataAfterClass() throws Exception {

	}
	
	@Test(description = "User is able to create a new category")
	public void userIsAbleToCreateNewCategory() {
		// Given
		String categoryName = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
					.categoryListComponent(c -> c.createCategory(categoryName))
					.categoryListComponent(c -> c.assertThat().hasCategoryByName(categoryName))
					.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
		} finally {
			Optional<ProjectCategory> categoryOptional = getProjectCategoryService().findCategoryByName(categoryName);
			assertTrue(categoryOptional.isPresent(), "Category should be exist"); 
			getProjectCategoryService().deleteCategory(categoryOptional.get());
		}
	}

	@TestCase(link = "Component test", expectedHttpErrors = HttpStatus.BAD_REQUEST)
	@Test(description = "User is able to create a new category")
	public void userIsNotAbleToCreateNewCategoryIfAlreadyExists() throws Exception {
		// Given
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
					.categoryListComponent(c -> c.createCategory(existingCategory.name()))
					.categoryListComponent(c -> c.assertThat().hasCategoryByName(existingCategory.name()))
					.overlayMessageBoxComponent(c -> c.assertThat().hasError());
		} finally {
			getProjectCategoryService().deleteCategory(existingCategory);
		}
	}

	@Test(description = "User is able to delete a category")
	public void userIsAbleToDeleteCategory() throws Exception {
		// Given
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.categoryListComponent(c -> c.deleteCategory(existingCategory.name()))
				.categoryListComponent(c -> c.assertThat().hasNoCategoryByName(existingCategory.name()))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
		
		assertTrue(getProjectCategoryService().findCategoryByName(existingCategory.name()).isEmpty(), "Category should not be exist");
	}

	@Test(description = "User is able to drag and drop project to a category")
	public void userIsAbleToDragAndDropProjectToCategory() throws Exception {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		try {
			// When / Then
			getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.projectListComponent((p, f) -> f.dragAndDropProjectTo(project.name(), p.getCategoryListComponent().getCategoryByName(existingCategory.name())))
				.projectListComponent(c -> c.assertThat().isProjectPartOfCategory(project.name(), existingCategory.name()));
	
		} finally {
			getProjectApiService().deleteProject(project.id());
			getProjectCategoryService().deleteCategory(existingCategory);
		}
		
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(activeUser);
	}
	
	private ProjectCategoryApiService getProjectCategoryService() {
		return getDataManagerService().getProjectCategoryApiService(activeUser);
	}

}
