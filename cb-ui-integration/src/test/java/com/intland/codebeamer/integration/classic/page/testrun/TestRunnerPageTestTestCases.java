package com.intland.codebeamer.integration.classic.page.testrun;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.testmanagement.testrun.TestRunApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "TestRunPageTestTestCases")
public class TestRunnerPageTestTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private TrackerItemApiService trackerItemApiService;

	private TrackerApiService trackerApiService;

	private ProjectApiService projectApiService;

	private TestRunApiService testRunApiService;

	private Project project;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		UserApiService userService = getDataManagerService().getUserApiServiceWithConfigUser();

		projectAdmin = userService.findUserByName("bond");
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);
		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);
		testRunApiService = getDataManagerService().getTestRunApiService(projectAdmin);
		project = projectApiService.createProjectFromTemplate();
	}

	@Test(description = "Fail a test run and create a new bug")
	public void failTestRunAndCreateNewBug() {
		Tracker testRunTracker = trackerApiService.createDefaultTestRunTracker(project.id(), "Test run");
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project.id(), "Test case");
		Tracker bugTracker1 = trackerApiService.createDefaultBugTracker(project, "Bug1");
		TrackerItemId testCase = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name("TC"));

		TrackerItemId newTestRun = testRunApiService.createTestRunForTestCasesOrTestSets(testRunTracker.id(),
				t -> t.testCaseIds(testCase));


		String bugName = "Created bug from test run";
		getClassicCodebeamerApplication(projectAdmin)
				.visitTestRunnerPage(testRunTracker.id(), newTestRun)
				.failTestRunWithoutTestSteps(reportBugComponent -> reportBugComponent
						.createNewBugTab(createNewBugComponentTab -> createNewBugComponentTab.assertThat()
								.selectableProjectsSizeIs(1)
								.selectableTrackerSizeIs(2)
								.copyPropertiesFromTestRun(true)
								.copyPropertiesFromTestCase(true))
						.createNewBugTab()
						.setTracker("Bug1")
						.copyPropertiesFromTestCase(false)
						.copyPropertiesFromTestRun(false)
						.next()
						.redirectedToTrackerItemCreatePage()
						.fieldFormComponent(formComponent -> formComponent
								.setTextField("Summary", bugName)
								.setWikiText("Description", "Test Description")
								.setChoiceField("Priority", "Low"))
						.save())
				.isActive();

		// Check if the created bug exists
		TrackerItem trackerItemByName = trackerItemApiService.findTrackerItemByName(bugTracker1, bugName);
		trackerItemApiService.deleteTrackerItem(trackerItemByName);
	}

}