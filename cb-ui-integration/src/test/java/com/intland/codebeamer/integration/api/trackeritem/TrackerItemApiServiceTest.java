package com.intland.codebeamer.integration.api.trackeritem;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.ReviewHubApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewType;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ThresholdChangeOption;
import com.intland.codebeamer.integration.api.service.testmanagement.testrun.TestRunApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.Attachment;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.Comment;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.CommentType;
import com.intland.codebeamer.integration.api.service.trackeritem.comment.TrackerItemAddedComment;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplate;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.model.AbstractFieldValue;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.BoolFieldValue;
import com.intland.swagger.client.model.ChoiceFieldValue;
import com.intland.swagger.client.model.ColorFieldValue;
import com.intland.swagger.client.model.CountryFieldValue;
import com.intland.swagger.client.model.DateFieldValue;
import com.intland.swagger.client.model.DecimalFieldValue;
import com.intland.swagger.client.model.DurationFieldValue;
import com.intland.swagger.client.model.IntegerFieldValue;
import com.intland.swagger.client.model.LanguageFieldValue;
import com.intland.swagger.client.model.TableFieldValue;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerItemReference;
import com.intland.swagger.client.model.UrlFieldValue;
import com.intland.swagger.client.model.WikiTextFieldValue;

@Test(groups = "TrackerItemApiService")
public class TrackerItemApiServiceTest extends AbstractBaseNGTests {

	public static final String ISSUE_WIKI_LINK = "[ISSUE:%d]";

	private static final Logger logger = LogManager.getLogger(TrackerItemApiServiceTest.class);

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private BaselineApiService baselineApiService;

	private TestRunApiService testRunApiService;

	private ReviewHubApiService reviewHubApiService;

	private Project project;

	private User testUser;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		UserApiService userService = new UserApiService(getApplicationConfiguration());

		testUser = userService.createUser()
				.addToRegularUserGroup()
				.addToDefaultSystemAdministratorGroup()
				.build();

