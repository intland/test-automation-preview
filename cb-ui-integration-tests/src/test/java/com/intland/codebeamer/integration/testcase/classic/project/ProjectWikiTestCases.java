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

package com.intland.codebeamer.integration.testcase.classic.project;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.project.child.wiki.component.ProjectWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

import net.datafaker.Faker;

@Test(groups = "ProjectWikiTestCases")
public class ProjectWikiTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiService().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {

	}

	@Test(description = "User is able to create a new project wiki page")
	public void createNewProjectWikiPage() {
		// Given
		Faker faker = new Faker();
		Project project = getProjectApiService().createProjectFromTemplate();

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.getDashboardActionbarComponent()
					.createNewWiki()
					.wikiNewChildDialogFormComponent(form -> form.pageName(faker.book().title()))
					.save()
					.redirectedToProjectWikiPage();
		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "User is able to create a new project dashboard page")
	public void createNewProjectDashboardPage() {
		// Given
		Faker faker = new Faker();
		Project project = getProjectApiService().createProjectFromTemplate();

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.getDashboardActionbarComponent()
					.createNewDashBoard()
					.dashboardNewChildDialogFormComponent(form -> form.pageName(faker.book().title()))
					.save()
					.redirectedToProjectDashboardPage();
		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "User is able to create a new project wiki page with a child wiki page")
	public void createNewUserWikiPageWithAChild() {
		// Given
		Faker faker = new Faker();
		Project project = getProjectApiService().createProjectFromTemplate();
		String newProjectWikiName1 = faker.book().title();
		String newProjectWikiName2 = faker.book().title();

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.getDashboardActionbarComponent()
					.createNewWiki()
					.wikiNewChildDialogFormComponent(form -> form.pageName(newProjectWikiName1))
					.save()
					.redirectedToProjectWikiPage()
					.getActionbarComponent()
					.createNewWiki()
					.wikiNewChildDialogFormComponent(form -> form.pageName(newProjectWikiName2))
					.save()
					.redirectedToProjectWikiPage();

		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "Select child project wiki")
	public void selectChildProjectWiki() {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();
		String parentDashboardItemName = "Requirements Dashboard";
		String childDashboardItemName = "Requirements Guide";

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.applyProjectTreeComponent(c ->
							c.selectChildTreeItemByName(parentDashboardItemName, childDashboardItemName))
					.applyProjectTreeComponent(c -> c.assertThat().treeItemExistsByName(childDashboardItemName));

		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "User is not able to create a new project wiki page without filling out required fields")
	public void UserIsAbleToSeeAnErrorAboutRequiredField() {
		Project project = getProjectApiService().createProjectFromTemplate();

		getClassicCodebeamerApplication(activeUser)
				.visitProjectLandingPage(project)
				.getDashboardActionbarComponent().createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(StringUtils.EMPTY))
				.save()
				.redirectedToWikiNewChildDialog()
				.assertWikiNewChildDialogFormComponent(ProjectWikiNewChildDialogFormAssertions::isPageNameEmpty);
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(activeUser);
	}

}