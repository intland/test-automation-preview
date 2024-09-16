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

package com.intland.codebeamer.integration.classic.page.tracker.main;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplate;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "TrackerManageTemplatesTestCases")
public class TrackerManageTemplatesTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		projectAdmin = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);
		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);

		project = projectApiService.createProjectFromTemplate();
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "User is able to edit tracker item template")
	public void userIsAbleToEditTrackerItemTemplate() {
		// Given
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "MyTaskTracker");

		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTaskTracker", builder -> builder
				.name(getRandomText("MyTrackerItem"))
				.description("description"));

		TrackerItemTemplate myTrackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(
				trackerItemId, "MyTrackerItemTemplate", "description", true);

		// When / Then
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(myTrackerItemTemplate)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> f
						.templateName(getRandomText("TrackerItemTemplate"))
						.templateDescription(getRandomText("edited description"))
						.setChoiceField("Priority", "High"))
				.save()
				.redirectedToManageItemTemplatesDialog()
				.editTrackerItemTemplate(myTrackerItemTemplate)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> f.assertThat().choiceField("Priority", v -> v.is("High")))
				.save()
				.redirectedToManageItemTemplatesDialog()
				.close()
				.redirectedToTrackerTableViewPage();
	}
}