		projectApiService = new ProjectApiService(getApplicationConfiguration(), testUser.getName());
		trackerApiService = new TrackerApiService(getApplicationConfiguration(), testUser.getName());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration(), testUser.getName());
		baselineApiService = new BaselineApiService(getApplicationConfiguration(), testUser.getName());
		this.testRunApiService = new TestRunApiService(getApplicationConfiguration(), testUser.getName());
		reviewHubApiService = new ReviewHubApiService(getApplicationConfiguration(), testUser.getName());

		project = projectApiService.createProjectFromTemplate(getRandomText("MyProject"));
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		projectApiService.deleteProject(project);
	}

	@Test(description = "Tracker item can be created via API")
	public void createTrackerItemViaApi() {
		// Given
		String usTrackerName = "MyUserStoryCreated";
		Tracker myUserStory = trackerApiService.createDefaultUserStoryTracker(project, usTrackerName);

		String taskTrackerName = "MyTasks";
		Tracker myTask = trackerApiService
				.createTaskTracker(project, taskTrackerName)
				.createTextField("My Text Field")
				.createIntegerField("My Integer Field")
				.createDecimalField("My Decimal Field")
				.createDateField("My Date Field")
				.createBooleanField("My Boolean Field")
				.createLanguageField("My Language Field")
				.createCountryField("My Country Field")
				.createWikiTextField("My WikiText Field")
				.createDurationField("My Duration Field")
				.createColorField("My Color Field")
				.createWikiLinkUrlField("My Url Field")
				.createWikiLinkUrlField("My Alias Url Field")
				.createWikiLinkUrlField("My Wiki Link Field")
				.createWikiLinkUrlField("My Other Wiki Link Field")
				.createChoiceField("My Choice Field", List.of(
						new ChoiceOption(1, "option 1"),
						new ChoiceOption(2, "option 2")))
				.createReferenceFieldOfTrackers("My Reference Field",
						filter -> filter.addTrackerFilter(project, usTrackerName))
				.buildAndAdd();

		// When
		TrackerItemId userStory = trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name("Basic Radio 1")
				.description("description"));

		TrackerItemId testCase = trackerItemApiService.createTrackerItem(project, "Test Cases", builder -> builder
				.name("Test Case 1")
				.description("description"));

		trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name("Available Radio Stations 1")
				.description("description"));

		trackerItemApiService.createTrackerItem(project, taskTrackerName, builder -> builder
				.name("name")
				.description("description")
				.priority("Low")
				.setTextFor("My Text Field", "Text")
				.setIntegerFor("My Integer Field", 1)
				.setDecimalFor("My Decimal Field", 2.2)
				.setDateFor("My Date Field", new Date())
				.setBooleanFor("My Boolean Field", Boolean.TRUE)
				.setLanguageFor("My Language Field", Language.DE)
				.setCountryFor("My Country Field", Country.DE)
				.setWikiTextFor("My WikiText Field", "Text")
				.setDurationFor("My Duration Field", Duration.ofHours(1))
				.setColorFor("My Color Field", "#ffffff")
				.setUrlFor("My Url Field", "http://my.com")
				.setUrlFor("My Alias Url Field", "MYCOM", "http://my.com")
				.setWikiLinkFor("My Wiki Link Field", userStory)
				.setWikiLinkFor("My Other Wiki Link Field", testCase)
				.setChoiceOptionFor("My Choice Field", "option 1")
				.setTrackerItemFor("My Reference Field", project, usTrackerName, "Available Radio Stations 1")
				.setTrackerItemFor("Subject", project, usTrackerName, "Basic Radio 1")
				.setUserFor("Assigned to", testUser.getName(), "bond")
				.setRoleFor("Assigned to", "Developer", "Tester")
				);

		// Then
		// Assert user story
		TrackerItem userStoriesItem1Id = trackerItemApiService.findTrackerItemByName(myUserStory, "Basic Radio 1");
		assertNotNull(userStoriesItem1Id, "Tracker item is null");

		com.intland.swagger.client.model.TrackerItem userStoriesItem1 = getTrackerItem(userStoriesItem1Id);
		assertNotNull(userStoriesItem1, "Tracker item is null");
		assertEquals(userStoriesItem1.getName(), "Basic Radio 1");
		assertEquals(userStoriesItem1.getDescription(), "description");

		// Assert user story
		TrackerItem userStoriesItem2Id = trackerItemApiService.findTrackerItemByName(myUserStory, "Available Radio Stations 1");
		assertNotNull(userStoriesItem2Id, "Tracker item is null");

		com.intland.swagger.client.model.TrackerItem userStoriesItem2 = getTrackerItem(userStoriesItem2Id);
		assertNotNull(userStoriesItem2, "Tracker item is null");
		assertEquals(userStoriesItem2.getName(), "Available Radio Stations 1");
		assertEquals(userStoriesItem2.getDescription(), "description");

		//MyTasks tracker item
		TrackerItem tasksTrackerItemId = trackerItemApiService.findTrackerItemByName(myTask, "name");
		assertNotNull(tasksTrackerItemId, "Tracker item is null");

		com.intland.swagger.client.model.TrackerItem tasksTrackerItem = getTrackerItem(tasksTrackerItemId);
		assertNotNull(tasksTrackerItem, "Tracker item is null");
		assertNotNull(tasksTrackerItem.getPriority());
		assertEquals(tasksTrackerItem.getPriority().getName(), "Low");

		List<AbstractFieldValue> tasksTrackerItemCustomFields = tasksTrackerItem.getCustomFields();
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Text Field", TextFieldValue.class).getValue(), "Text");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Integer Field", IntegerFieldValue.class).getValue(), 1);
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Decimal Field", DecimalFieldValue.class).getValue(), 2.2);

		assertNotNull(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Date Field", DateFieldValue.class).getValue());
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Boolean Field", BoolFieldValue.class).getValue(), Boolean.TRUE);
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Language Field", LanguageFieldValue.class).getValues(), Set.of(Language.DE.name()));
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Country Field", CountryFieldValue.class).getValues(), Set.of(Country.DE.name()));
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My WikiText Field", WikiTextFieldValue.class).getValue(), "Text");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Duration Field", DurationFieldValue.class).getValue(), Duration.ofHours(1).toMillis());
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Color Field", ColorFieldValue.class).getValue(), "#ffffff");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Url Field", UrlFieldValue.class).getValue(), "[http://my.com]");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Alias Url Field", UrlFieldValue.class).getValue(), "[MYCOM|http://my.com]");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Wiki Link Field", UrlFieldValue.class).getValue(), ISSUE_WIKI_LINK.formatted(userStory.id()));
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Other Wiki Link Field", UrlFieldValue.class).getValue(), ISSUE_WIKI_LINK.formatted(testCase.id()));
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Choice Field", ChoiceFieldValue.class).getValues().getFirst().getName(), "option 1");
		assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Reference Field", ChoiceFieldValue.class).getValues().getFirst().getName(), "Available Radio Stations 1");

		assertEquals(Optional.ofNullable(tasksTrackerItem.getAssignedTo())
				.orElse(List.of())
				.stream()
				.map(ref -> ref.getType())
				.filter("RoleReference"::equals)
				.toList().size(), 2);

		assertTrue(Optional.ofNullable(tasksTrackerItem.getAssignedTo())
				.orElse(List.of())
				.stream()
				.map(ref -> ref.getName())
				.anyMatch("bond"::equals));
	}

	@Test(description = "Tracker item can be updated via API")
	public void updateTrackerItemViaApi() {
		// Given
		trackerItemApiService.createTrackerItem(project, "User Stories", builder -> builder
				.name("Basic Radio 2")
				.description("description"));

		trackerItemApiService.createTrackerItem(project, "User Stories", builder -> builder
				.name("Available Radio Stations 2")
				.description("description"));

		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, "Tasks", builder -> builder
				.name("task 1")
				.description("description")
				.priority("Low")
				.setDurationFor("Planned Effort", Duration.ofHours(1))
				.setTrackerItemFor("Upstream Reference", project, "User Stories", "Basic Radio 2")
				.setUserFor("Assigned to", testUser.getName(), "bond")
				.setRoleFor("Assigned to", "Developer", "Tester"));

		// When
		trackerItemApiService.updateTrackerItem(trackerItem, builder -> builder
				.name("updated name")
				.description("updated description")
				.priority("High")
				.status("In Progress")
				.setDurationFor("Planned Effort", Duration.ofHours(2))
				.setTrackerItemFor("Upstream Reference", project, "User Stories", "Available Radio Stations 2")
				.setUserFor("Assigned to", "bond")
				.setRoleFor("Assigned to", "Developer")
				);

		// Then
		Tracker tasksTracker = projectApiService.findTrackerByName(project, "Tasks");
		TrackerItem updatedTasksTrackerItem = trackerItemApiService.findTrackerItemByName(tasksTracker, "updated name");
		assertNotNull(updatedTasksTrackerItem, "Tracker item is null");

		com.intland.swagger.client.model.TrackerItem tasksTrackerItem = getTrackerItem(updatedTasksTrackerItem);
		assertNotNull(tasksTrackerItem, "Tracker item is null");
		assertEquals(tasksTrackerItem.getName(), "updated name");
		assertEquals(tasksTrackerItem.getDescription(), "updated description");
	}

	@Test(description = "Comment with attachment can be added to a Tracker item via API")
	public void createTrackerItemCommentWithAttachment() throws IOException {
		// Given
		File tmpFile1 = File.createTempFile("tmpFile", "");
		FileUtils.writeStringToFile(tmpFile1, "File1 content", UTF_8);
		File tmpFile2 = File.createTempFile("tmpFile", "");
		FileUtils.writeStringToFile(tmpFile2, "File2 content", UTF_8);

		String commentStr1 = "Comment1 content";
		String commentStr2 = "Comment2 content";

		Path picture = getFilePath("600x400_000_fff.png");
		try {
			TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "Tasks", builder -> builder
					.name("TrackerItem with comment and attachment")
					.description("Mandatory description"));

			// When
			trackerItemApiService.createTrackerItemComment(trackerItemId,
					builder -> builder
							.plainText()
							.comment(commentStr1)
							.attachment(tmpFile1),
					builder -> builder
							.plainText()
							.comment(commentStr2)
							.attachment(tmpFile2),
					builder -> builder
							.markup()
							.comment(markup -> markup.heading1("My Own Markup")
									.italic("This text should be in italic")
									.newLine()
									.bold("This text should be in Bold")
									.doubleNewLine()
									.trackerItemLink(trackerItemId)
									.doubleNewLine()
									.picture(picture.getFileName().toString()))
							.attachment(tmpFile1)
							.attachment(tmpFile2)
							.attachment(picture));

			// Then
			List<Comment> createdComments = trackerItemApiService.getTrackerItemComments(trackerItemId);
			assertEquals(createdComments.size(), 3);
			assertEquals(commentStr1, createdComments.getFirst().comment());
			assertEquals(commentStr2, createdComments.get(1).comment());
			assertTrue(createdComments.get(2).comment().startsWith("!1 My Own Markup"));

			assertEquals(createdComments.getFirst().attachmentIds().size(), 1);
			assertEquals(createdComments.get(1).attachmentIds().size(), 1);
			assertEquals(createdComments.get(2).attachmentIds().size(), 3);

			Attachment attachment1 = trackerItemApiService.getTrackerItemAttachment(trackerItemId, createdComments.getFirst()
					.attachmentIds().getFirst());
			assertEquals(attachment1.id(), createdComments.getFirst().attachmentIds().getFirst());
			assertEquals(attachment1.name(), tmpFile1.getName());
			assertEquals(attachment1.size(), tmpFile1.length());

			Attachment attachment2 = trackerItemApiService.getTrackerItemAttachment(trackerItemId, createdComments.get(1)
					.attachmentIds().getFirst());
			assertEquals(attachment2.id(), createdComments.get(1).attachmentIds().getFirst());
			assertEquals(attachment2.name(), tmpFile2.getName());
			assertEquals(attachment2.size(), tmpFile2.length());
		} finally {
			FileUtils.deleteQuietly(tmpFile1);
			FileUtils.deleteQuietly(tmpFile2);
		}
	}

	@Test(description = "Tracker item can be deleted via API")
	public void deleteTrackerItemViaApi() {
		// GIVEN
		Tracker myTask = trackerApiService.createDefaultTaskTracker(project, "MyTasksDeleteById");

		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(myTask.id(), builder -> builder
				.name("Basic Task")
				.description("description"));

		TrackerItem basicTask = trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");

		assertNotNull(basicTask, "Tracker item cannot be null");

		// WHEN
		trackerItemApiService.deleteTrackerItem(trackerItem);

		// THEN
		try {
			trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");
			fail("Tracker item should have been deleted");
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertTrue(Pattern.matches("Tracker item is not found by name:.*Basic Task.*", e.getMessage()));
		}
	}

	@Test(description = "Tracker item can be deleted by name via API")
	public void deleteTrackerItemByNameViaApi() {
		// GIVEN
		Tracker myTask = trackerApiService.createDefaultTaskTracker(project, "MyTasksDeleteByName");

		trackerItemApiService.createTrackerItem(myTask.id(), builder -> builder
				.name("Basic Task")
				.description("description"));

		TrackerItem basicTask = trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");

		assertNotNull(basicTask, "Tracker item cannot be null");

		// WHEN
		trackerItemApiService.deleteTrackerItemByName(myTask, "Basic Task");

		// THEN
		try {
			trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");
			fail("Tracker item should have been deleted");
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertTrue(Pattern.matches("Tracker item is not found by name:.*Basic Task.*", e.getMessage()));
		}
	}

	@Test(description = "Tracker item template can be created via API")
	public void createTrackerItemTemplate() {
		// Given
		trackerApiService.createDefaultUserStoryTracker(project, "MyUserStory 5");
		TrackerItemId myTrackerItemId = trackerItemApiService.createTrackerItem(project, "MyUserStory 5", builder -> builder
				.name("Basic Radio 5")
				.description("description"));

		// When
		TrackerItemTemplate myTrackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(
				myTrackerItemId, "MyItemTemplate 1", "description", true);

		// Then
		assertNotNull(myTrackerItemTemplate, "Tracker item template cannot be null");
		assertEquals(myTrackerItemTemplate.title(), "MyItemTemplate 1");
		assertEquals(myTrackerItemTemplate.description(), "description");
		assertTrue(myTrackerItemTemplate.publicTemplate());
	}

	@Test(description = "Tracker item template can be overwritten via API")
	public void overwriteTrackerItemTemplate() {
		// Given
		trackerApiService.createDefaultUserStoryTracker(project, "MyUserStory 3");
		TrackerItemId myTrackerItemId = trackerItemApiService.createTrackerItem(project, "MyUserStory 3", builder -> builder
				.name("Basic Radio 3")
				.description("description"));

		TrackerItemTemplate myTrackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(
				myTrackerItemId, "MyItemTemplate 2", "description", true);

		TrackerItemTemplate overwrittenTrackerItemTemplate = trackerItemApiService.overwriteTrackerItemTemplate(
				myTrackerItemId, myTrackerItemTemplate.id());

		// Then
		assertNotNull(overwrittenTrackerItemTemplate, "Overwritten tracker item template cannot be null");
	}

	@Test(description = "Tracker item with folder type can be created via API")
	public void createTrackerItemWithFolderTypeViaApi() {
		//GIVEN
		//WHEN
		TrackerItemId itemId = trackerItemApiService
				.createTrackerItem(project, "User Stories", builder -> builder
						.folderType()
						.name("folder name")
						.description("folder desc"));

		//THEN
		List<AbstractReference> types = getTrackerItem(itemId).getCategories();
		assertNotNull(types);
		assertEquals(types.size(), 1);
		assertEquals(types.getFirst().getName(), "Folder");
	}

	@Test(description = "Table field can be edited via API")
	public void editTableField() {
		// GIVEN
		final String trackerName = "Test Task";
		final String tableFieldName = "Test Table";
		final String textColumnName = "Text column";
		final String integerColumnName = "Integer column";
		trackerApiService.createTaskTracker(project, trackerName)
				.createTableField(tableFieldName, trackerFieldBuilder -> trackerFieldBuilder,
						tableFieldFactory -> tableFieldFactory
								.createTextField(textColumnName)
								.createIntegerField(integerColumnName))
				.buildAndAdd();

		// WHEN
		TrackerItemId itemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name("name")
				.description("description")
				.table(tableFieldName, tableRowBuilder -> tableRowBuilder
						.addRow(row -> row
								.setTextFor(textColumnName, "Test1")
								.setIntegerFor(integerColumnName, 1)
						)
						.addRow(row -> row
								.setIntegerFor(integerColumnName, 2)
								.setTextFor(textColumnName, "Test2")
						)
				)
				.priority("Low"));

		// THEN
		com.intland.swagger.client.model.TrackerItem task = getTrackerItem(itemId);
		assertNotNull(task, "Tracker item is null");

		List<List<AbstractFieldValue>> tableFieldRows = getCustomFieldByNameAndType(task.getCustomFields(), tableFieldName, TableFieldValue.class).getValues();
		assertNotNull(tableFieldRows);
		assertEquals(tableFieldRows.size(), 2);

		List<AbstractFieldValue> firstRowValues = tableFieldRows.get(0);
		assertEquals(firstRowValues.size(), 2);
		assertEquals(getCustomFieldByNameAndType(firstRowValues, textColumnName, TextFieldValue.class).getValue(), "Test1");
		assertEquals(getCustomFieldByNameAndType(firstRowValues, integerColumnName, IntegerFieldValue.class).getValue(), 1);

		List<AbstractFieldValue> secondRowValues = tableFieldRows.get(1);
		assertEquals(secondRowValues.size(), 2);
		assertEquals(getCustomFieldByNameAndType(secondRowValues, textColumnName, TextFieldValue.class).getValue(), "Test2");
		assertEquals(getCustomFieldByNameAndType(secondRowValues, integerColumnName, IntegerFieldValue.class).getValue(), 2);
	}

	@Test(description = "create test run from test cases")
	public void createTestRunFromTestCases() {
		// GIVEN
		Tracker testRunTracker = trackerApiService.createDefaultTestRunTracker(project.id(), "test run tracker");
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project.id(), "test case tracker");
		String testCase1Name = "tc1";
		String testCase2Name = "tc2";

		TrackerItemId testCase1 = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name(testCase1Name));
		TrackerItemId testCase2 = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name(testCase2Name));

		// WHEN
		TrackerItemId newTestRun = testRunApiService.createTestRunForTestCasesOrTestSets(testRunTracker.id(),
				t -> t.testCaseIds(testCase1, testCase2));

		// THEN
		List<TrackerItemReference> testCases = getTestCasesOfTestRuns(getTrackerItem(newTestRun));
		assertEquals(testCases.size(), 2);
		assertEquals(testCases.get(0).getName(), testCase1Name);
		assertEquals(testCases.get(1).getName(), testCase2Name);
	}

	@Test(description = "create test run from test set")
	public void createTestRunFromTestSet() throws ApiException {
		// GIVEN
		Tracker testRunTracker = trackerApiService.createDefaultTestRunTracker(project.id(), "test run tracker 2");
		Tracker testCaseTracker = trackerApiService.createDefaultTestCaseTracker(project.id(), "test case tracker 2");
		Tracker testSetTracker = trackerApiService.createDefaultTestSetTracker(project.id(), "test set tracker");
		String testCase1Name = "tc1";
		String testCase2Name = "tc2";

		TrackerItemId testCase1 = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name(testCase1Name));
		TrackerItemId testCase2 = trackerItemApiService.createTrackerItem(project, testCaseTracker.name(), t -> t.name(testCase2Name));

		TrackerItemId testSet = trackerItemApiService.createTrackerItem(project, testSetTracker.name(), t -> t.name("ts1"));

		testRunApiService.addTestCasesToSets(Arrays.asList(testCase1.id(), testCase2.id()), Arrays.asList(testSet.id()));

		// WHEN
		TrackerItemId newTestRun = testRunApiService.createTestRunForTestCasesOrTestSets(testRunTracker.id(),
				t -> t.testSetIds(testSet));

		// THEN
		List<TrackerItemReference> testCases = getTestCasesOfTestRuns(getTrackerItem(newTestRun));
		assertEquals(testCases.size(), 2);
		assertEquals(testCases.get(0).getName(), testCase1Name);
		assertEquals(testCases.get(1).getName(), testCase2Name);
	}

	@Test(description = "Multiple tracker items can be created via API")
	public void testTrackerItemBatchCreation() {
		// Given
		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, "MyTask");
		Tracker bugTracker = trackerApiService.createDefaultBugTracker(project, "MyBug");
			// WHEN
		trackerItemApiService.createTrackerItems(
				builder -> builder
					.tracker(taskTracker)
					.name("Basic Task1")
					.description("description"),
				builder -> builder
					.tracker(taskTracker)
					.name("Basic Task2")
					.description("description2"),
				builder -> builder
					.tracker(taskTracker)
					.name("Basic Task3")
					.description("description3"),
				builder -> builder
					.tracker(bugTracker)
					.name("Basic Bug")
					.description("description4"));

		List<TrackerItem> taskTrackerItemByNames = trackerItemApiService.findTrackerItemByNames(taskTracker.id(), "Basic Task1",
				"Basic Task2", "Basic Task3");
		List<TrackerItem> bugTrackerItem = trackerItemApiService.findTrackerItemByNames(bugTracker.id(), "Basic Bug");

		// THEN
		assertEquals(taskTrackerItemByNames.size(), 3);
		assertEquals(bugTrackerItem.size(), 1);
	}

	@Test(description = "Tracker items cannot be created without tracker id", expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Tracker id cannot be null")
	public void testTrackerItemBatchCreationWithoutTrackerId() {
		trackerItemApiService.createTrackerItems(
				builder -> builder
						.name("Basic Task1")
						.description("description"),
				builder -> builder
						.name("Basic Task2")
						.description("description2"),
				builder -> builder
						.name("Basic Task3")
						.description("description3"));
	}

	@Test(description = "Comments on Review Item can be added via API")
	public void addNewCommentOnReviewItemViaApi() {
		// Given
		Tracker userStoryTracker = trackerApiService.createDefaultUserStoryTracker(project, "MyUserStory");
		trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
				.name("Basic Radio")
				.description("Description for tracker item"));

		// When
		String testReviewItem = getRandomText("Test review item");
		Review review = reviewHubApiService.createReview(testReviewItem,
				reviewInformationBuilder -> reviewInformationBuilder
						.addProjectIdsItem(project.id())
						.addTrackerIdsItem(userStoryTracker.id())
						.addUserAsReviewer(testUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.addUserAsModerator(testUser.getName())
						.addUserAsModerator("bond")
						.reviewType(ReviewType.TRACKER_REVIEW)
						.approvedStatusThreshold(-1)
						.approvedThresholdChangeOption(ThresholdChangeOption.SET_TO_APPROVED)
						.minimumSignaturesRequired(0)
						.rejectedStatusThreshold(-1)
						.rejectedThresholdChangeOption(ThresholdChangeOption.SET_TO_REJECTED)
						.description("Description for review item"));

		// Then
		assertNotNull(review.id());
		assertEquals(review.name(), testReviewItem);

		// When
		TrackerItemAddedComment comment = trackerItemApiService.addNewCommentOnReviewItem(review.id().id(),
				addCommentBuilder -> addCommentBuilder
						.setCommentType(CommentType.COMMENT)
						.setComment("New comment on review")
						.setConversationId(UUID.randomUUID().toString()));

		// Then
		assertNotNull(comment);
		assertNotNull(comment.comment());
		assertEquals(comment.comment(), "New comment on review");
		assertNotNull(comment.review());
		assertEquals(comment.review().id().id(), review.id().id());
		assertEquals(comment.review().name(), review.name());
	}

	private <T> T getCustomFieldByNameAndType(List<AbstractFieldValue> customFieldList, String fieldName, Class<T> clazz) {
		AbstractFieldValue abstractFieldValue = Optional.ofNullable(customFieldList)
				.orElse(List.of())
				.stream()
				.filter(f -> fieldName.equals(f.getName()))
				.findFirst().orElse(null);
		assertNotNull(abstractFieldValue, String.format("Custom field %s is null", fieldName));

		return Optional.ofNullable(abstractFieldValue)
				.filter(clazz::isInstance)
				.map(clazz::cast)
				.orElseThrow(() -> new IllegalStateException("%s cannot be converted to %s".formatted(abstractFieldValue.getClass(), clazz)));
	}

	private List<TrackerItemReference> getTestCasesOfTestRuns(com.intland.swagger.client.model.TrackerItem trackerItem) {
		TableFieldValue tableFieldValue = getCustomFieldByNameAndType(trackerItem.getCustomFields(), "Test Cases",
				TableFieldValue.class);
		List<List<AbstractFieldValue>> values = tableFieldValue.getValues();
		return values.stream()
				.map(value -> getCustomFieldByNameAndType(value, "Test Case", ChoiceFieldValue.class))
				.map(ChoiceFieldValue::getValues)
				.filter(Objects::nonNull)
				.flatMap(List::stream)
				.map(t -> (TrackerItemReference) t)
				.collect(Collectors.toList());
	}
}
