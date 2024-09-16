package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.stream.IntStream;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemHistoryComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

public class TrackerItemHistoryTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private Project project;

	private Tracker tracker;

	private TrackerItemId trackerItemId;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService services = getDataManagerService();
		projectAdmin = services.getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		project = services.getProjectApiService(projectAdmin).createProject(getRandomText("MyProject"))
				.addUserAs(projectAdmin, "Project Admin")
				.build();

		tracker = services.getTrackerApiService(projectAdmin).createDefaultTaskTracker(project, "MyTask");
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(projectAdmin).deleteProject(project);
		getDataManagerService().getUserApiService(projectAdmin).disableUser(projectAdmin);
	}

	@BeforeMethod(alwaysRun = true)
	protected void generateDataBeforeMethod() throws Exception {
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTask", builder -> builder
				.name(getRandomText("MyItem"))
				.description("description"));
	}

	@Test(description = "User is able to see item history")
	public void userIsAbleToSeeItemHistory() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.changeToHistoryTab()
				.historyTabComponent(c -> c.assertThat().isTabActive())
				.historyTabComponent(c -> {
					c.assertThat().hasNumberOfHistoryItemsShown(1);
					TrackerItemHistoryComponent trackerItemHistoryComponent = c.getHistoryList().getFirst();
					trackerItemHistoryComponent.assertThat()
							.actionEqualsTo("Submit")
							.versionEqualsTo(1)
							.isSubmittedBy(projectAdmin.getUserId())
							.isSubmittedBy(projectAdmin.getName())
							.hasNumberOfFieldChanges(0);
				});
	}

	@Test(description = "User can see field changes in the history tab")
	public void userIsAbleToSeeMultipleFieldChangesOnHistoryTab() {
		final String name = "updated name";
		final String description = "updated description";
		final String priority = "High";
		final String status = "In progress";
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerItemApiService.updateTrackerItem(trackerItemId, builder -> builder
				.name(name)
				.description(description)
				.priority(priority)
				.status(status));

		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.changeToHistoryTab()
				.historyTabComponent(c -> c.assertThat().isTabActive())
				.historyTabComponent(c -> {
					c.assertThat().hasNumberOfHistoryItemsShown(2);
					TrackerItemHistoryComponent trackerItemHistoryComponent = c.getHistoryList().getFirst();
					trackerItemHistoryComponent.assertThat()
							.isSubmittedBy(projectAdmin.getUserId())
							.isSubmittedBy(projectAdmin.getName())
							.versionEqualsTo(2)
							.actionEqualsTo("Start")
							.hasNumberOfFieldChanges(4)
							.hasFieldChangeWithNewValue("Summary", name)
							.hasFieldChangeWithNewValue("Description", description)
							.hasFieldChangeWithNewValue("Status", status)
							.hasFieldChangeWithOldAndNewValue("Priority", "--", priority);
				});
	}

	@Test(description = "User is able to see multiple history items")
	public void userIsAbleToSeeMultipleHistoryItems() {
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		IntStream.range(0, 30).forEach(i -> trackerItemApiService.updateTrackerItem(trackerItemId, builder -> builder
				.name("item %d".formatted(Integer.valueOf(i)))));

		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.changeToHistoryTab()
				.historyTabComponent(c -> c.assertThat().isTabActive())
				.historyTabComponent(c -> {
					c.assertThat()
							.hasAllHistoryItemShown()
							.hasTotalNumberOfHistoryItems(31)
							.hasNumberOfHistoryItemsShown(20);
					c.showAllHistoryItems();
					c.assertThat().hasNumberOfHistoryItemsShown(31);
				});
	}

}
