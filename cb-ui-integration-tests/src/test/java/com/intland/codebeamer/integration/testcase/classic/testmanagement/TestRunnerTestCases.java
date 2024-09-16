package com.intland.codebeamer.integration.testcase.classic.testmanagement;

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.testmanagement.testrun.TestRunApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "TestRunnerTestCases")
public class TestRunnerTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private TestRunApiService testRunApiService;

	private static final String LANGUAGE_HUNGARIAN = "Hungarian";

	private static final String LANGUAGE_ENGLISH = "English";

	private static final String COUNTRY_HUNGARY = "Hungary";

	private static final String COUNTRY_ENGLAND = "England";

	private static final String COUNTRY = "Country";

	private static final String LANGUAGE = "Language";

	private static final String BUGS = "Bugs";


	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);
		testRunApiService = getDataManagerService().getTestRunApiService(activeUser);
		project = projectApiService.createProjectFromTemplate(getRandomText("Scrum Project"), ProjectTemplate.AGILE_SCRUM);
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13282314")
	@Test(description = "Verify field dependency, in reverse, in reporting bugs from test runner")
	public void verifyFieldDependencyInReportingBugsFromTestRunner() {
		// GIVEN
		Tracker bugs = trackerApiService.createDefaultBugTracker(project,  getRandomText(BUGS));
		trackerApiService.updateTracker(project, bugs.name())
				.createChoiceField(COUNTRY, List.of(
						new ChoiceOption(1, COUNTRY_HUNGARY),
						new ChoiceOption(2, COUNTRY_ENGLAND)))
				.buildAndAdd();
		trackerApiService.updateTracker(project, bugs.name())
				.createChoiceField(LANGUAGE, List.of(
						new ChoiceOption(1, LANGUAGE_HUNGARIAN),
						new ChoiceOption(2, LANGUAGE_ENGLISH)), trackerFieldBuilder -> trackerFieldBuilder
						.dependsOnField(COUNTRY,
								builder -> builder
										.addDependencyForOptions(List.of("1"), List.of("1"))
										.addDependencyForOptions(List.of("2"), List.of("2"))
						)).updateChoiceField("Release", List.of(), trackerFieldBuilder -> trackerFieldBuilder
						.mandatory(false))
				.buildAndAdd();

		Tracker testRunTracker = projectApiService.findTrackerByName(project, "Test Runs");
		Tracker testCaseTracker = projectApiService.findTrackerByName(project, "Test Cases");
		TrackerItemId testCase = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name("TC"));
		TrackerItemId newTestRun = testRunApiService.createTestRunForTestCasesOrTestSets(testRunTracker.id(),
				t -> t.testCaseIds(testCase));

		// WHEN, THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTestRunnerPage(testRunTracker.id(), newTestRun)
				.failTestRunWithoutTestSteps(reportBugComponent -> reportBugComponent
						.createNewBugTab()
						.setTracker(bugs.name())
						.next()
						.redirectedToTrackerItemCreatePage()
						.fieldFormComponent(formComponent -> formComponent
								.setChoiceField(COUNTRY, COUNTRY_ENGLAND))
						.fieldFormComponent(formComponent -> formComponent
								.assertThat().choiceField(LANGUAGE, assertion -> assertion.options(LANGUAGE_ENGLISH, "--"))));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13282318")
	@Test(description = "Verify field dependency, in reverse, in reporting bugs from test runner")
	public void verifyFieldDependencyInReverseInReportingBugsFromTestRunner() {
		// GIVEN
		Tracker bugs = trackerApiService.createDefaultBugTracker(project,  getRandomText(BUGS));
		String bugName = "My bug";
		trackerApiService.updateTracker(project, bugs.name())
				.createChoiceField(COUNTRY, List.of(
						new ChoiceOption(1, COUNTRY_HUNGARY),
						new ChoiceOption(2, COUNTRY_ENGLAND)))
				.buildAndAdd();
		trackerApiService.updateTracker(project, bugs.name())
				.createChoiceField(LANGUAGE, List.of(
						new ChoiceOption(1, LANGUAGE_HUNGARIAN),
						new ChoiceOption(2, LANGUAGE_ENGLISH)), trackerFieldBuilder -> trackerFieldBuilder
						.dependsOnField(COUNTRY,
								builder -> builder
										.addDependencyForOptions(List.of("1"), List.of("1"))
										.addDependencyForOptions(List.of("2"), List.of("2"))
						)).updateChoiceField("Release", List.of(), trackerFieldBuilder -> trackerFieldBuilder
						.mandatory(false))
				.buildAndAdd();

		Tracker testRunTracker = projectApiService.findTrackerByName(project, "Test Runs");
		Tracker testCaseTracker = projectApiService.findTrackerByName(project, "Test Cases");
		TrackerItemId testCase = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name("TC"));
		TrackerItemId newTestRun = testRunApiService.createTestRunForTestCasesOrTestSets(testRunTracker.id(),
				t -> t.testCaseIds(testCase));

		// WHEN, THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTestRunnerPage(testRunTracker.id(), newTestRun)
				.failTestRunWithoutTestSteps(reportBugComponent -> reportBugComponent
						.createNewBugTab()
						.setTracker(bugs.name())
						.next()
						.redirectedToTrackerItemCreatePage()
						.fieldFormComponent(formComponent -> formComponent
								.setChoiceField(COUNTRY, COUNTRY_ENGLAND)
								.setChoiceField(LANGUAGE, LANGUAGE_ENGLISH)
								.setChoiceField(COUNTRY, COUNTRY_HUNGARY)
								.setTextField("Summary", bugName))
						.fieldFormComponent(formComponent -> formComponent
								.assertThat().choiceField(COUNTRY, assertion -> assertion.options(COUNTRY_ENGLAND, COUNTRY_HUNGARY, "--")))
						.save().redirectedToTrackerItemPage()
						.isActive());

		// Check if the created bug exists
		trackerItemApiService.findTrackerItemByName(bugs, bugName);
	}

}
