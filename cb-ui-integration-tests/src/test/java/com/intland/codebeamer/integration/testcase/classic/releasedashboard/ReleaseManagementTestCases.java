package com.intland.codebeamer.integration.testcase.classic.releasedashboard;

import java.util.List;

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
import com.intland.codebeamer.integration.classic.defaults.Field;
import com.intland.codebeamer.integration.classic.defaults.Role;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "ReleaseManagementTestCases")
public class ReleaseManagementTestCases extends AbstractIntegrationClassicNGTests {

	private User user;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private Tracker releaseTracker;

	private TrackerItemId releaseTrackerItemId;

	private TrackerItemApiService trackerItemApiService;

	private UserApiService userApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		user = userApiService.createUser().addToRegularUserGroup().build();

		userApiService.updateUserDetails(user.getName(), builder -> builder.dateFormat(DateFormat.FORMAT1));

		projectApiService = getDataManagerService().getProjectApiService(user);
		trackerApiService = getDataManagerService().getTrackerApiService(user);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(user);

		project = projectApiService.createProjectFromTemplate(getRandomText("Agile-Scrum Project "), ProjectTemplate.AGILE_SCRUM);

		// 2. Create the following structure in Releases tracker:
		// Prepare a release (Release - R1)
		String releaseTrackerName = "Releases";
		releaseTracker = projectApiService.findTrackerByName(project, releaseTrackerName);

		// Create a child release for R1 (R1.1)
		String releaseDescription = "Description of the release.";
		releaseTrackerItemId = trackerItemApiService.createTrackerItem(releaseTracker,
				builder -> builder.name("Release - R1").description(releaseDescription));

		TrackerItemId releaseR11 = trackerItemApiService.createTrackerItem(this.releaseTracker,
				builder -> builder.name("R1.1").description(releaseDescription)
						.setTrackerItemFor(Field.PARENT.toString(), releaseTrackerItemId));

		// Create a child release for R1.1 (R1.1.1)
		TrackerItemId releaseR111 = trackerItemApiService.createTrackerItem(this.releaseTracker,
				builder -> builder.name("R1.1.1").description(releaseDescription)
						.setTrackerItemFor(Field.PARENT.toString(), releaseR11));

		// Create another child release for R1 (R1.2)
		TrackerItemId releaseR12 = trackerItemApiService
				.createTrackerItem(this.releaseTracker,
						builder -> builder.name("R1.2").description(releaseDescription)
								.setTrackerItemFor(Field.PARENT.toString(), releaseTrackerItemId));

		// Create another child release for R1 (R1.3)
		TrackerItemId releaseR13 = trackerItemApiService
				.createTrackerItem(this.releaseTracker,
						builder -> builder.name("R1.3").description(releaseDescription)
								.setTrackerItemFor(Field.PARENT.toString(), releaseTrackerItemId));

		// 3. Assign 2 task items to each release, assign the remaining to R1.
		String taskTrackerName = getRandomText("Tasks ");
		Tracker taskTracker = trackerApiService.createTaskTracker(project, taskTrackerName).buildAndAdd();

		String myTaskItem = "My Task Item ";
		String taskDescription = "Description of the task.";
		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "1")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseTrackerItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "2")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseTrackerItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "3")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR11));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "4")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR11));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "5")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR111));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "6")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR111));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "7")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR12));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "8")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR12));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "9")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR13));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name(myTaskItem + "10")
						.description(taskDescription)
						.setTrackerItemFor(Field.RELEASE.toString(), releaseR13));

		// Others
		projectApiService.addUserWithRoles(project.id(), List.of(user.getName()), List.of(Role.PROJECT_ADMIN.toString()));

		switchToClassicUI(user);
	}

	@Override
	protected void cleanUpDataAfterClass() {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(user);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(user);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14016709")
	@Test(description = "Verify that the release name field in the header is a link to release stats page")
	public void verifyReleaseNameLinkInPageHeader() {
		// GIVEN
		// Data created in generateDataBeforeClass()

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(releaseTracker)
				.releaseDashboardComponent(
						c -> c.assertReleaseSection(releaseTrackerItemId,
								s -> s.isHeaderPointingToReleaseStatsPage(releaseTrackerItemId)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14029927")
	@Test(description = "Verify (state) transition options on release page.")
	public void verifyTransitionOptionsOnReleasePage() {
		// GIVEN
		// Data created in generateDataBeforeClass()

		// WHEN THEN
		getClassicCodebeamerApplication(user)
				.visitTrackerItemReleasePage(releaseTrackerItemId)
				.visit()
				.releaseDashboardComponent(c -> {
					c.getReleaseSection(releaseTrackerItemId).clickTransitionSectionIcon();
					c.getReleaseSection(releaseTrackerItemId).assertThat()
							.isTransitionAvailable(List.of("transition-Start", "transition-Deactivate"));
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14100286")
	@Test(description = "Verify that description is visible in release page's header.")
	public void verifyThatDescriptionIsVisibleInReleasePageHeader() {
		// GIVEN
		// Data created in generateDataBeforeClass()

		// WHEN THEN
		getClassicCodebeamerApplication(user)
				.visitTrackerItemReleasePage(releaseTrackerItemId)
				.releaseDashboardComponent(c -> {
					c.getReleaseSection(releaseTrackerItemId).assertThat().isReleaseDescriptionVisible();
				});
	}
}