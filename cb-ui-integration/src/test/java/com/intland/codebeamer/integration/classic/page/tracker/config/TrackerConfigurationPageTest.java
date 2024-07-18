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

package com.intland.codebeamer.integration.classic.page.tracker.config;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "TrackerConfigurationPage")
public class TrackerConfigurationPageTest extends AbstractIntegrationClassicNGTests {

	@Test(description = "User is able to switch between tracker configuration tabs")
	public void testActivatingTrackerConfigurationTabs() {
		// GIVEN
		UserApiService userService = new UserApiService(getApplicationConfiguration());

		User activeUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration(), activeUser.getName());
		Project project = projectApiService.createProjectFromTemplate();
		try {
			TrackerApiService trackerApiService = new TrackerApiService(this.getApplicationConfiguration(),
					activeUser.getName());
			Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "Task 2");

			// WHEN THEN
			getClassicCodebeamerApplication(activeUser)
					.visitTrackerConfigPage(tracker)
					.changeToTrackerConfigGeneralTab()
					.trackerConfigGeneralTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigPermissionsTab()
					.trackerConfigPermissionsTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigStateTransitionsTab()
					.trackerConfigStateTransitionsTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigFieldsTab()
					.trackerConfigFieldsTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigEscalationTab()
					.trackerConfigEscalationTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigNotificationsTab()
					.trackerConfigNotificationsTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigAuditTrailTab()
					.trackerConfigAuditTrailTab(c -> c.assertThat().isTabActive())
					.changeToTrackerConfigServiceDeskTab()
					.trackerConfigServiceDeskTab(c -> c.assertThat().isTabActive());

		} finally {
			projectApiService.deleteProject(project);
		}
	}

}