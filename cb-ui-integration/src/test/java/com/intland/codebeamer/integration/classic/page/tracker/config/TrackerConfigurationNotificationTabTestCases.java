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
import com.intland.codebeamer.integration.api.service.tracker.TrackerField;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.component.model.TrackerFieldReference;
import com.intland.codebeamer.integration.classic.component.model.UserReference;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.AbstractTrackerConfigTab;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "TrackerConfigurationNotificationTabTestCases" })
public class TrackerConfigurationNotificationTabTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerFieldApiService trackerFieldApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		trackerFieldApiService = getDataManagerService().getTrackerFieldApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "user is able to set notification subscribers and only members checkboxes")
	public void userIsAbleToSetNotificationSubscribersAndOnlyMembersCheckboxes() {
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "Check field configurations");
		TrackerField assignedToTrackerField = trackerFieldApiService.findFieldReference(tracker.id(), "Assigned to");
		TrackerFieldReference assignedTo = new TrackerFieldReference("Assigned to");

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigNotificationsTab()
				.trackerConfigNotificationsTab(tab -> tab
						.setAnyNotificationField(new UserReference(activeUser.getName()))
						.setStatusChangeNotification(assignedTo)
						.setSubscriberForStatus("New", assignedTo)
						.setSubscriberForStatus("In progress", assignedTo)
						.setOnlyMemberForAnyNotificationCheckbox(true)
						.setOnlyMemberStatusChangeNotificationCheckbox(true)
						.setOnlyMemberForStatusCheckbox("New", true)
						.setOnlyMemberForStatusCheckbox("In progress", true))
				.trackerConfigNotificationsTab(AbstractTrackerConfigTab::save)
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
				.trackerConfigNotificationsTab(tab -> tab
						.assertThat()
						.anyNotificationContains(activeUser)
						.statusChangeNotificationContains(assignedToTrackerField)
						.statusNotificationContains("New", assignedToTrackerField)
						.statusNotificationContains("In progress", assignedToTrackerField)
						.assertOnlyMemberForAnyNotificationCheckbox(true)
						.assertOnlyMemberForStatusChangeNotificationCheckbox(true)
						.assertOnlyMembersForStatusCheckbox("New", true)
						.assertOnlyMembersForStatusCheckbox("In progress", true)
						.assertOnlyMembersForStatusCheckbox("Suspended", false));
	}
}
