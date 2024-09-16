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

package com.intland.codebeamer.integration.classic.page.project;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.project.child.wiki.component.ProjectWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectWikiTestCases")
public class ProjectWikiTestCases extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to create a new project wiki page")
	public void createNewProjectWikiPage() {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.getDashboardActionbarComponent()
					.createNewWiki()
					.wikiNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
					.save()
					.redirectedToProjectWikiPage();
		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "User is able to create a new project dashboard page")
	public void createNewProjectDashboardPage() {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();

		// When / Then
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectLandingPage(project)
					.getDashboardActionbarComponent()
					.createNewDashBoard()
					.dashboardNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
					.save()
					.redirectedToProjectDashboardPage();
		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	@Test(description = "User is able to create a new project wiki page with a child wiki page")
	public void createNewUserWikiPageWithAChild() {
		// Given
		Project project = getProjectApiService().createProjectFromTemplate();
		String newProjectWikiName1 = getRandomText(getRandomString());
		String newProjectWikiName2 = getRandomText(getRandomString());

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
	public void userIsAbleToSeeAnErrorAboutRequiredField() {
		Project project = getProjectApiService().createProjectFromTemplate();

		getClassicCodebeamerApplication(activeUser)
				.visitProjectLandingPage(project)
				.getDashboardActionbarComponent().createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(StringUtils.EMPTY))
				.save()
				.redirectedToWikiNewChildDialog()
				.assertWikiNewChildDialogFormComponent(ProjectWikiNewChildDialogFormAssertions::isPageNameEmpty);
	}

	@Test(description = "User inline edits a wiki item")
	public void editWikiItem() {
		Project project = getProjectApiService().createProjectFromTemplate(RandomStringUtils.randomAlphabetic(4),
				ProjectTemplate.AGILE_SCRUM);
		try {
			getClassicCodebeamerApplication(activeUser).visitProjectLandingPage(project)
					.applyProjectTreeComponent(c -> c.selectTreeItemByName("Definition of Done"))
					.wikiContentComponent(c -> {
						c.inlineEditSection("Definition of Done",
										wikiMarkupBuilder -> wikiMarkupBuilder.heading1("heading1")
												.heading2("Heading with accents - áóé")
												.text("descripition"))
								.assertThat()
								.contentOfSectionIs("heading1", "Heading with accents - áóé\ndescripition")
								.contentOfSectionIs("Heading with accents - áóé", "descripition");
						c.inlineEditSection("Heading with accents - áóé", "Heading with accents - áóé\nnew descripition")
								.assertThat()
								.contentOfSectionIs("heading1", "Heading with accents - áóé\nnew descripition")
								.contentOfSectionIs("Heading with accents - áóé", "new descripition");
					});
		} finally {
			getProjectApiService().deleteProject(project);
		}
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(activeUser);
	}

}