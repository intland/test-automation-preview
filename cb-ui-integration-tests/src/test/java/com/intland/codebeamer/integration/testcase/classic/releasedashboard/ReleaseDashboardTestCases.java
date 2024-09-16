package com.intland.codebeamer.integration.testcase.classic.releasedashboard;

import java.util.Date;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermission;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "ReleaseDashboardTestCases")
public class ReleaseDashboardTestCases extends AbstractIntegrationClassicNGTests {

	private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.FORMAT1;

	private static final String NO_PERMISSION_TOOLTIP = "You don't have permission to modify this item.";

	private static final String QA_ROLE = "QA";
	
	private static final String RELEASE_TRACKER_NAME = "Releases";

	private static final String RELEASE = "Multimedia and Navigation Release 1";

	private static final String SPRINT = "Sprint 1.2";

	private Date originalStartDate;

	private Date originalEndDate;

	private TrackerItem releaseItem;

	private TrackerItem sprintItem;

	private User projectAdmin;

	private User qaUser;

	private Project agileProject;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		UserApiService userApiService = getDataManagerService().getUserApiServiceWithConfigUser();

		projectAdmin = userApiService
				.createUser()
				.addToRegularUserGroup()
				.build();

		userApiService.updateUserDetails(projectAdmin.getName(), builder -> builder
				.dateFormat(DEFAULT_DATE_FORMAT));

