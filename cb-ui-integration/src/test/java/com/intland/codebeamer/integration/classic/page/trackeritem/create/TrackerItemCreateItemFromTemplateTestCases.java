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

package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "TrackerItemCreateItemFromTemplateTestCases")
public class TrackerItemCreateItemFromTemplateTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private Project project;

	private Tracker tracker;

	private TrackerItemId trackerItemId;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService services = getDataManagerService();
		projectAdmin = services.getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		project = services.getProjectApiService(projectAdmin)
				.createProject(getRandomText("MyProject"))
					.build();

		tracker = services.getTrackerApiService(projectAdmin).createDefaultTaskTracker(project, "MyTask");
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(projectAdmin).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(projectAdmin);
	}

	@BeforeMethod(alwaysRun = true)
	protected void generateDataBeforeMethod() throws Exception {
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTask", builder -> builder
				.name(getRandomText("MyItem"))
				.description("description"));
	}

	@Test(description = "User is able to create tracker item template")
	public void userIsAbleToCreateTrackerItemTemplate() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectSaveItemAsTemplate()
				.saveTrackerItemAsTemplateComponent(f -> f.templateName(getRandomText("MyTemplate")))
				.save()
				.navigatedToTrackerItemPage();
	}

	@Test(description = "User is able to overwrite tracker item template")
	public void userIsAbleToOverwriteTrackerItemTemplate() {
		String myTemplateName = getRandomText("MyTemplate");

		trackerItemApiService.createTrackerItemTemplate(
				trackerItemId, myTemplateName, "description", true);

		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectSaveItemAsTemplate()
				.saveTrackerItemAsTemplateComponent(f -> f.overwriteTemplate(myTemplateName))
				.save()
				.navigatedToTrackerItemPage();
	}
}
