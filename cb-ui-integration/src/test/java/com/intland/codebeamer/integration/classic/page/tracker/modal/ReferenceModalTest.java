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

package com.intland.codebeamer.integration.classic.page.tracker.modal;

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.workconfigitemreferencemodal.GroupingLevelLabel;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "ReferenceModalTest" })
public class ReferenceModalTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private Tracker taskTracker;

	private Tracker releaseTracker;

	private TrackerItemId trackerItemId;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemService;

	private UserApiService userApiService;

	final String taskTrackerName = "builtInTaskTracker";

	@Override
	protected void generateDataBeforeClass() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();

		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);

		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);

		trackerItemService = getDataManagerService().getTrackerItemApiService(activeUser);

		project = projectApiService.createProjectFromTemplate("ReferenceModalTest" + getRandomString(),
				ProjectTemplate.AGILE_SCRUM);

		releaseTracker = projectApiService.findTrackerByName(project, "Releases");

		taskTracker = trackerApiService.createTaskTracker(project, taskTrackerName)
				.buildAndAdd();

		trackerItemId = getDataManagerService().getTrackerItemApiService(activeUser)
				.createTrackerItem(project, taskTrackerName, builder -> builder
						.name("taskTrackerItemName")
						.description("desc"));

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		userApiService.disableUser(activeUser);
	}

	@Test(description = "user is able to initiate reference modal from Table view reference field")
	public void userIsAbleToInitiateReferenceModalFromTableViewReferenceField() {
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(taskTracker)
				.trackerItemTableRowComponent(trackerItemId, row -> row.getFieldsComponent()
						.editReferenceFieldByModal("Release")
						.assertThat().isVisible());
	}

	@Test(description = "User is able to use group by with Release and Status")
	public void userIsAbleToUseGroupByWithAssignee() {
		GroupingLevelLabel groupingLevelLabel1 = new GroupingLevelLabel("1", "Tracker", "Releases");
		GroupingLevelLabel groupingLevelLabel2 = new GroupingLevelLabel("2", "Status", "Finished");
		List<TrackerItem> trackerItems = trackerItemService.findTrackerItemByNames(releaseTracker.id(), "Sprint 1.1");

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableRow(trackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal("Release")
						.reportSelector(header -> header
								.chooseGroupByField("Tracker")
								.chooseGroupByField("Status")
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.groupIs(trackerItems, groupingLevelLabel1, groupingLevelLabel2)
								.isGroupItemCountEquals(groupingLevelLabel1, 14)
								.isGroupItemCountEquals(groupingLevelLabel2, 1)
								.isAllItemCountEquals(14)));
	}
}
