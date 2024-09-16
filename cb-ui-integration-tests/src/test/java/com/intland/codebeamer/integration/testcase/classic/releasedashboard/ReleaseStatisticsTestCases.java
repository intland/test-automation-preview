package com.intland.codebeamer.integration.testcase.classic.releasedashboard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "ReleaseStatisticsTestCases")
public class ReleaseStatisticsTestCases extends AbstractIntegrationClassicNGTests {

	private static final String RELEASE_FIELD_NAME = "Release";

	private static final String PARENT_FIELD_NAME = "Parent";

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private UserApiService userApiService;

	private User user;

	private Project agileProject;

	private Tracker releaseTracker;

	private TrackerItemId releaseItemId;

	private TrackerItemId sprintItemId1;

	private TrackerItemId sprintItemId11;

	private TrackerItemId sprintItemId2;

	private TrackerItemId sprintItemId3;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		user = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(user);
		trackerApiService = getDataManagerService().getTrackerApiService(user);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(user);

		switchToClassicUI(user);
	}

	@Override
	protected void cleanUpDataAfterClass() {
		userApiService.disableUser(user);
	}

	@BeforeMethod
	private void generateDataBeforeMethod() {
		// GIVEN
		agileProject = projectApiService.createProjectFromTemplate(getRandomText("Agile Project"), ProjectTemplate.AGILE_SCRUM);

		releaseTracker = projectApiService
				.findTrackerByName(agileProject, "Releases");

		Tracker taskTracker = trackerApiService
				.createTaskTracker(agileProject, "Task")
				.buildAndAdd();

		releaseItemId = trackerItemApiService
				.createTrackerItem(releaseTracker, builder -> builder.name("Release"));

		sprintItemId1 = trackerItemApiService
				.createTrackerItem(releaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		sprintItemId11 = trackerItemApiService
				.createTrackerItem(releaseTracker, builder -> builder.name("Sprint1.1")
						.setTrackerItemFor(PARENT_FIELD_NAME, sprintItemId1));

		sprintItemId2 = trackerItemApiService
				.createTrackerItem(releaseTracker, builder -> builder.name("Sprint2")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		sprintItemId3 = trackerItemApiService
				.createTrackerItem(releaseTracker, builder -> builder.name("Sprint3")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 1")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId1));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 2")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId1));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 3")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId11));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 4")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId11));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 5")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId2));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 6")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId2));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 7")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId3));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 8")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintItemId3));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 9")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 10")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 11")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 12")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));
	}

	@AfterMethod
	private void generateDataAfterMethod() {
		projectApiService.deleteProject(agileProject);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14116774")
	@Test(description = "Verify that the 1st stats line in the header is correct")
	public void verifyReleaseStatisticsFirstLine() {
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(releaseTracker)
				.releaseDashboardComponent(
						c -> c.assertReleaseSection(releaseItemId,
								s -> s.verifyFirstStatsLineInHeader("4 Sprints", 0.0, "0 closed", "4 open")));
	}
}
