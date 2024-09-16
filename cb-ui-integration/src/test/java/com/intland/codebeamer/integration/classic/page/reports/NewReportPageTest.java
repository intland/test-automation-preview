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

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportQueryAccordionAssertions;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportQueryAccordionComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "NewReportPageTest")
public class NewReportPageTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "user is able to visit new report page")
	public void newReportPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitNewReportPage();
	}

	@Test(description = "user is able to use report query accordion")
	public void testReportQueryAccordion() {
		getClassicCodebeamerApplication(activeUser)
				.visitNewReportPage()
				.assertReportQueryAccordionComponent(ReportQueryAccordionAssertions::isReportQueryAccordionReady);
	}

	@Test(description = "user is able to open and close report query accordion")
	public void testOpenCloseAccordion() {
		getClassicCodebeamerApplication(activeUser)
				.visitNewReportPage()
				.assertReportQueryAccordionComponent(ReportQueryAccordionAssertions::isReportQueryAccordionOpened)
				.reportQueryAccordionComponent(ReportQueryAccordionComponent::closeReportQueryAccordion)
				.assertReportQueryAccordionComponent(ReportQueryAccordionAssertions::isReportQueryAccordionClosed);
	}

	@Test(description = "user is able to add CbQL string in expert mode")
	public void testAdvancedQueryMode() {
		getClassicCodebeamerApplication(activeUser)
				.visitNewReportPage()
				.reportQueryAccordionComponent(c -> c
						.toggleQueryType()
						.setCbQL("project.id IN ('current project')"))
				.reportQueryAccordionComponent(c -> c.assertThat().cbQLEqulsTo("project.id IN ('current project')"))
				.reportQueryAccordionComponent(ReportQueryAccordionComponent::toggleQueryType)
				.assertReportQueryAccordionComponent(assertions -> assertions
						.selectedProjectsEqualsTo("Current Project")
						.selectedTrackersEqualsTo("Any"));
	}

	@Test(description = "user is able to select project and tracker from dropdown")
	public void testSelectProjectAndTracker() {
		// Given
		Project myProject = projectApiService.createProjectFromTemplate();
		Project myProject2 = projectApiService.createProjectFromTemplate();
		Tracker userStoryTracker = trackerApiService.createDefaultUserStoryTracker(myProject, "MyUserStory");

		try {
			// When / Then
			getClassicCodebeamerApplication(activeUser)
					.visitNewReportPage()
					.reportQueryAccordionComponent(c -> c
							.selectProjects(myProject, myProject2)
							.selectTrackers(userStoryTracker))
					.assertReportQueryAccordionComponent(assertions -> assertions
							.selectedProjectsEqualsTo(String.join(", ", myProject.name(), myProject2.name()))
							.selectedTrackersEqualsTo(userStoryTracker.name()));
		} finally {
			projectApiService.deleteProject(myProject.id());
			projectApiService.deleteProject(myProject2.id());
		}
	}
}
