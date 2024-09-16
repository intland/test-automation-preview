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

package com.intland.codebeamer.integration.classic.page.reports;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.reports.findreports.component.ProjectSelectorDropdownAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "FindReportsTestCases")
public class FindReportsTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private ProjectApiService projectApiService;

	private Project project;

	private UserApiService userService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		DataManagerService dataManagerService = new DataManagerService(getApplicationConfiguration());
		userService = dataManagerService.getUserApiService(apiUser);
		projectApiService = dataManagerService.getProjectApiService(apiUser);

		activeUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		project = projectApiService.createProject(getRandomText("MyProject"))
				.addUserAs(activeUser, "Project Admin")
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (project != null) {
			projectApiService.deleteProject(project.id());
		}
		if (activeUser != null) {
			userService.disableUser(activeUser);
		}
	}

	@Test(description = "User is able to visit Report and Traceability Report Finder dialog page.")
	public void reportFinderDialogCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitFindReportsPage();
	}

	@Test(description = "Assert all project selector dropdown component elements.")
	public void projectSelectorDropDownCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitFindReportsPage()
				.getReportFinderDialogComponent()
				.reportFinderDialogComponent(c -> c
						.selectProjectSelectorDropDown()
						.assertProjectSelectorDropdownComponent(ProjectSelectorDropdownAssertions::isProjectSelectorDropdownReady)
				);
	}

	@Test(description = "The header label is not clickable.")
	public void validateHeaderLabelNonClickable() {
		getClassicCodebeamerApplication(activeUser)
				.visitFindReportsPage()
				.getReportFinderDialogComponent()
				.reportFinderDialogComponent(c -> c
						.projectSelectorDropdownComponent(s -> s
								.selectProject(project.name())
								.selectHeaderLabel("Shared Reports from other Projects")
						)
						.assertProjectSelectorDropdownComponent(s -> s.assertProjectCheckbox(project.name(), true))
				);
	}

	@Test(description = "User can successfully use the filter and clear all checkbox functionality.")
	public void verifyFilterAndClearFunctionality() {
		getClassicCodebeamerApplication(activeUser)
				.visitFindReportsPage()
				.getReportFinderDialogComponent()
				.reportFinderDialogComponent(c -> c
						.projectSelectorDropdownComponent(s -> s
								.setFilterProjectByName(project.name())
								.selectProject(project.name())
								.clearAll()
						)
						.assertProjectSelectorDropdownComponent(a -> a
								.assertProjectCheckbox(project.name(), false))
				);
	}
}
