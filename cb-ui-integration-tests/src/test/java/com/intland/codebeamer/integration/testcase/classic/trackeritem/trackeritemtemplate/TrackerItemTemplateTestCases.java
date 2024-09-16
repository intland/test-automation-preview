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

package com.intland.codebeamer.integration.testcase.classic.trackeritem.trackeritemtemplate;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import org.testng.annotations.Test;

public class TrackerItemTemplateTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);
		project = projectApiService.createProjectFromTemplate(getRandomText("Agile Project"), ProjectTemplate.AGILE_SCRUM);
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13577781")
	@Test(description = "User is able to create Private Template from Task Tracker Item Details Page")
	public void createPrivateTemplateFromTaskTrackerItemDetailsPage() {
		// GIVEN
		final String trackerName = "Tasks";
		final String trackerItemName = "Default Task Tracker Item";
		final String backlogItemName = "DAB Radio";
		final String templateName = "Custom Tracker Item Template";
		Tracker tracker = projectApiService.findTrackerByName(project, trackerName);
		Tracker backlogItemTracker = projectApiService.findTrackerByName(project, "User Stories");
		TrackerItem backlogItem = trackerItemApiService.findTrackerItemByName(backlogItemTracker, backlogItemName);
		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.setTrackerItemFor("Backlog Item", backlogItem));

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemEditPage(tracker, trackerItem)
				.fieldFormComponent(form -> form
						.setChoiceField("Priority", "Highest")
						.setChoiceField("Resolution", "Implemented"))
				.save()
				.redirectedToTrackerItemPage()
				.actionBarComponent()
				.setStatus("Start")
				.save()
				.redirectedToTrackerItemPage()
				.actionBarComponent()
				.openMoreActionMenu()
				.selectSaveItemAsTemplate()
				.saveTrackerItemAsTemplateComponent(f -> f.templateName(templateName))
				.save();

		// THEN
		application
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(templateName)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> f.assertThat()
						.textField("Template Name", v -> v.is(templateName))
						.referenceField("Backlog Item", v -> v.is(backlogItem))
						.referenceField("Release", v -> v.isEmpty())
						.choiceField("Priority", v -> v.is("Highest"))
						.choiceField("Status", v -> v.is("To Do")) // Templates do not save Statuses, so To Do is expected
						.textField("Summary", v -> v.is(trackerItemName)));
	}

}
