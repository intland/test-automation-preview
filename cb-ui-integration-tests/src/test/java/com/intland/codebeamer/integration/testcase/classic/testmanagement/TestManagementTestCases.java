package com.intland.codebeamer.integration.testcase.classic.testmanagement;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.AbstractTrackerItemComponentTabAssertion;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemBreadcrumbAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "CustomFieldsTestCases")
public class TestManagementTestCases extends AbstractIntegrationClassicNGTests {

	private Project project;

	private User projectAdmin;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private UserApiService userApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		// Given
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		projectAdmin = userApiService.createUser()
				.addToRegularUserGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);
		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		project = projectApiService.createProjectFromTemplate();
		switchToClassicUI(projectAdmin);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		userApiService.disableUser(projectAdmin);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264573")
	@Test(description = "If an item link inserted to a wiki field, the source item doesn't show up as reference on the target item's page")
	public void insertArtifactLinkIntoCustomWikiTextFieldDoesNotCreateReferenceOnInsertedItem() {
		final String testCaseTrackerName = getRandomText("TestCases");
		final String wikiFieldName = "customWikiField";

		Tracker testCaseTracker = trackerApiService.createTestCaseTracker(project, testCaseTrackerName)
				.createWikiTextField(wikiFieldName)
				.buildAndAdd();
		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReferenceOnInsertedItem(testCaseTracker, wikiFieldName);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264574")
	@Test(description = "If an item link inserted to the Pre-action field, the Test case doesn't show up as reference on the target item's page")
	public void insertArtifactLinkIntoPreActionDoesNotCreateReferenceOnInsertedItem() {
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));
		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReferenceOnInsertedItem(testCaseTracker, "Pre-Action");
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264575")
	@Test(description = "If an item link inserted to the Post-action field, the Test case doesn't show up as reference on the target item's page")
	public void insertArtifactLinkIntoPostActionDoesNotCreateReferenceOnInsertedItem() {
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));
		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReferenceOnInsertedItem(testCaseTracker, "Post-Action");
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264581")
	@Test(description = "If an item link inserted to the Action field, the Test case doesn't show up as reference on the target item's page")
	public void insertArtifactLinkIntoActionDoesNotCreateReferenceOnInsertedItem() {
		// GIVEN
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));

		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Tasks"));
		String taskName = getRandomText("MyTask");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTracker.name(), builder -> builder
				.name(taskName)
				.description(getRandomText()));

		TrackerItemId testCaseItemId = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name("Default Work Item")
				.description(getRandomText())
				.table("Test Steps", tableRowBuilder -> tableRowBuilder
						.addRow(row -> row
								.setWikiTextFor("Action", markupBuilder -> markupBuilder.trackerItemLink(taskId))
								.setTextFor("Expected result", getRandomText())
						)
				));

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(testCaseTracker, testCaseItemId)
				.changeToTestStepsTab()
				.testStepsTabComponent()
				.getTestStep(1)
				.getAction()
				.clickOnInterwikiLink(taskName)
				.redirectedToTrackerItemPage(taskTracker.id(), taskId)
				.traceabilitySection(TrackerItemTraceabilitySectionComponent::open)
				.assertPage(page -> {
					page.assertBreadcrumb(TrackerItemBreadcrumbAssertions::hasNoItemReference);
					page.assertTraceabilitySection(a -> a
							.hasNoUpstreamReference()
							.hasNoDownstreamReference()
					);
					page.assertDownstreamReferencesTab(AbstractTrackerItemComponentTabAssertion::isTabHidden);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264583")
	@Test(description = "If an item link inserted to the Expected result field, the Test case doesn't show up as reference on the target item's page")
	public void insertArtifactLinkIntoExpectedResultDoesNotCreateReferenceOnInsertedItem() {
		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Tasks"));
		String taskName = getRandomText("MyTask");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTracker.name(), builder -> builder
				.name(taskName)
				.description(getRandomText()));

		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));
		TrackerItemId testCaseItemId = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name("Default Work Item")
				.description(getRandomText())
				.table("Test Steps", tableRowBuilder -> tableRowBuilder
				.addRow(row -> row
						.setTextFor("Action", getRandomText())
						.setWikiTextFor("Expected result", markupBuilder -> markupBuilder.trackerItemLink(taskId))
				))
		);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(testCaseTracker, testCaseItemId)
				.changeToTestStepsTab()
				.testStepsTabComponent()
				.getTestStep(1)
				.getExpectedResult()
				.clickOnInterwikiLink(taskName)
				.redirectedToTrackerItemPage(taskTracker.id(), taskId)
				.traceabilitySection(TrackerItemTraceabilitySectionComponent::open)
				.assertPage(page -> {
					page.assertBreadcrumb(TrackerItemBreadcrumbAssertions::hasNoItemReference);
					page.assertTraceabilitySection(a -> a
							.hasNoUpstreamReference()
							.hasNoDownstreamReference()
					);
					page.assertDownstreamReferencesTab(AbstractTrackerItemComponentTabAssertion::isTabHidden);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264436")
	@Test(description = "If an item link inserted to a wiki field, the referred item doesn't show up as reference on the details page")
	public void insertArtifactLinkIntoCustomWikiTextFieldDoesNotCreateReference() {
		final String customWikiFieldName = "customWikiField";
		final Tracker testCaseTracker = trackerApiService.createTestCaseTracker(project, getRandomText("TestCases"))
				.createWikiTextField(customWikiFieldName)
				.buildAndAdd();

		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReference(testCaseTracker, customWikiFieldName);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264446")
	@Test(description = "If an item link inserted to the Pre-action, the referred item doesn't show up as reference on the details page")
	public void insertArtifactLinkIntoPreActionDoesNotCreateReference() {
		final Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));
		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReference(testCaseTracker, "Pre-Action");
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264449")
	@Test(description = "If an item link inserted to the Post-action, the referred item doesn't show up as reference on the details page")
	public void insertArtifactLinkIntoPostActionDoesNotCreateReference() {
		final Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("TestCases"));
		checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReference(testCaseTracker, "Post-Action");
	}

	private void checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReferenceOnInsertedItem(final Tracker testCaseTracker, final String wikiFieldName) {
		// GIVEN
		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Tasks"));
		String taskName = getRandomText("MyTask");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTracker.name(), builder -> builder
				.name(taskName)
				.description(getRandomText()));

		TrackerItemId testCaseItemId = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name("Default Work Item")
				.description(getRandomText())
				.setWikiLinkFor(wikiFieldName, taskId));

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(testCaseTracker, testCaseItemId)
				.fieldFormComponent()
				.wikiField(wikiFieldName)
				.getRichTextValue()
				.clickOnInterwikiLink(taskName)
				.redirectedToTrackerItemPage(taskTracker.id(), taskId)
				.traceabilitySection(TrackerItemTraceabilitySectionComponent::open)
				.assertPage(page -> {
					page.assertBreadcrumb(TrackerItemBreadcrumbAssertions::hasNoItemReference);
					page.assertTraceabilitySection(a -> a
							.hasNoUpstreamReference()
							.hasNoDownstreamReference()
					);
					page.assertDownstreamReferencesTab(AbstractTrackerItemComponentTabAssertion::isTabHidden);
				});
	}

	private void checkArtifactLinkInTestCaseWikiTextFieldDoesNotCreateReference(final Tracker testCaseTracker, final String wikiFieldName) {
		// GIVEN
		TrackerItemId testCaseItemId = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name("Default Work Item")
				.description(getRandomText()));

		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Tasks"));
		String taskName = getRandomText("MyTask");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTracker.name(), builder -> builder
				.name(taskName)
				.description(getRandomText()));

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(testCaseTracker, testCaseItemId)
				.edit()
				.fieldFormComponent(f -> f.setWikiText(wikiFieldName, editor ->
						editor.fill(markupBuilder -> markupBuilder.trackerItemLink(taskId)))
				)
				.save()
				.redirectedToTrackerItemPage()
				.traceabilitySection(TrackerItemTraceabilitySectionComponent::open)
				.assertPage(page -> {
					page.fieldFormComponent()
							.wikiField(wikiFieldName)
							.getRichTextValue()
							.getInterwikiLink(taskName)
							.assertThat()
							.targetsItem(taskId);
					page.assertBreadcrumb(TrackerItemBreadcrumbAssertions::hasNoItemReference);
					page.assertTraceabilitySection(a -> a
							.hasNoUpstreamReference()
							.hasNoDownstreamReference()
					);
					page.assertDownstreamReferencesTab(AbstractTrackerItemComponentTabAssertion::isTabHidden);
				});
	}

}
