/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.tracker.main;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.CreateTrackerItemBuilder;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "TrackerDocumentViewPageTestCases")
public class TrackerDocumentViewPageTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser()
				.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "Check some basic functionalities of TrackerDocumentViewTableComponent")
	public void checkTrackerDocumentViewTableComponentBasicFunctionalities() {
		// Given
		String trackerName = "MyEpicTracker" + RandomStringUtils.randomAlphabetic(5);
		Tracker tracker = trackerApiService
				.createEpicTracker(project, trackerName)
				.buildAndAdd();

		trackerItemApiService.createTrackerItem(tracker,
				builder -> builder
						.name("My Item 2")
						.description("Item desc 2"));

		trackerItemApiService.createTrackerItem(tracker,
				builder -> builder
						.name("My Item 3")
						.description("Item desc 3"));

		// When
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerDocumentViewPage(tracker)
				// THEN
				.trackerDocumentViewTableComponent(c -> {
					c.getRowByTrackerItemName("My Item 2")
							.setName("New Item 2")
							.setDescription("new description 2");
					c.getRowByTrackerItemName("New Item 2").assertThat()
							.nameIs("New Item 2")
							.descriptionIs("new description 2");
					c.getRowByTrackerItemName("My Item 3")
							.setDescription(builder -> builder
									.heading1("Heading 1")
									.text("new description 3"))
							.assertThat()
							.nameIs("My Item 3")
							.descriptionIs("Heading 1\nnew description 3");
				});
	}

	@Test(description = "Check some basic functionalities of TrackerItemTreeComponent")
	public void checkTrackerItemTreeComponentBasicFunctionalities() {
		// Given
		String trackerName = "MyEpicTracker" + RandomStringUtils.randomAlphabetic(5);
		Tracker tracker = trackerApiService
				.createEpicTracker(project, trackerName)
				.buildAndAdd();

		trackerItemApiService.createTrackerItem(tracker,
				builder -> builder
						.name("My Item 1")
						.description("Item desc 1"));

		trackerItemApiService.createTrackerItem(tracker,
				builder -> builder
						.name("My Item 2")
						.description("Item desc 2"));

		trackerItemApiService.createTrackerItem(tracker,
				builder -> builder
						.name("My Item 3")
						.description("Item desc 3"));

		// When
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerDocumentViewPage(tracker)
				// THEN
				.trackerItemTreeComponent(c -> c.assertThat()
						.treeItemsExistInSpecificOrder(trackerName, "My Item 1", "My Item 2", "My Item 3"))
				.trackerItemTreeComponent(c -> {
					c.dragTreeItemBeforeTarget("My Item 3", "My Item 1")
							.assertThat()
							.treeItemsExistInSpecificOrder(trackerName, "My Item 3", "My Item 1", "My Item 2");
					c.dragTreeItemAfterTarget("My Item 1", "My Item 2")
							.assertThat()
							.treeItemsExistInSpecificOrder(trackerName, "My Item 3", "My Item 2", "My Item 1");
				});

	}

	@Test(description = "User is able to a create test run from a test case")
	public void createTestRunFromTestCase() {
		// GIVEN
		final String testCaseName = "tc1";
		final String testRunNameToCreate = "tr1";
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("testCases"));
		trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name(testCaseName)
				.description("description"));

		// WHEN / THEN
		getClassicCodebeamerApplication(activeUser).visitTrackerDocumentViewPage(testCaseTracker)
				.trackerItemTreeComponent(c -> c.selectNode(testCaseName))
				.centerActionBarComponent()
				.openMoreActionMenu()
				.generateTestRuns()
				.generateTestRunsFormComponent(c -> c.summary(testRunNameToCreate))
				.save()
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@Test(description = "User is able to a select a test run tracker to create the test case to")
	public void selectTestRunTrackerAndCreate() {
		// GIVEN
		final String testCaseName = "tc1";
		final String testRunNameToCreate = "tr1";
		final String newTestRunsTrackerName = "newTestRuns";
		Tracker testRunTracker = trackerApiService.createDefaultTestRunTracker(project.id(), newTestRunsTrackerName);
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("testCases"));
		trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), builder -> builder
				.name(testCaseName)
				.description("description")
		);

		// WHEN / THEN
		getClassicCodebeamerApplication(activeUser).visitTrackerDocumentViewPage(testCaseTracker)
				.trackerItemTreeComponent(c -> c.selectNode(testCaseName))
				.centerActionBarComponent()
				.openMoreActionMenu()
				.selectTestRunTracker()
				.getTestRunTrackerSelectorFormComponent()
				.testRunTrackersHistoryTab(
						testRunTrackersHistoryTab -> testRunTrackersHistoryTab.testRunTrackersHistoryTableComponent(
								c -> c.selectTracker(testRunTracker)))
				.submit()
				.generateTestRunsFormComponent(c -> c.summary(testRunNameToCreate))
				.save()
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@Test(description = "User is able to view item details on document view's right pane")
	public void documentViewPropertiesTab() {
		// Given
		final Tracker tracker = trackerApiService.createDefaultTaskTracker(project,  getRandomText("Tasks"));
		final String itemName = "MyTask";
		final TrackerItemId itemId = trackerItemApiService.createTrackerItem(project, tracker.name(), builder -> builder
				.name(itemName)
				.description("description")
				.severity("Major"));

		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerDocumentViewPage(tracker)
				.trackerItemTreeComponent(a -> a.selectTreeItemByName(itemName))
				.rightPaneComponent()
				.changeToPropertiesTab()
				.propertiesTab(properties -> properties
					.detailsSection(details -> details.open()
						.assertWikiLink(assertions -> assertions.hasItemSummaryText(new TrackerItem(itemId, itemName)))
						.fieldFormComponent(form -> form
								.assertThat().choiceField("Severity", a -> a.is("Major"))
						)
					)
				);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13293985")
	@Test(description = "generate test run from 2000 test cases")
	public void generateTestRunFrom2000TestCases() {
		// GIVEN
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project, getRandomText("testCases"));
		Tracker testRunTracker = projectApiService.findTrackerByName(project, "Test Runs");

		List<String> testCaseNames = IntStream.range(0, 2000).mapToObj("test case item %04d"::formatted).toList();

		Stream<Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder>> builders = testCaseNames.stream()
				.map(name -> builder -> builder
						.trackerId(testCaseTracker.id())
						.name(name)
						.description("desc"));

		trackerItemApiService.createTrackerItems(builders.toArray(Function[]::new));

		// WHEN
		Integer testRunId = getClassicCodebeamerApplication(activeUser).visitTrackerDocumentViewPage(testCaseTracker)
				.trackerItemTreeComponent(c -> c.selectNodes(testCaseNames.getFirst(), testCaseNames.getLast()))
				.centerActionBarComponent()
				.openMoreActionMenu()
				.generateTestRuns()
				.generateTestRunsFormComponent(c -> c
						.summary("test run")
						.runOnlyAcceptedTestCases(false))
				.save()
				.overlayMessageBoxComponent(c -> c.assertThat().hasTrackerItemInSuccessMessage())
				.getOverlayMessageBoxComponent()
				.getTrackerItemIdFromSuccessMessage();

		// THEN
		getClassicCodebeamerApplication(activeUser).visitTrackerItemPage(testRunTracker.id(), new TrackerItemId(testRunId))
				.getTestRunResultsPluginComponent()
				.setPagingToAll()
				.assertThat()
				.countOfTestCasesMatch(testCaseNames.size());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13293989")
	@Test(description = "Add 5000 test cases to empty test set")
	public void verifyAdding5000TestCasesToEmptyTestSet() {
		// GIVEN
		Tracker testCaseTracker = projectApiService.findTrackerByName(project, "Test Cases");
		Tracker testSetTracker = projectApiService.findTrackerByName(project, "Test Sets");
		TrackerItemId testSetItem = trackerItemApiService.createTrackerItem(testSetTracker.id(), t -> t.name("test set item"));

		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		// Created test set needs to be visited, since it is not indexed yet by lucene,
		// and it won't be available on the test set search dialog otherwise
		application.visitTrackerItemPage(testSetTracker.id(), testSetItem);

		List<String> testCaseNames = IntStream.range(0, 5000)
				.mapToObj("test case item %04d"::formatted)
				.toList();

		Stream<Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder>> builders = testCaseNames.stream()
				.map(name -> builder -> builder
						.trackerId(testCaseTracker.id())
						.name(name)
						.description("desc"));

		trackerItemApiService.createTrackerItems(builders.toArray(Function[]::new));

		// WHEN
		application.visitTrackerDocumentViewPage(testCaseTracker)
				.trackerItemTreeComponent(c -> c.selectNodes(testCaseNames.getFirst(), testCaseNames.getLast(),
						Duration.ofSeconds(10).toMillis()))
				.centerActionBarComponent()
				.openMoreActionMenu()
				.addTestCasesToTestSets(10000)
				.getAddTestCasesToTestSetsFormComponent()
				.openTestSetHistoryTab(c -> c.selectTestSet(testSetItem), 10000)
				.submit();

		// THEN
		application.visitTrackerItemPage(testSetTracker.id(), testSetItem)
				.getTestCasesAndSetsTabComponent()
				.assertThat()
				.countOfTestCasesMatch(testCaseNames.size());
	}
}
