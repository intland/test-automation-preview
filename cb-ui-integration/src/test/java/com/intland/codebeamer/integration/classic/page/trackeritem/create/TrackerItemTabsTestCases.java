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

public class TrackerItemTabsTestCases extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to switch between tabs on item details page")
	public void userIsAbleToCreateComment() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(tracker, trackerItemId)
				.changeToHistoryTab()
				.historyTabComponent(c -> c.assertThat().isTabActive())
				.changeToCommentsAndAttachmentsTab()
				.commentsAndAttachmentsTabComponent(c -> c.assertThat().isTabActive());
	}

}
