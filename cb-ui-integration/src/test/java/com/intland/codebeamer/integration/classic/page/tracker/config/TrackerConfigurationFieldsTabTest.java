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
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "TrackerConfigurationFieldsTabTest" })
public class TrackerConfigurationFieldsTabTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
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
	}

	@Test(description = "User is able to edit field configurations")
	public void editFieldConfiguration() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Check field configurations"));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(c -> c.editFieldConfig("description")
						.setDescription("Best desc")
						.save())
				.trackerConfigFieldsTab(c -> c.newTextField()
						.setLabel("My New Text")
						.setListable(true)
						.save())
				.trackerConfigFieldsTab(c -> c.newLanguageField()
						.setLabel("Another Lang")
						.setDependsOnSelector("Priority")
						.save())
				.trackerConfigFieldsTab(c -> c.save())
				.trackerConfigFieldsTab(c -> c.editFieldConfig("My New Text")
						.assertThat().assertListable(true)
						.close())
				.trackerConfigFieldsTab(c -> c.editFieldConfig("description")
						.assertThat().assertDescription("Best desc")
						.close())
				.trackerConfigFieldsTab(c -> c.newCustomField()
						.setTypeSelector(FieldType.COLOR)
						.setLabel("New color field")
						.setListable(true)
						.save())
				.trackerConfigFieldsTab(c -> c.editFieldConfig("New color field")
						.assertThat()
						.assertListable(true)
						.assertType(FieldType.COLOR)
						.close())
				.trackerConfigFieldsTab(c -> c.save());
	}

	@Test(description = "User is able to add columns to a table field")
	public void addColumnsToATableField() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Add fields to a table"));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(c -> c.newTableField()
						.setLabel("My Table field")
						.save())
				.trackerConfigFieldsTab(c -> c.addColumnToTableField("My Table field")
						.setLabel("My Text column")
						.save())
				.trackerConfigFieldsTab(c -> c.addColumnToTableField("My Table field")
						.setTypeSelector(FieldType.COLOR)
						.setLabel("My Color column")
						.save())
				.trackerConfigFieldsTab(c -> c.save())
				.trackerConfigFieldsTab(c -> c.editFieldConfig("My Text column")
						.assertThat()
						.assertType(FieldType.TEXT)
						.close())
				.trackerConfigFieldsTab(c -> c.editFieldConfig("My Color column")
						.assertThat()
						.assertType(FieldType.COLOR)
						.close());
	}
}