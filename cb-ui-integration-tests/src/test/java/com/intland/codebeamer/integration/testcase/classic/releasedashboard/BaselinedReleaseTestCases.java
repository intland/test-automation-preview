package com.intland.codebeamer.integration.testcase.classic.releasedashboard;

import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.component.actionmenubar.WorkingSetSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.actionmenubar.WorkingSetSelectorComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.AbstractReleaseDashboardReleaseComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardReleaseAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardReleaseComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardSprintAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardSprintComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseItemActionBarAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "BaselinedReleaseTestCases")
public class BaselinedReleaseTestCases extends AbstractIntegrationClassicNGTests {

	private static final String RELEASE_FIELD_NAME = "Release";

	private static final String PARENT_FIELD_NAME = "Parent";

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private BaselineApiService baselineApiService;

	private UserApiService userApiService;

	private User user;

	private Project commonProject;

	private Project uniqueProject;

	private Tracker commonReleaseTracker;

	private Tracker uniqueReleaseTracker;

	private TrackerItemId commonReleaseItemId;

	private TrackerItemId commonSprintItemId;

	private Baseline commonBaseline;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService dataManagerService = getDataManagerService();

		userApiService = dataManagerService.getUserApiServiceWithConfigUser();

		user = userApiService
				.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = dataManagerService.getProjectApiService(user);
		trackerApiService = dataManagerService.getTrackerApiService(user);
		trackerItemApiService = dataManagerService.getTrackerItemApiService(user);
		baselineApiService = dataManagerService.getBaselineApiService(user);

		commonProject = projectApiService
				.createProject(getRandomText("Default Test Project"))
				.build();

		commonReleaseTracker = projectApiService
				.findTrackerByName(commonProject, "Releases");

		commonReleaseItemId = trackerItemApiService
				.createTrackerItem(commonReleaseTracker, builder -> builder.name("Release1"));

		trackerItemApiService
				.createTrackerItem(commonReleaseTracker,
						builder -> builder.name("Sprint1")
								.setTrackerItemFor(PARENT_FIELD_NAME, commonReleaseItemId));

		commonSprintItemId = trackerItemApiService
				.createTrackerItem(commonReleaseTracker,
						builder -> builder.name("Sprint1")
								.setTrackerItemFor(PARENT_FIELD_NAME, commonReleaseItemId));

		commonBaseline = baselineApiService
				.createProjectBaseline("Common baseline", commonProject);

