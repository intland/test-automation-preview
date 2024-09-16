package com.intland.codebeamer.integration.testcase.classic.releasedashboard;

import com.intland.codebeamer.integration.api.builder.association.AssociationType;
import com.intland.codebeamer.integration.api.service.association.AssociationApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionComponent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardReleaseComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "BaselinedReleaseTraceabilityTestCases")
public class BaselinedReleaseTraceabilityTestCases extends AbstractIntegrationClassicNGTests {

	private ProjectApiService projectApiService;
	private UserApiService userApiService;
	private BaselineApiService baselineApiService;
	private TrackerItemApiService trackerItemApiService;
	private AssociationApiService associationApiService;

	private Project project;
	private Project scrumProject;
	private User user;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService dataManagerService = getDataManagerService();
		userApiService = dataManagerService.getUserApiServiceWithConfigUser();
		user = userApiService
				.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = dataManagerService.getProjectApiService(user);
		baselineApiService = dataManagerService.getBaselineApiService(user);
		trackerItemApiService = dataManagerService.getTrackerItemApiService(user);
		associationApiService = dataManagerService.getAssociationApiService(user);

		switchToClassicUI(user);
	}

	@Override
	protected void cleanUpDataAfterClass() {
		userApiService.disableUser(user);
	}

	@BeforeMethod
	private void generateDataBeforeMethod() {
		project = projectApiService.createProjectFromTemplate(getRandomText("My project"), ProjectTemplate.AGILE_SCRUM);
	}

	@AfterMethod
	private void cleanUpDataAfterMethod() {
		projectApiService.deleteProject(project);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13776268")
	@Test(description = "Verify that the user is able to visit the Traceability Tab of a Release Tracker Item in Project Baseline")
	public void visitReleaseTrackerItemTraceabilityPageInBaselineModeBasedOnName() {
		// GIVEN
		final String baselineName = "Default Project Baseline";
		final String releaseName = "Multimedia and Navigation Release 1";
		final String releaseTrackerName = "Releases";
		scrumProject = projectApiService.createProjectFromTemplate("Scrum Template " + getRandomString(),
				ProjectTemplate.AGILE_SCRUM);
		Tracker releasesTracker = projectApiService.findTrackerByName(scrumProject, releaseTrackerName);
		TrackerItem releaseItem = trackerItemApiService.findTrackerItemByName(releasesTracker, releaseName);

		// WHEN / THEN
		try {
			Baseline baseline = baselineApiService
					.createProjectBaseline(baselineName, scrumProject);

			getClassicCodebeamerApplication(user)
					.visitReleasesPage(releasesTracker, baseline)
					.releaseDashboardComponent(c -> c
							.releaseSection(releaseItem.id(), s -> s.openMoreMenu()
									.openTraceability()
									.actionMenuBarComponent(actionMenuBar -> {
										actionMenuBar.assertThat().summaryEqualsTo(releaseName);
										actionMenuBar.assertThat().idEqualsTo(releaseItem.id().id());
									})
									.assertFieldFormComponent(f -> f
											.textField("Tracker", a -> a.is(releaseTrackerName))
											.choiceField("Status", a -> a.is("Active")))
									.assertTraceabilitySection((a -> a
											.hasNoUpstreamReference()
											.hasDownstreamReference(32)
									))));
		} finally {
			projectApiService.deleteProject(scrumProject);
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13798892")
	@Test(description = "Verify that task traceability tab of release item opened with 'Traceability' button in baseline mode has no edit button")
	public void verifyReleaseItemIsNotEditableInBaselineModeAfterClickingTraceabilityIconOfAReleaseTrackerItem() {
		// GIVEN
		Baseline baseline = baselineApiService.createProjectBaseline("My baseline", project);
		Tracker tracker = projectApiService.findTrackerByName(project, "Releases");
		TrackerItem release = trackerItemApiService.findTrackerItemByName(tracker, "Multimedia and Navigation Release 1");

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(tracker)
				.switchToBaselineMode(baseline)
				.releaseDashboardComponent(c -> c.releaseSection(release.id(),
						s -> s.openTrackerDependenciesInTraceability()
								.actionBarComponent()
								.assertThat()
								.editButtonIsHidden()));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13799429")
	@Test(description = "Verify that task traceability tab of sprint item opened with 'Traceability' button in baseline mode has no edit button")
	public void verifyReleaseItemIsNotEditableInBaselineModeAfterClickingTraceabilityIconOfASprintTrackerItem() {
		// GIVEN
		Baseline baseline = baselineApiService.createProjectBaseline("My baseline", project);
		Tracker tracker = projectApiService.findTrackerByName(project, "Releases");
		TrackerItem release = trackerItemApiService.findTrackerItemByName(tracker, "Multimedia and Navigation Release 1");
		TrackerItem sprint = trackerItemApiService.findTrackerItemByName(tracker, "Sprint 1.2");

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(tracker)
				.switchToBaselineMode(baseline)
				.releaseDashboardComponent(c -> {
					c.releaseSection(release.id(), ReleaseDashboardReleaseComponent::expand);
					c.sprintSection(sprint.id(), s -> s.openTrackerDependenciesInTraceability()
							.actionBarComponent()
							.assertThat()
							.editButtonIsHidden());
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13725215")
	@Test(description = "Verify that Description of a Tracker Item is not visible on a Release Item's Traceability Page in Baseline mode when the Show Description checkbox is unchecked")
	public void verifyDescriptionsAreNotVisibleOnAReleaseItemTraceabilityPageInBaselineModeWhenShowDescriptionIsUnchecked() {
		// GIVEN
		final String trackerName = "Test Cases";
		final String releaseTrackerName = "Releases";
		final String trackerItemName = "Default Test Case Tracker Item";
		final String releaseTrackerItemName = "Default Release";
		final String description = "Description";
		project = projectApiService.createProjectFromTemplate(getRandomText("Default project"));
		Tracker releaseTracker = projectApiService.findTrackerByName(project, releaseTrackerName);
		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
			.name(trackerItemName)
			.description(description));
		TrackerItemId releaseTrackerItem = trackerItemApiService.createTrackerItem(project, releaseTrackerName, builder -> builder
			.name(releaseTrackerItemName)
			.description("Default Release Description"));
		associationApiService.createAssociation(associationBuilder -> associationBuilder
			.from(trackerItem)
			.to(releaseTrackerItem)
			.type(AssociationType.DEPENDS)
		);
		Baseline baseline = baselineApiService
			.createProjectBaseline("My Baseline", project);

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
			.visitTrackerItemReleasePageTraceabilityTab(releaseTracker.id(), releaseTrackerItem, baseline)
			//First we need to check the checkbox to later uncheck it
			.traceabilitySection(TrackerItemTraceabilitySectionComponent::checkShowDescriptionCheckbox)
			.traceabilitySection(TrackerItemTraceabilitySectionComponent::checkShowDescriptionCheckbox)
			.assertTraceabilitySection(a->a
				.hasDownstreamReference(1)
				.hasNoUpstreamReference()
				.verifyTrackerItemTraceabilityDescriptionIsHidden(trackerItem));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13799430")
	@Test(description = "Verify that task traceability tab of sprint item opened from context menu in baseline mode has no edit button")
	public void verifyReleaseItemIsNotEditableInBaselineModeAfterClickingThreeDotsOfASprintTrackerItemThenChooseTraceability() {
		// GIVEN
		Baseline baseline = baselineApiService.createProjectBaseline("My baseline", project);
		Tracker tracker = projectApiService.findTrackerByName(project, "Releases");
		TrackerItem release = trackerItemApiService.findTrackerItemByName(tracker, "Multimedia and Navigation Release 1");
		TrackerItem sprint = trackerItemApiService.findTrackerItemByName(tracker, "Sprint 1.2");

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(tracker)
				.switchToBaselineMode(baseline)
				.getReleaseDashboardComponent()
				.releaseSection(release.id(), ReleaseDashboardReleaseComponent::expand)
				.getSprintSection(sprint.id())
				.openMoreMenu()
				.openTraceability()
				.actionBarComponent()
				.assertThat()
				.editButtonIsHidden();
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13798894")
	@Test(description = "Verify that task traceability tab of release item opened from context menu in baseline mode has no edit button")
	public void verifyReleaseItemIsNotEditableInBaselineModeAfterClickingThreeDotsOfAReleaseTrackerItemThenChooseTraceability() {
		// GIVEN
		Baseline baseline = baselineApiService.createProjectBaseline("My baseline", project);
		Tracker tracker = projectApiService.findTrackerByName(project, "Releases");
		TrackerItem release = trackerItemApiService.findTrackerItemByName(tracker, "Multimedia and Navigation Release 1");

		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(tracker)
				.switchToBaselineMode(baseline)
				.getReleaseDashboardComponent()
				.getReleaseSection(release.id())
				.openMoreMenu()
				.openTraceability()
				.actionBarComponent()
				.assertThat()
				.editButtonIsHidden();
	}
}
