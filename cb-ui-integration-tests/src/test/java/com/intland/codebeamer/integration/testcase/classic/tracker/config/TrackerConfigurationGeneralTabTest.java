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

package com.intland.codebeamer.integration.testcase.classic.tracker.config;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "TrackerConfiguration", "TrackerConfigurationGeneralTab" })
public class TrackerConfigurationGeneralTabTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiService().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "User is able to change the name of a tracker")
	public void changeNameOfATracker() {
		// GIVEN
		String newTrackerName = "MyNewTracker";

		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "Change Task Name");

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigGeneralTab()
				.trackerConfigGeneralTab(c -> c.assertThat()
						.isTabActive()
						.nameEqualsTo(tracker.name()))
				.trackerConfigGeneralTab(c -> c.fillInputName(newTrackerName))
				.trackerConfigGeneralTab(c -> c.saveTrackerConfig())
				.trackerConfigGeneralTab(c -> c.assertThat().nameEqualsTo(newTrackerName))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

}