		switchToClassicUI(user);
	}

	@Override
	protected void cleanUpDataAfterClass() {
		baselineApiService.deleteBaseline(commonBaseline.id());
		projectApiService.deleteProject(commonProject);
		userApiService.disableUser(user);
	}

	private void generateDataBeforeMethod() {
		uniqueProject = projectApiService
				.createProject(getRandomText("Unique Default Test Project"))
				.build();

		uniqueReleaseTracker = projectApiService
				.findTrackerByName(uniqueProject, "Releases");
	}

	private void cleanUpDataAfterMethod() {
		projectApiService.deleteProject(uniqueProject);
	}

	private WorkingSetSelectorAssertions assertNavigatingBackToReleasePageWorkingSetContainsCommonBaseline(
			TrackerItemTraceabilitySectionComponent traceabilitySection, TrackerItemId trackerItemId) {
		return traceabilitySection
				.clickOnCurrentItem()
				.redirectedToReleasePage(trackerItemId, commonBaseline)
				.getActionMenuBarComponent()
				.getWorkingSetSelector()
				.assertThat()
				.workingSetContainsBaseline(commonBaseline.name());
	}

	private WorkingSetSelectorAssertions assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForSprint(
			TrackerItemTraceabilitySectionComponent traceabilitySection) {
		return assertNavigatingBackToReleasePageWorkingSetContainsCommonBaseline(traceabilitySection, commonSprintItemId);
	}

	private WorkingSetSelectorAssertions assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForRelease(
			TrackerItemTraceabilitySectionComponent traceabilitySection) {
		return assertNavigatingBackToReleasePageWorkingSetContainsCommonBaseline(traceabilitySection, commonReleaseItemId);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862118")
	@Test(description = "Verify that release item has backlog section")
	public void verifyReleaseHaveBacklogSection() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker)
				.releaseDashboardComponent(c -> c.assertThat()
						.hasBacklogSectionForReleaseItem(commonReleaseItemId));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862288")
	@Test(description = "Verify that 'Baselines And Branches' button is visible")
	public void verifyVisibilityOfBaselinesAndBranchesButton() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker)
				.actionBarComponent(c -> c.assertButtons()
						.hasBaselinesAndBranchesButton());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13893157")
	@Test(description = "Verify that 'Baselines And Branches' button is visible when multiple sprints are present")
	public void verifyVisibilityOfBaselinesAndBranchesButtonWithMultipleSprints() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService
				.createTaskTracker(uniqueProject, "Task")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId releaseItemId2 = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release2"));

		TrackerItemId releaseItemId3 = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release3"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker,
						builder -> builder.name("Sprint1")
								.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		TrackerItemId sprint2Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker,
						builder -> builder.name("Sprint2")
								.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		TrackerItemId sprint3Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint3")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId2));

		TrackerItemId sprint4Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint4")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId3));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 1")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 2")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId2));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 3")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 4")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprint2Id));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Item 5")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprint3Id));

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(
							a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of(sprintId, sprint2Id))
									.hasSprintSectionsForReleaseItem(releaseItemId2, List.of(sprint3Id))
									.hasSprintSectionsForReleaseItem(releaseItemId3, List.of(sprint4Id))
					);

			getClassicCodebeamerApplication(user)
					.visitTrackerItemReleasePage(releaseItemId)
					.assertReleaseDashboardComponent(a -> a.hasBacklogSectionForReleaseItem(releaseItemId))
					.releaseDashboardComponent(c -> c.assertReleaseSection(releaseItemId,
									ReleaseDashboardReleaseAssertions::canBeOpenedAndClosed)
							.releaseSection(releaseItemId, ReleaseDashboardReleaseComponent::expand)
							.assertSprintSection(sprintId, ReleaseDashboardSprintAssertions::canBeOpenedAndClosed))
					.assertReleaseActionBarComponent(ReleaseItemActionBarAssertions::hasBaselinesAndBranchesButton);
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862163")
	@Test(description = "Verify that release section can be expanded")
	public void verifyReleaseSectionCanBeExpanded() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker)
				.releaseDashboardComponent(c -> c.getReleaseSection(commonReleaseItemId)
						.expand()
						.assertThat()
						.isExpanded());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862224")
	@Test(description = "Verify that release section can be closed")
	public void verifyReleaseSectionCanBeClosed() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker)
				.releaseDashboardComponent(c -> {
					c.releaseSection(commonReleaseItemId, s -> {
						s.expand()
								.assertThat()
								.isExpanded();
						s.close()
								.assertThat()
								.isClosed();
					});
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862213")
	@Test(description = "Verify that sprint section can be expanded")
	public void verifySprintSectionCanBeExpanded() {
		// GIVEN
		generateDataBeforeMethod();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker,
						builder -> builder.name("Sprint2")
								.setTrackerItemFor(PARENT_FIELD_NAME, sprintId));

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.releaseDashboardComponent(c -> c.releaseSection(releaseItemId, ReleaseDashboardReleaseComponent::expand)
							.sprintSection(sprintId, s -> s.expand()
									.assertThat()
									.isExpanded()));
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862270")
	@Test(description = "Verify that sprint section can be closed")
	public void verifySprintSectionCanBeClosed() {
		// GIVEN
		generateDataBeforeMethod();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker,
						builder -> builder.name("Sprint2")
								.setTrackerItemFor(PARENT_FIELD_NAME, sprintId));

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.releaseDashboardComponent(c -> {
						c.releaseSection(releaseItemId, ReleaseDashboardReleaseComponent::expand);
						c.assertSprintSection(sprintId, ReleaseDashboardSprintAssertions::canBeOpenedAndClosed);
					});
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13862287")
	@Test(description = "Verify that right panel displays correct navigation tree")
	public void verifyRightPanelDisplaysCorrectNavigationTree() {
		// GIVEN
		generateDataBeforeMethod();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		TrackerItemId sprint2Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker,
						builder -> builder.name("Sprint2")
								.setTrackerItemFor(PARENT_FIELD_NAME, sprintId));

		// WHEN / THEN
		LinkedHashMap<Integer, Integer> expectedTreeStructure = new LinkedHashMap<>();
		expectedTreeStructure.put(releaseItemId.id(), 0);
		expectedTreeStructure.put(sprintId.id(), 1);
		expectedTreeStructure.put(sprint2Id.id(), 2);

		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.releasesRightPanelComponent(
							c -> c.releasesNavigationTree(t -> t.assertThat()
									.checkTreeStructure(expectedTreeStructure)));
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13937844")
	@Test(description = "Verify that release and sprint structure is valid after baseline was created")
	public void verifyThatReleasesAndSprintsAreListedCorrectlyOnReleaseDashboardInBaselineMode() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService
				.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Task 1")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintId));

		baselineApiService
				.createProjectBaseline("My Baseline", uniqueProject);

		TrackerItemId releaseItem2Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release 2"));

		trackerItemApiService.updateTrackerItem(sprintId, builder -> builder
				.setTrackerItemFor(PARENT_FIELD_NAME, releaseItem2Id));

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of())
							.hasSprintSectionsForReleaseItem(releaseItem2Id, List.of(sprintId)));

			getClassicCodebeamerApplication(user)
					.visitTrackerItemReleasePage(releaseItem2Id)
					.assertReleaseDashboardComponent(a -> a.hasSprintSectionsForReleaseItem(releaseItem2Id, List.of(
							sprintId)))
					.releaseDashboardComponent(c -> c.releaseSection(releaseItem2Id, ReleaseDashboardReleaseComponent::expand)
							.assertSprintSection(sprintId, ReleaseDashboardSprintAssertions::canBeOpenedAndClosed))
					.assertReleaseActionBarComponent(ReleaseItemActionBarAssertions::hasBaselinesAndBranchesButton);

			getClassicCodebeamerApplication(user)
					.visitTrackerItemReleasePage(sprintId)
					.assertReleaseDashboardComponent(a -> a.hasSprintSections(List.of(sprintId)))
					.releaseDashboardComponent(c -> c.assertSprintSection(sprintId,
							ReleaseDashboardSprintAssertions::canBeOpenedAndClosed))
					.assertReleaseActionBarComponent(ReleaseItemActionBarAssertions::hasBaselinesAndBranchesButton);
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link="https://codebeamer.com/cb/item/13993289")
	@Test(description = "Check if the release is editable in baseline mode")
	public void verifyReleaseTraceabilityOpenedWithIcon() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
 				.visitReleasesPage(commonReleaseTracker, commonBaseline)
				.releaseDashboardComponent(releaseDashboardComponent -> {
					releaseDashboardComponent.getReleaseSection(commonReleaseItemId)
							.openTrackerDependenciesInTraceability()
							.assertTraceabilitySection(TrackerItemTraceabilitySectionAssertions::isExpanded)
							.assertFieldFormComponent(TrackerItemFieldFormAssertions::isNotEditable)
							.actionBarComponentAssertion(TrackerItemActionBarAssertions::isEmpty);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13993341")
	@Test(description = "Verify that release page in baseline mode looks the same as the original tracker in time of the baseline creation")
	public void verifyReleaseDashboardBaselineModeWithMultipleSprintsAndReleases() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService
				.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId releaseItem2Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release2"));

		TrackerItemId releaseItem3Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release3"));

		TrackerItemId sprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint1")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		TrackerItemId sprint2Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint2")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItem2Id));

		TrackerItemId sprint3Id = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint3")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItem3Id));

		TrackerItemId newSprintId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("NewSprint")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		TrackerItemId sprintToMoveId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("SprintToMove")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItem3Id));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("My Task 1")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintId));

		Baseline baseline = baselineApiService
				.createProjectBaseline("My Baseline", uniqueProject);

		// WHEN / THEN
		LinkedHashMap<Integer, Integer> expectedTreeStructure = new LinkedHashMap<>();
		expectedTreeStructure.put(releaseItemId.id(), 0);
		expectedTreeStructure.put(newSprintId.id(), 1);
		expectedTreeStructure.put(sprintId.id(), 1);
		expectedTreeStructure.put(releaseItem2Id.id(), 0);
		expectedTreeStructure.put(sprint2Id.id(), 1);
		expectedTreeStructure.put(releaseItem3Id.id(), 0);
		expectedTreeStructure.put(sprint3Id.id(), 1);
		expectedTreeStructure.put(sprintToMoveId.id(), 1);

		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(
							a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of(newSprintId, sprintId))
									.hasSprintSectionsForReleaseItem(releaseItem2Id, List.of(sprint2Id))
									.hasSprintSectionsForReleaseItem(releaseItem3Id, List.of(sprint3Id, sprintToMoveId)))
					.switchToBaselineMode(baseline)
					.assertActionMenuBar(a -> a.hasBaselineColorGradient()
							.hasBaselineNamePresent(baseline.name()))
					.assertReleaseDashboardComponent(
							a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of(newSprintId, sprintId))
									.hasBacklogSectionForReleaseItem(releaseItemId)
									.hasSprintSectionsForReleaseItem(releaseItem2Id, List.of(sprint2Id))
									.hasBacklogSectionForReleaseItem(releaseItem2Id)
									.hasSprintSectionsForReleaseItem(releaseItem3Id, List.of(sprint3Id, sprintToMoveId))
									.hasBacklogSectionForReleaseItem(releaseItem3Id))
					.releaseDashboardComponent(c -> c.assertReleaseSection(releaseItemId, s -> {
						s.canBeOpenedAndClosed();
						s.hasVersionBadge();
						s.isLinkPointingToBaseline(baseline.id());
					}).assertReleaseSection(releaseItem2Id, s -> {
						s.canBeOpenedAndClosed();
						s.hasVersionBadge();
						s.isLinkPointingToBaseline(baseline.id());
					}).assertReleaseSection(releaseItem3Id, s -> {
						s.canBeOpenedAndClosed();
						s.hasVersionBadge();
						s.isLinkPointingToBaseline(baseline.id());
					})).releasesRightPanelComponent(
							c -> c.releasesNavigationTree(t -> t.assertThat()
									.checkTreeStructure(expectedTreeStructure)));
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14014933")
	@Test(description = "Verify that if user deletes sprint after creating baseline, then in baseline mode the sprint is present with the correct item")
	public void verifyTaskIsVisibleWhenSprintIsExpanded() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService
				.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Release1"));

		TrackerItemId sprintToDelete = trackerItemApiService
				.createTrackerItem(uniqueReleaseTracker, builder -> builder.name("Sprint to delete")
						.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("Task backlog")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		TrackerItemId taskToDelete = trackerItemApiService
				.createTrackerItem(taskTracker, builder -> builder.name("Task to delete")
						.description("Description")
						.setTrackerItemFor(RELEASE_FIELD_NAME, sprintToDelete));

		Baseline baseline = baselineApiService
				.createProjectBaseline("My Baseline", uniqueProject);

		trackerItemApiService.deleteTrackerItem(sprintToDelete);

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(
							a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of()))
					.switchToBaselineMode(baseline)
					.releaseDashboardComponent(c -> {
						c.releaseSection(releaseItemId,
										ReleaseDashboardReleaseComponent::expand)
								.assertThat()
								.hasSprintSections(List.of(sprintToDelete));
						c.sprintSection(sprintToDelete, ReleaseDashboardSprintComponent::expand)
								.assertSprintSection(sprintToDelete, s -> s.hasTrackerItems(List.of(taskToDelete)));
					});
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link="https://codebeamer.com/cb/item/13804205")
	@Test(description = "Check if release page stays in baseline mode when navigating to it via a release's open traceability button then traceability table's link")
	public void verifyLinkOfATrackerItemInTheTraceabilityTabInBaselinedModeNavigatesToBaselineVersionOfItAfterClickingTraceabilityIconOfAReleaseTrackerItem() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker, commonBaseline)
				.getReleaseDashboardComponent()
				.getReleaseSection(commonReleaseItemId)
				.openTrackerDependenciesInTraceability()
				.traceabilitySection(this::assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForRelease);
	}

	@TestCase(link="https://codebeamer.com/cb/item/13804261")
	@Test(description = "Check if release page stays in baseline mode when navigating to it via a sprint's open traceability button then traceability table's link")
	public void verifyLinkOfATrackerItemInTheTraceabilityTabInBaselinedModeNavigatesToBaselineVersionOfItAfterClickingTraceabilityIconOfASprintTrackerItem() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker, commonBaseline)
				.getReleaseDashboardComponent()
				.releaseSection(commonReleaseItemId, AbstractReleaseDashboardReleaseComponent::expand)
				.getSprintSection(commonSprintItemId)
				.openTrackerDependenciesInTraceability()
				.traceabilitySection(this::assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForSprint);
	}

	@TestCase(link="https://codebeamer.com/cb/item/13804256")
	@Test(description = "Check if release page stays in baseline mode when navigating to it via a release context menu's traceability button then traceability table's link")
	public void verifyLinkOfATrackerItemInTheTraceabilityTabInBaselinedModeNavigatesToBaselineVersionOfItAfterClickingThreeDotsOfAReleaseTrackerItemThenChooseTraceability() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker, commonBaseline)
				.getReleaseDashboardComponent()
				.getReleaseSection(commonReleaseItemId)
				.openMoreMenu()
				.openTraceability()
				.traceabilitySection(this::assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForRelease);
	}

	@TestCase(link="https://codebeamer.com/cb/item/13804266")
	@Test(description = "Check if release page stays in baseline mode when navigating to it via a sprint context menu's traceability button then traceability table's link")
	public void verifyLinkOfATrackerItemInTheTraceabilityTabInBaselinedModeNavigatesToBaselineVersionOfItAfterClickingThreeDotsOfASprintTrackerItemThenChooseTraceability() {
		// GIVEN
		// WHEN / THEN
		getClassicCodebeamerApplication(user)
				.visitReleasesPage(commonReleaseTracker, commonBaseline)
				.getReleaseDashboardComponent()
				.releaseSection(commonReleaseItemId, AbstractReleaseDashboardReleaseComponent::expand)
				.getSprintSection(commonSprintItemId)
				.openMoreMenu()
				.openTraceability()
				.traceabilitySection(this::assertNavigatingBackToReleasePageWorkingSetContainsCommonBaselineForSprint);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14016343")
	@Test(description = "If user tries to navigate to HEAD from a baseline version of an already deleted release, 'Access Denied' error is shown.")
	public void verifyThatOpeningDeletedReleaseOnHeadThrowsPermissionError() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Release1"));

		TrackerItemId releaseToDelete = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("ReleaseToDelete"));

		TrackerItemId sprintToMove = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Sprint to move").
						setTrackerItemFor(PARENT_FIELD_NAME, releaseToDelete));

		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task backlog")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, releaseItemId));

		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task to move")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, sprintToMove));

		Baseline baseline = baselineApiService.createProjectBaseline("My Baseline", uniqueProject);

		trackerItemApiService.updateTrackerItem(sprintToMove,
				builder -> builder.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService.deleteTrackerItem(releaseToDelete);

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of(sprintToMove)));

			getClassicCodebeamerApplication(user)
					.visitTrackerItemReleasePage(releaseToDelete, baseline)
					.actionMenuBarComponent(
							c -> c.workingSetSelector(WorkingSetSelectorComponent::switchToHeadRevision))
					.assertPage(page -> page.assertGlobalMessagesComponent(
							a -> a.hasErrorWithMessage("Access Denied")));
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14074406")
	@Test(description = "If user tries to navigate to the baseline version of an item that was created after the baseline, 'Access Denied' error is thrown.")
	public void verifyThatOpeningReleaseAddedAfterBaselineCreationThrowsPermissionError() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId release1 = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Release1"));

		TrackerItemId sprint1 = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Sprint1").
						setTrackerItemFor(PARENT_FIELD_NAME, release1));

		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task 1")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, release1));

		trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task 2")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, sprint1));

		Baseline baseline = baselineApiService.createProjectBaseline("My Baseline", uniqueProject);

		TrackerItemId release2 = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Release2"));

		trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Sprint1").
						setTrackerItemFor(PARENT_FIELD_NAME, release2));

		trackerItemApiService.updateTrackerItem(sprint1,
				builder -> builder.setTrackerItemFor(PARENT_FIELD_NAME, release2));

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.navigateToReleaseItem(release2)
					.switchToBaselineMode(baseline)
					.assertGlobalMessagesComponent(a -> a.hasErrorWithMessage("Access Denied"));
		} finally {
			cleanUpDataAfterMethod();
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/14075862")
	@Test(description = "If user creates a baseline before deleting a release, then that release and the contained sprints and items are still available in the baseline version.")
	public void verifyThatMovedReleaseIsVisibleInBaselineMode() {
		// GIVEN
		generateDataBeforeMethod();

		Tracker taskTracker = trackerApiService.createTaskTracker(uniqueProject, "My Task Tracker")
				.buildAndAdd();

		TrackerItemId releaseItemId = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Release1"));

		TrackerItemId releaseToDelete = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("ReleaseToDelete"));

		TrackerItemId sprintToMove = trackerItemApiService.createTrackerItem(uniqueReleaseTracker,
				builder -> builder.name("Sprint to move").
						setTrackerItemFor(PARENT_FIELD_NAME, releaseToDelete));

		TrackerItemId taskInReleaseToDelete = trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task in release to delete")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, releaseToDelete));

		TrackerItemId taskToMove = trackerItemApiService.createTrackerItem(taskTracker, builder -> builder.name("Task to move")
				.description("Description")
				.setTrackerItemFor(RELEASE_FIELD_NAME, sprintToMove));

		Baseline baseline = baselineApiService.createProjectBaseline("My Baseline", uniqueProject);

		trackerItemApiService.updateTrackerItem(sprintToMove,
				builder -> builder.setTrackerItemFor(PARENT_FIELD_NAME, releaseItemId));

		trackerItemApiService.deleteTrackerItem(releaseToDelete);

		// WHEN / THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitReleasesPage(uniqueReleaseTracker)
					.assertReleaseDashboardComponent(a -> a.hasSprintSectionsForReleaseItem(releaseItemId, List.of(sprintToMove)))
					.navigateToReleaseItem(releaseToDelete)
					.switchToBaselineMode(baseline)
					.releaseDashboardComponent(c -> {
						c.releaseSection(releaseToDelete, ReleaseDashboardReleaseComponent::expand)
								.assertThat()
								.hasSprintSections(List.of(sprintToMove));
						c.assertBacklogSection(releaseToDelete, a -> a.hasBacklogItems(List.of(taskInReleaseToDelete)));
						c.sprintSection(sprintToMove, ReleaseDashboardSprintComponent::expand)
								.assertSprintSection(sprintToMove, a -> a.hasTrackerItems(List.of(taskToMove)));
					});
		} finally {
			cleanUpDataAfterMethod();
		}
	}
}
