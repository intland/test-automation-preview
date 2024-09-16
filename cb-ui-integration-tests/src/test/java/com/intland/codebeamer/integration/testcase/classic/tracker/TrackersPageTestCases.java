package com.intland.codebeamer.integration.testcase.classic.tracker;

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.workconfigitemreferencemodal.GroupingLevelLabel;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "TrackersPageTestCases")
public class TrackersPageTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13693263")
	@Test(description = "Verify that table view link not visible in the inplace editor modal window's group headers")
	public void verifyTableViewLinkNotVisibleInInplaceEditorModalWindowGroupHeaders() {
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		Tracker srsTracker = trackerApiService.createDefaultRequirementTracker(project, "SRS");
		Tracker crsTracker = trackerApiService.createDefaultRequirementTracker(project, "CRS");
		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, "Task");

		TrackerItemId srs1Id = trackerItemApiService.createTrackerItem(project, srsTracker.name(), builder -> builder
				.name("SRS1")
				.description("SRS1"));
		TrackerItemId srs2Id = trackerItemApiService.createTrackerItem(project, srsTracker.name(), builder -> builder
				.name("SRS2")
				.description("SRS2"));
		TrackerItemId crs1Id = trackerItemApiService.createTrackerItem(project, crsTracker.name(), builder -> builder
				.name("CRS1")
				.description("CRS1"));
		TrackerItemId crs2Id = trackerItemApiService.createTrackerItem(project, crsTracker.name(), builder -> builder
				.name("CRS2")
				.description("CRS2"));
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTracker.name(), builder -> builder
				.name("Task")
				.description("Task"));

		GroupingLevelLabel groupingLevelLabel1 = new GroupingLevelLabel("1", "Tracker", "SRS");
		GroupingLevelLabel groupingLevelLabel2 = new GroupingLevelLabel("1", "Tracker", "CRS");

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableRow(taskId)
						.getFieldsComponent()
						.editReferenceFieldByModal("Subject")
						.reportSelector(header -> header
								.chooseGroupByField("Tracker")
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.groupIs(List.of(new TrackerItem(srs2Id, "SRS2"), new TrackerItem(srs1Id, "SRS1")), groupingLevelLabel1)
								.groupIs(List.of(new TrackerItem(crs2Id, "CRS2"), new TrackerItem(crs1Id, "CRS1")), groupingLevelLabel2)
								.isGroupItemCountEquals(groupingLevelLabel1, 2)
								.isGroupItemCountEquals(groupingLevelLabel2, 2)
								.isAllItemCountEquals(4))
						.reportSelector(reportSelector -> reportSelector.assertThat()
								.openInTableViewDoesNotVisible()));
	}

}
