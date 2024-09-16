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

package com.intland.codebeamer.integration.testcase.classic.sharedfields;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldBuilder;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.AbstractTrackerConfigTab;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "SharedFieldsTestsCases")
public class SharedFieldsTestsCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private SharedFieldApiService sharedFieldApiService;

	private static final String SHARED_FIELD_NAME = "mySharedField";

	@Override
	protected void generateDataBeforeClass() throws Exception {
		sharedFieldApiService = getDataManagerService().getSharedFieldApiService();
		sharedFieldApiService.createSharedField(SHARED_FIELD_NAME, SharedFieldBuilder::textField);
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
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
		sharedFieldApiService.deleteSharedFieldByName(SHARED_FIELD_NAME);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13513395")
	@Test(description = "User is able to create custom fields with a shared field")
	public void createNewCustomFieldFromANewSharedFieldInSpecificProjectAndTracker() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "My Tasks");
		String fieldLabel = "My custom text field";

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(config -> config
						.newTextField()
						.setLabel(fieldLabel)
						.setSharedField(SHARED_FIELD_NAME)
						.save())
				.trackerConfigFieldsTab(AbstractTrackerConfigTab::save)
				.trackerConfigFieldsTab(c -> c
						.editFieldConfig(fieldLabel)
						.assertThat()
						.assertLabel(fieldLabel)
						.assertSharedField(SHARED_FIELD_NAME));
	}
}
