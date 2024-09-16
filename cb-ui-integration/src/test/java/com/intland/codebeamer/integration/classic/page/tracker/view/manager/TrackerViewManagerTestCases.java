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

package com.intland.codebeamer.integration.classic.page.tracker.view.manager;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "TrackerViewManagerTestCases")
public class TrackerViewManagerTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser()
				.createUser()
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
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "User is able save tracker views")
	public void checkSavingTrackerViews() {
		// GIVEN
		String viewName = "My Super View";
		Tracker tracker = trackerApiService
				.createEpicTracker(project, "My Epic 1")
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerDocumentViewPage(tracker)
				.centerActionBarComponent()
				.reportSelector(r -> r
						.viewManager(manager -> manager
								.saveAs(builder -> builder
										.name(viewName)
										.publicView()
										.addNewFolder("My Folder")
										.roles("Developer", "Tester")
										.save())
								.assertTreeComponent(a -> a.treeItemExistsByName(viewName))));
	}

	@Test(description = "User is able select tracker views")
	public void checkSelectingTrackerViews() {
		// GIVEN
		String viewName = "All Items";
		Tracker tracker = trackerApiService
				.createEpicTracker(project, "My Epic 2")
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerDocumentViewPage(tracker)
				.centerActionBarComponent()
				.reportSelector(r -> r
						.viewManager(manager -> manager
								.treeComponent(t -> t.selectTreeItemByName(viewName)))
						.assertViewManager(a -> a.activeView(viewName)));
	}
}