		qaUser = getDataManagerService().getUserApiServiceWithConfigUser()
				.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);
		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);

		agileProject = projectApiService.createProjectFromTemplate(getRandomText("Agile Project"), ProjectTemplate.AGILE_SCRUM);

		sprintItem = getItem(SPRINT);
		com.intland.swagger.client.model.TrackerItem sprint = getTrackerItem(sprintItem);
		originalStartDate = sprint.getStartDate();
		originalEndDate = sprint.getEndDate();

		releaseItem = getItem(RELEASE);

		switchToClassicUI(projectAdmin);
		switchToClassicUI(qaUser);
	}

	@Override
	protected void cleanUpDataAfterClass() {
		projectApiService.deleteProject(agileProject);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(projectAdmin);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(qaUser);
	}

	@AfterMethod
	protected void cleanUpDataAfterMethod() {
		trackerItemApiService.updateTrackerItem(sprintItem.id(), builder -> builder
				.setDateFor("Start Date", originalStartDate)
				.setDateFor("Planned Release Date", originalEndDate));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365769")
	@Test(description = "Modify start date of GANTT chart as QA by double click")
	public void tryToModifyStartDateOfGanttChartAsQAByDoubleClick() {
		// GIVEN
		Tracker tracker = projectApiService.findTrackerByName(agileProject, RELEASE_TRACKER_NAME);
		trackerApiService.updateTrackerPermissions(tracker, builder -> builder
				.revokePermissionForRole(QA_ROLE, TrackerPermission.ISSUE_EDIT_NOT_OWN));
		projectApiService.addUserWithRoles(agileProject.id(), List.of(qaUser.getName()), List.of(QA_ROLE));

		// WHEN THEN
		getClassicCodebeamerApplication(qaUser)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.doubleClickOnReleaseBar(1)
						.assertThat()
						.assertTooltip(NO_PERMISSION_TOOLTIP)
						.assertEditDialogIsHidden());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365770")
	@Test(description = "Modify end date of GANTT chart as QA by Drag and Drop")
	public void tryToModifyEndDateOfGanttChartAsQAByDragAndDrop() {
		// GIVEN
		Tracker tracker = projectApiService.findTrackerByName(agileProject, RELEASE_TRACKER_NAME);
		trackerApiService.updateTrackerPermissions(tracker, builder -> builder
				.revokePermissionForRole(QA_ROLE, TrackerPermission.ISSUE_EDIT_NOT_OWN));
		projectApiService.addUserWithRoles(agileProject.id(), List.of(qaUser.getName()), List.of(QA_ROLE));

		// WHEN THEN
		getClassicCodebeamerApplication(qaUser)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.hoverOnReleaseBar(1)
						.assertThat()
						.assertTooltip(NO_PERMISSION_TOOLTIP));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365768")
	@Test(description = "Modify start date of GANTT chart as QA by Drag and Drop")
	public void tryToModifyStartDateOfGanttChartAsQAByDragAndDrop() {
		// GIVEN
		Tracker tracker = projectApiService.findTrackerByName(agileProject, RELEASE_TRACKER_NAME);
		trackerApiService.updateTrackerPermissions(tracker, builder -> builder
				.revokePermissionForRole(QA_ROLE, TrackerPermission.ISSUE_EDIT_NOT_OWN));
		projectApiService.addUserWithRoles(agileProject.id(), List.of(qaUser.getName()), List.of(QA_ROLE));

		// WHEN THEN
		getClassicCodebeamerApplication(qaUser)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.hoverOnReleaseBar(1)
						.assertThat()
						.assertTooltip(NO_PERMISSION_TOOLTIP))
				.releaseGanttChartComponent(c -> c
						.hoverOnReleaseBar(2)
						.assertThat()
						.assertTooltip(NO_PERMISSION_TOOLTIP));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365762")
	@Test(description = "Verify modify start date of Gantt chart as ProjectAdmin by double click and typing date")
	public void modifyStartDateOfGanttChartAsProjectAdminByDoubleClickAndTypingDate() {
		// GIVEN

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.editReleaseBar(3)
						.setStartDate(2020, 6, 20, DEFAULT_DATE_FORMAT)
						.save())
				.releaseGanttChartComponent(c -> c.editReleaseBar(3)
						.assertThat()
						.startDateIs(2020, 6, 20, DEFAULT_DATE_FORMAT))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365763")
	@Test(description = "Verify modify end date of Gantt chart as ProjectAdmin by double click and typing date")
	public void modifyEndDateOfGanttChartAsProjectAdminByDoubleClickAndTypingDate() {
		// GIVEN

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.editReleaseBar(3)
						.setEndDate(2020, 6, 30, DEFAULT_DATE_FORMAT)
						.save())
				.releaseGanttChartComponent(c -> c.editReleaseBar(3)
						.assertThat()
						.endDateIs(2020, 6, 30, DEFAULT_DATE_FORMAT))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365764")
	@Test(description = "Verify modify start date of Gantt chart as ProjectAdmin by double click and pick a date from calendar")
	public void modifyStartDateOfGanttChartAsProjectAdminByDoubleClickAndPickingDateFromCalendar() {
		// GIVEN

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.editReleaseBar(3)
						.openStartDateCalendar(calendar -> calendar.selectDay(2020, 6, 2))
						.save())
				.releaseGanttChartComponent(c -> c.editReleaseBar(3)
						.assertThat()
						.startDateIs(2020, 6, 2, DEFAULT_DATE_FORMAT))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365765")
	@Test(description = "Verify modify end date of Gantt chart as ProjectAdmin by double click and pick a date from calendar")
	public void modifyEndDateOfGanttChartAsProjectAdminByDoubleClickAndPickingDateFromCalendar() {
		// GIVEN

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.editReleaseBar(3)
						.openEndDateCalendar(calendar -> calendar.selectDay(2020, 6, 30))
						.save())
				.releaseGanttChartComponent(c -> c.editReleaseBar(3)
						.assertThat()
						.endDateIs(2020, 6, 30, DEFAULT_DATE_FORMAT))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365756")
	@Test(description = "Verify modify start date of Gantt chart as ProjectAdmin by Drag and Drop")
	public void modifyStartDateOfGanttChartAsProjectAdminByDragAndDrop() {
		// GIVEN
		TrackerItem releaseTrackerItem = getItem("Multimedia and Navigation Release 1");

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseTrackerItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.moveStartDateOfReleaseBar(3, 10))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13365757")
	@Test(description = "Verify modify end date of Gantt chart as ProjectAdmin by Drag and Drop")
	public void modifyEndDateOfGanttChartAsProjectAdminByDragAndDrop() {
		// GIVEN

		// WHEN / THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemReleasePage(releaseItem.id())
				.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
						.moveEndDateOfReleaseBar(3, 45))
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13693366")
	@Test(description = "Verify that table view link is not visible in release kanban board view group headers")
	public void verifyTableViewLinkNotVisibleInReleaseKanbanBoardViewGroupHeaders() {
		Project defaultProject = projectApiService.createProjectFromTemplate();
		Tracker releaseTracker = projectApiService.findTrackerByName(defaultProject, RELEASE_TRACKER_NAME);
		Tracker srsTracker = projectApiService.findTrackerByName(defaultProject, "System Requirement Specifications");
		Tracker crsTracker = projectApiService.findTrackerByName(defaultProject, "Customer Requirement Specifications");
		Tracker taskTracker = projectApiService.findTrackerByName(defaultProject, "Tasks");
		Tracker bugTracker = projectApiService.findTrackerByName(defaultProject, "Bugs");

		TrackerItemId releaseId = trackerItemApiService.createTrackerItem(releaseTracker, builder -> builder
				.name("Release1")
				.status("Draft"));
		trackerItemApiService.createTrackerItem(srsTracker, builder -> builder
				.name("SRS1")
				.description("SRS1")
				.status("New"));
		trackerItemApiService.createTrackerItem(crsTracker, builder -> builder
				.name("CRS1")
				.description("CRS1")
				.status("New")
				.setTrackerItemFor("Release", releaseId));
		trackerItemApiService.createTrackerItem(crsTracker, builder -> builder
				.name("CRS2")
				.description("CRS2")
				.status("New")
				.setTrackerItemFor("Release", releaseId));
		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder
				.name("Task")
				.description("Task")
				.status("New"));
		trackerItemApiService.createTrackerItem(bugTracker, builder -> builder
				.name("Bug")
				.description("Bug")
				.status("New"));

		getClassicCodebeamerApplication(projectAdmin)
				.visitReleaseKanbanBoardPage(releaseTracker.id(), releaseId)
				.actionBarComponent(actionBar -> actionBar
						.chooseGroupByField("Tracker")
						.filter())
				.kanbanBoardComponent()
				.assertThat()
				.openInTableViewIsNotVisible();

		// Cleanup
		projectApiService.deleteProject(defaultProject);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13693343")
	@Test(description = "Verify that table view link is not visible in release planner view group headers")
	public void verifyTableViewLinkNotVisibleInReleasePlannerViewGroupHeaders() {
		Project defaultProject = projectApiService.createProjectFromTemplate();
		Tracker releaseTracker = projectApiService.findTrackerByName(defaultProject, RELEASE_TRACKER_NAME);
		Tracker srsTracker = projectApiService.findTrackerByName(defaultProject, "System Requirement Specifications");
		Tracker crsTracker = projectApiService.findTrackerByName(defaultProject, "Customer Requirement Specifications");
		Tracker taskTracker = projectApiService.findTrackerByName(defaultProject, "Tasks");
		Tracker bugTracker = projectApiService.findTrackerByName(defaultProject, "Bugs");

		TrackerItemId releaseId = trackerItemApiService.createTrackerItem(releaseTracker, builder -> builder
				.name("Release1")
				.status("Draft"));
		trackerItemApiService.createTrackerItem(srsTracker, builder -> builder
				.name("SRS1")
				.description("SRS1")
				.status("New"));
		trackerItemApiService.createTrackerItem(crsTracker, builder -> builder
				.name("CRS1")
				.description("CRS1")
				.status("New")
				.setTrackerItemFor("Release", releaseId));
		trackerItemApiService.createTrackerItem(crsTracker, builder -> builder
				.name("CRS2")
				.description("CRS2")
				.status("New")
				.setTrackerItemFor("Release", releaseId));
		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder
				.name("Task")
				.description("Task")
				.status("New"));
		trackerItemApiService.createTrackerItem(bugTracker, builder -> builder
				.name("Bug")
				.description("Bug")
				.status("New"));

		getClassicCodebeamerApplication(projectAdmin)
				.visitReleasePlannerPage(releaseTracker.id(), releaseId)
				.actionBarComponent(actionBar -> actionBar
						.chooseGroupByField("Tracker")
						.filter())
				.plannerComponent()
				.assertThat()
				.openInTableViewIsNotVisible();

		// Cleanup
		projectApiService.deleteProject(defaultProject);
	}

	private TrackerItem getItem(String itemName) {
		Tracker releaseTracker = projectApiService.findTrackerByName(agileProject, RELEASE_TRACKER_NAME);

		return trackerItemApiService.findTrackerItemByName(releaseTracker, itemName);
	}
}