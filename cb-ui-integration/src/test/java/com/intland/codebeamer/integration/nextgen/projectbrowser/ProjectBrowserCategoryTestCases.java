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

package com.intland.codebeamer.integration.nextgen.projectbrowser;

import static org.testng.Assert.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategory;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategoryApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationNextgenNGTests;

@Test(groups = "ProjectBrowserTestCases")
public class ProjectBrowserCategoryTestCases extends AbstractIntegrationNextgenNGTests {

	private User activeUser;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {	
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToNextgenUI(activeUser);
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
			getNextgenCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.getCategoryListComponent().createNewCategory()
				.projectBrowserNewCategoryFormComponent(form -> form.createCategory(categoryName))
				.save()
				.redirectedToProjectBrowserPage()
				.categoryListComponent(c -> c.assertThat().hasCategoryByName(categoryName))
				.toastComponent(c -> c.assertThat().hasSuccess());
		} finally {
			Optional<ProjectCategory> categoryOptional = getProjectCategoryService().findCategoryByName(categoryName);
			assertTrue(categoryOptional.isPresent(), "Category('%s') should be exist".formatted(categoryName)); 
			getProjectCategoryService().deleteCategory(categoryOptional.get());
		}
	}

	@Test(description = "User is not able to create a new category if it is already exists")
	public void userIsNotAbleToCreateNewCategoryIfAlreadyExists() throws Exception {
		// Given
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		// When / Then
		try {
			getNextgenCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.getCategoryListComponent().createNewCategory()
				.projectBrowserNewCategoryFormComponent(form -> form.createCategory(existingCategory.name()))
				.save()
				.redirectedToProjectBrowserPage()
				.toastComponent(c -> c.assertThat().hasError());
		} finally {
			getProjectCategoryService().deleteCategory(existingCategory);
		}
	}

	@Test(description = "User is able to delete a category")
	public void userIsAbleToDeleteCategory() throws Exception {
		// Given
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		// When / Then
		getNextgenCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.categoryListComponent(c -> c.deleteCategory(existingCategory.name()))
				.categoryListComponent(c -> c.assertThat().hasNoCategoryByName(existingCategory.name()))
				.toastComponent(c -> c.assertThat().hasSuccess());
		
		assertTrue(getProjectCategoryService().findCategoryByName(existingCategory.name()).isEmpty(), "Category should not be exist");
	}

	@Test(description = "User is able to drag and drop project to a category")
	public void userIsAbleToDragAndDropProjectToCategory() throws Exception {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();
		ProjectCategory existingCategory = getProjectCategoryService().createNewCategory();
		
		try {
			// When / Then
			getNextgenCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.projectListComponent((p, f) -> {
					p.getCategoryListComponent().showAllCategories();
					f.dragAndDropProjectTo(project.name(), p.getCategoryListComponent().getCategoryByName(existingCategory.name()));
				})
				.toastComponent(c -> c.assertThat().hasSuccess())
				.projectListComponent(c -> c.assertThat().isProjectPartOfCategory(project.name(), existingCategory.name()));
	
		} finally {
			getProjectApiService().deleteProject(project.id());
			getProjectCategoryService().deleteCategory(existingCategory);
		}
		
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(this.activeUser);
	}
	
	private ProjectCategoryApiService getProjectCategoryService() {
		return getDataManagerService().getProjectCategoryApiService(this.activeUser);
	}

}
