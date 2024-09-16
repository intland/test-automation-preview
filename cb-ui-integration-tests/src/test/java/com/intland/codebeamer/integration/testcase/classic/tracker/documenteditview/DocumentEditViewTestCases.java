package com.intland.codebeamer.integration.testcase.classic.tracker.documenteditview;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.applicationconfiguration.ApplicationConfigurationRestApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "DocumentEditViewTestCases")
public class DocumentEditViewTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ApplicationConfigurationRestApiService applicationConfigurationService;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		applicationConfigurationService = new ApplicationConfigurationRestApiService(getApplicationConfiguration());
		project = projectApiService.createProjectFromTemplate();
	}

	@Override
	protected void cleanUpDataAfterClass() {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13834622")
	@Test(description = "Check the absence of 'Document Edit View' button of the tracker action menu bar when this feature is disabled")
	public void verifyDocumentEditViewIsNotVisibleWhenEnableDocumentEditViewIsFalseInApplicationConfiguration() {
		try {
			// GIVEN
			JSONObject jsonObject = new JSONObject("{\"enableDocumentEditView\" : false }");
			String configurationString = applicationConfigurationService.getConfigurationString("features", jsonObject);
			applicationConfigurationService.saveApplicationConfiguration(configurationString);

			Tracker tracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Task"));

			// WHEN THEN
			getClassicCodebeamerApplication(activeUser)
					.visitTrackerTableViewPage(tracker)
					.actionMenuBarComponent()
					.assertThat()
					.hasNoDocumentEditViewButton();
		} finally {
			applicationConfigurationService.clearApplicationConfigParameter("features");
		}
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13834611")
	@Test(description = "Check the presence of 'Document Edit View' button of the tracker action menu bar when this feature is enabled")
	public void verifyDocumentEditViewIconIsVisibleWhenEnableDocumentEditViewIsTrueInApplicationConfiguration() {
		try {
			// GIVEN
			JSONObject jsonObject = new JSONObject("{\"enableDocumentEditView\" : true }");
			String configurationString = applicationConfigurationService.getConfigurationString("features", jsonObject);
			applicationConfigurationService.saveApplicationConfiguration(configurationString);

			Tracker tracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Task"));

			// WHEN THEN
			getClassicCodebeamerApplication(activeUser)
					.visitTrackerTableViewPage(tracker)
					.actionMenuBarComponent()
					.assertThat()
					.hasDocumentEditViewButton();
		} finally {
			applicationConfigurationService.clearApplicationConfigParameter("features");
		}
	}
}
