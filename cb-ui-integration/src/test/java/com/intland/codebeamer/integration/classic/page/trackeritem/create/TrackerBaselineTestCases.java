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

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.CommentsAndAttachmentsTabAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "TrackerBaselineTestCases")
public class TrackerBaselineTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private Project project;

	private TrackerItemId trackerItemId;

	private Tracker tracker;

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
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTask", builder -> builder
				.name(getRandomText("MyItem"))
				.description("description"));
	}

	@Test(description = "User is able to open tracker item in baseline")
	public void userIsAbleToOpenTrackerItemInBaseline() {
		BaselineApiService baselineApiService = getDataManagerService().getBaselineApiService(
				projectAdmin);

		//Creating baseline
		Baseline baseline = baselineApiService.createTrackerBaseline(
				getRandomText("MyBaseline"), project, tracker);

		//Creating comment on HEAD
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.commentsAndAttachmentsTabComponent()
				.addCommentsAndAttachments()
				.setComment("My Comment")
				.save()
				.commentsAndAttachmentsTabAssertion(a -> a.hasNumberOfComment(1));

		//There should be no comment on baseline
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId, baseline)
				.commentsAndAttachmentsTabAssertion(CommentsAndAttachmentsTabAssertions::hasNoComment);
	}
}
