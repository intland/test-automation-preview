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

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectBrowserTabsTestCases")
public class ProjectBrowserTabsTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "User is navigated to the Project List tab by default")
	public void defaultTabIsProjectList() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.projectListComponent(c -> c.assertThat().isTabActive())
				.projectListComponent(c -> c.assertThat().isTabContentActive());

	}

	@Test(description = "User is able to open the Project tree tab")
	public void userIsAbleToOpenProjectTree() {
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.openProjectTree()
				.projectTreeComponent(c -> c.assertThat().isTabActive())
				.projectTreeComponent(c -> c.assertThat().isTabContentActive());
	}

	@Test(description = "User is able to open the Available to join tab")
	public void userIsAbleToOpenJoinTab() {
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.openAvailableToJoin()
				.availableToJoinComponent(c -> c.assertThat().isTabActive())
				.availableToJoinComponent(c -> c.assertThat().isTabContentActive());
	}

	@Test(description = "User is able to open the Compact list tab")
	public void userIsAbleToOpenCompactList() {
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.openCompactList()
				.compactListComponent(c -> c.assertThat().isTabActive())
				.compactListComponent(c -> c.assertThat().isTabContentActive());
	}

	@Test(description = "User is able to open the Project groups tab")
	public void userIsAbleToOpenProjectGroups() {
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.openProjectGroups()
				.projectGroupsComponent(c -> c.assertThat().isTabActive())
				.projectGroupsComponent(c -> c.assertThat().isTabContentActive());
	}
}
