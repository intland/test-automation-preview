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

package com.intland.codebeamer.integration.testcase.classic.testmanagement;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.component.field.read.UrlFieldAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "CustomFieldsTestCases")
public class CustomFieldsTestCases extends AbstractIntegrationClassicNGTests {

	private Project project;

	private Tracker taskTracker;

	private Tracker testCasesTracker;

	private TrackerItemId taskTrackerItemId;

	private TrackerItemId testCasesTrackerItemId;

	private User projectAdmin;

	private final String customWikiLinkURLFieldName = "CustomWikiLinkURLField";

	private final String descriptionFieldValue = "description";

	private final String taskTrackerItemName = "TaskTrackerItemCustom";

	private final String testCasesTrackerName = "TestCasesCustom";

	private final String testCasesTrackerItemName = "TestCasesTrackerItemCustom";

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private UserApiService userApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		// Given
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		projectAdmin = userApiService.createUser()
				.addToRegularUserGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);
		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		switchToClassicUI(projectAdmin);
	}

	@BeforeMethod
	protected void generateDataBeforeMethod() throws Exception {
		final String taskTrackerName = "TaskCustom";
		project = projectApiService.createProjectFromTemplate();
		taskTracker = trackerApiService.createTaskTracker(project, taskTrackerName)
				.buildAndAdd();
		testCasesTracker = trackerApiService.createTestCaseTracker(project, testCasesTrackerName)
				.buildAndAdd();
		taskTrackerItemId = trackerItemApiService.createTrackerItem(project, taskTrackerName, builder -> builder
				.name(taskTrackerItemName)
				.description(descriptionFieldValue));
	}

	@AfterMethod
	protected void cleanUpDataAfterMethod() throws Exception {
		trackerApiService.deleteTracker(taskTracker.id());
		trackerApiService.deleteTracker(testCasesTracker.id());
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		userApiService.disableUser(projectAdmin);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13282296", expectedHttpErrors = HttpStatus.NOT_FOUND)
	@Test(description = "User is able to edit Test Case with deleted reference within Wiki Link URL field")
	public void editTestCaseWithDeletedReferenceWithinWikiLinkURLField() {
		// Given
		createTestCaseWithDeletedReferenceWithinWikiLinkURLField();

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemEditPage(testCasesTracker, testCasesTrackerItemId)
				.assertFieldFormComponent(form -> form
						.urlField(customWikiLinkURLFieldName, assertion -> assertion
								.isWikiLink(String.valueOf(taskTrackerItemId.id()))
								.isWikiLinkBroken(String.valueOf(taskTrackerItemId.id()))
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13293826", expectedHttpErrors = HttpStatus.NOT_FOUND)
	@Test(description = "User is able to remove the deleted reference within Wiki Link URL field from Test Case")
	public void removeTheDeletedReferenceWithinWikiLinkURLFieldFromTestCase() {
		// Given
		createTestCaseWithDeletedReferenceWithinWikiLinkURLField();

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemEditPage(testCasesTracker, testCasesTrackerItemId)
				.fieldFormComponent(form -> form
						.clearUrlField(customWikiLinkURLFieldName)
				)
				.save()
				.redirectedToTrackerItemPage()
				.assertFieldFormComponent(form -> form
						.urlField(customWikiLinkURLFieldName, UrlFieldAssertions::assertUrlIsEmpty));
	}

	private void createTestCaseWithDeletedReferenceWithinWikiLinkURLField() {
		trackerApiService.updateTracker(project, testCasesTrackerName).createWikiLinkUrlField(customWikiLinkURLFieldName).buildAndAdd();
		testCasesTrackerItemId = trackerItemApiService.createTrackerItem(project, testCasesTrackerName, builder -> builder
				.name(testCasesTrackerItemName)
				.description(descriptionFieldValue)
				.setWikiLinkFor(customWikiLinkURLFieldName, taskTrackerItemId));
		trackerItemApiService.deleteTrackerItem(taskTrackerItemId);
	}
	
}
