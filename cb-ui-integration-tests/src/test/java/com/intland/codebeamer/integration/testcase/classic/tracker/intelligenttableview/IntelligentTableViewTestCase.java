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

package com.intland.codebeamer.integration.testcase.classic.tracker.intelligenttableview;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.builder.choice.MemberType;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldBuilder;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.workconfigitemreferencemodal.GroupingLevelLabel;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.DateUtil;

@Test(groups = "IntelligentTableViewTestCase")
public class IntelligentTableViewTestCase extends AbstractIntegrationClassicNGTests {

	private static final String CUSTOM_BOOLEAN = "CustomBoolean";

	private static final String CUSTOM_COLOR = "CustomColor";

	private static final String CUSTOM_COUNTRY = "CustomCountry";

	private static final String CUSTOM_DECIMAL = "CustomDecimal";

	private static final String CUSTOM_DATE = "CustomDate";

	private static final String CUSTOM_DURATION = "CustomDuration";

	private static final String CUSTOM_INTEGER = "CustomInteger";

	public static final String CUSTOM_CHOICE = "CustomChoice";

	private static final String CUSTOM_LANGUAGE = "CustomLanguage";

	private static final String CUSTOM_MEMBER = "CustomMember";

	private static final String CUSTOM_MEMBER2= "CustomMemberSecond";

	private static final String CUSTOM_TEXT = "CustomText";

	private static final String ASSIGNED_TO = "Assigned to";

	private static final String DESCRIPTION = "description";

	private static final String SPENT_EFFORT = "Spent Effort";

	private static final String SPENT_PLAN = "% Spent / Plan";

	private static final String STATUS = "Status";

	private static final String STORY_POINTS = "Story Points";

	private static final String SUBMITTED_BY = "Submitted by";

	private static final String TASK_TRACKER_NAME = "CustomTask";

	private static final String TEAM_TRACKER_NAME = "Team";

	private static final String TRACKER_TYPE = "Tracker Type";

	private static final String REQUIREMENT_TRACKER_NAME = "ReqForTesting";

	private static final String REQUIREMENTS_FIELD = "Requirements field";

	private static final String CUSTOM_REF_FIELD = "CustomRefField";

	private static final String BACKLOG_ITEMS = "Backlog Items";

	private static final String BACKLOG_ITEM = "Backlog Item";

	private static final String USER_STORY_TRACKER_NAME = "User Stories";

	private static final String REF_SHARED_FIELD = "ReferenceSharedField";

	private static final String ITEM_1 = "item1";

	private static final String ITEM_2 = "item2";

	private static final String ITEM_3 = "item3";

	private static final String COLOR_VALUE = "#5eceeb";

	private static final String NO_GROUP_VALUE = "No Group";

	private static final String PROJECT_ADMIN_VALUE = "Project Admin";

	private static final String QA_VALUE = "QA";

	private static final String TEAM_VALUE = "Team1";

	private static final String TEXT1_VALUE = "text1";

	private static final String TEXT2_VALUE = "text2";

	private static final String TEXT3_VALUE = "text3";

	private static final int ALL_ITEMS = 3;

	private static final int[] COUNT_OF_ITEMS_SECOND_GROUP = { 2, 1, 1, 1, 1 };

	private static final int[] COUNT_OF_ITEMS_FOUR_GROUP = { 3, 3 };

	private Project project;

	private Tracker taskTracker;

	private TrackerItem userStoryBasicRadio;

	private TrackerItem userStoryNightMode;

	private TrackerItemId taskTrackerItemId;

	private User projectAdmin;
	
	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private SharedFieldApiService sharedFieldApiService;

	private UserApiService userApiService;

	private List<TrackerItem> trackerItems;

	@BeforeMethod
	protected void generateDataBeforeMethod() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();

		projectAdmin = userApiService.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(projectAdmin);

		trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);

		trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);

		sharedFieldApiService = getDataManagerService().getSharedFieldApiService();

		project = projectApiService.createProjectFromTemplate("ReferenceModalTest" + getRandomString(),
				ProjectTemplate.AGILE_SCRUM);

		sharedFieldApiService.createSharedField(REF_SHARED_FIELD, SharedFieldBuilder::referenceFieldWithTrackerItemType);

		trackerApiService.createRequirementTracker(project, REQUIREMENT_TRACKER_NAME)
				.createBooleanField(CUSTOM_BOOLEAN)
				.createMemberField(CUSTOM_MEMBER, List.of(MemberType.USERS, MemberType.ROLES))
				.createMemberField(CUSTOM_MEMBER2, List.of(MemberType.USERS, MemberType.ROLES))
				.createDurationField(CUSTOM_DURATION)
				.createColorField(CUSTOM_COLOR)
				.createChoiceField(CUSTOM_CHOICE, List.of(
						new ChoiceOption(1, "1"),
						new ChoiceOption(2, "2")))
				.createIntegerField(CUSTOM_INTEGER)
				.createCountryField(CUSTOM_COUNTRY)
				.createLanguageField(CUSTOM_LANGUAGE)
				.createDateField(CUSTOM_DATE)
				.createTextField(CUSTOM_TEXT)
				.createDecimalField(CUSTOM_DECIMAL)
				.createReferenceFieldOfTrackers(CUSTOM_REF_FIELD, referenceFilter -> referenceFilter
								.addTrackerFilter(project, USER_STORY_TRACKER_NAME),
						fieldProp -> fieldProp.multipleSelection(true).sharedFields(REF_SHARED_FIELD)
				)
				.buildAndAdd();

		trackerApiService
				.updateTracker(project, REQUIREMENT_TRACKER_NAME)
				.updateField("Planned Effort", builder -> builder.description("").withPermissionUnrestricted())
				.updateField(SPENT_EFFORT, builder -> builder.description("").withPermissionUnrestricted())
				.updateField(SPENT_PLAN, builder -> builder.description("").withPermissionUnrestricted())
				.buildAndAdd();

		taskTracker = trackerApiService.createTaskTracker(project, TASK_TRACKER_NAME)
				.createReferenceFieldOfTrackers(REQUIREMENTS_FIELD, referenceFilter -> referenceFilter
								.addTrackerFilter(project, REQUIREMENT_TRACKER_NAME),
						fieldProp -> fieldProp.multipleSelection(true).sharedFields(REF_SHARED_FIELD)
				)
				.createReferenceFieldOfTrackers(BACKLOG_ITEM, referenceFilter -> referenceFilter
						.addTrackerFilter(project, REQUIREMENT_TRACKER_NAME)
						.addTrackerFilter(project, BACKLOG_ITEMS)
						.addTrackerFilter(project, USER_STORY_TRACKER_NAME)
				)
				.buildAndAdd();

		Tracker userStoryTracker = projectApiService.findTrackerByName(project, USER_STORY_TRACKER_NAME);
		userStoryBasicRadio = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Basic Radio");
		userStoryNightMode = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Night Mode");

		trackerApiService.createTeamTracker(project, TEAM_TRACKER_NAME)
				.buildAndAdd();

		taskTrackerItemId = trackerItemApiService.createTrackerItem(project, TASK_TRACKER_NAME, builder -> builder
				.description(DESCRIPTION)
				.name(ITEM_1));

		trackerItemApiService.createTrackerItem(project, TEAM_TRACKER_NAME, builder -> builder
				.description(DESCRIPTION)
				.name(TEAM_VALUE));

		trackerItems = createRequiredTrackerItems();

		switchToClassicUI(projectAdmin);
	}

	@AfterMethod
	protected void cleanUpDataAfterMethod() throws Exception {
		projectApiService.deleteProject(project);
		sharedFieldApiService.deleteSharedFieldByName(REF_SHARED_FIELD);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		userApiService.disableUser(projectAdmin);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by default and custom fields", dataProvider = "groupByParamsFirstGroup", enabled = false)
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithDefaultAndCustomFields1(
			String customFieldName,
			String defaultFieldName,
			String mainGroupName,
			String[] labelOfCustomFieldGroups,
			int[] trackerItemsOrder,
			int[] countOfItemsByGroups
	) {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain = new GroupingLevelLabel("1", defaultFieldName, mainGroupName);

		List<GroupingLevelLabel> subGroupingLevelLabels = createSubGroupingLevelLabels(labelOfCustomFieldGroups, customFieldName);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
								.reportSelector(header -> header
										.chooseGroupByField(defaultFieldName)
										.chooseGroupByField(customFieldName)
										.filter())
								.trackerItemsTable(table -> table.assertThat()
										.isGroupItemCountEquals(groupingLevelLabelMain, countOfItemsByGroups[0])
										.isGroupItemCountEquals(subGroupingLevelLabels.get(0), countOfItemsByGroups[1])
										.isGroupItemCountEquals(subGroupingLevelLabels.get(1), countOfItemsByGroups[2])
										.isGroupItemCountEquals(subGroupingLevelLabels.get(2), countOfItemsByGroups[3])
										.groupIs(trackerItems.get(trackerItemsOrder[0]), groupingLevelLabelMain, subGroupingLevelLabels.get(0))
										.groupIs(trackerItems.get(trackerItemsOrder[1]), groupingLevelLabelMain, subGroupingLevelLabels.get(1))
										.groupIs(trackerItems.get(trackerItemsOrder[2]), groupingLevelLabelMain, subGroupingLevelLabels.get(2))
										.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Assigned to and custom member fields")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithAssignedToAndCustomMemberFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", ASSIGNED_TO, projectAdmin.getName());

		GroupingLevelLabel groupingLevelLabelMain2 = new GroupingLevelLabel("1", ASSIGNED_TO, NO_GROUP_VALUE);

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_MEMBER, projectAdmin.getName());

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_MEMBER, PROJECT_ADMIN_VALUE);

		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", CUSTOM_MEMBER, QA_VALUE);

		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(ASSIGNED_TO)
								.chooseGroupByField(CUSTOM_MEMBER)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, COUNT_OF_ITEMS_SECOND_GROUP[0])
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, COUNT_OF_ITEMS_SECOND_GROUP[1])
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, COUNT_OF_ITEMS_SECOND_GROUP[2])
								.isGroupItemCountEquals(groupingLevelLabelMain2, COUNT_OF_ITEMS_SECOND_GROUP[3])
								.isGroupItemCountEquals(groupingLevelLabelSubMenu3, COUNT_OF_ITEMS_SECOND_GROUP[3])
								.groupIs(trackerItems.get(1), groupingLevelLabelMain1, groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(0), groupingLevelLabelMain1, groupingLevelLabelSubMenu2)
								.groupIs(trackerItems.get(2), groupingLevelLabelMain2, groupingLevelLabelSubMenu3)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Summary and custom duration fields")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithSummaryAndCustomDurationFields() {
		final String SUMMARY = "Summary";
		final String DURATION_VALUE = "1:00h";
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", SUMMARY, trackerItems.get(0).name());

		GroupingLevelLabel groupingLevelLabelMain2 = new GroupingLevelLabel("1", SUMMARY, trackerItems.get(1).name());

		GroupingLevelLabel groupingLevelLabelMain3 = new GroupingLevelLabel("1", SUMMARY, trackerItems.get(2).name());

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_DURATION, DURATION_VALUE);

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_DURATION, DURATION_VALUE);

		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", CUSTOM_DURATION, DURATION_VALUE);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(SUMMARY)
								.chooseGroupByField(CUSTOM_DURATION)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.groupIs(trackerItems.get(0), groupingLevelLabelMain1, groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(1), groupingLevelLabelMain2, groupingLevelLabelSubMenu2)
								.groupIs(trackerItems.get(2), groupingLevelLabelMain3, groupingLevelLabelSubMenu3)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Tracker and custom field")
	public void abillityToUseGroupByOptionOfTheReferenceSelectorOverlayWithSharedCustomReferenceChoiceAndCustomIntegerFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", CUSTOM_REF_FIELD, "Basic Radio");

		GroupingLevelLabel groupingLevelLabelMain2 = new GroupingLevelLabel("1", CUSTOM_REF_FIELD, "Night Mode");

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_INTEGER, "1");

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_INTEGER, "2");

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField("%s (%s)".formatted(CUSTOM_REF_FIELD, REF_SHARED_FIELD))
								.chooseGroupByField(CUSTOM_INTEGER)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 2)
								.isGroupItemCountEquals(groupingLevelLabelMain2, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 2)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.groupIs(List.of(trackerItems.get(1), trackerItems.get(0)), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(2), groupingLevelLabelMain2, groupingLevelLabelSubMenu2)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Tracker and custom field")
	public void abillityToUseGroupByOptionOfTheReferenceSelectorOverlayWithSpentPlanAndSharedReferenceChoiceFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", SPENT_PLAN, "0%");

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", REF_SHARED_FIELD,
				"[US-%s] - %s".formatted(userStoryBasicRadio.id().id(), userStoryBasicRadio.name()));

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", REF_SHARED_FIELD,
				"[US-%s] - %s".formatted(userStoryNightMode.id().id(), userStoryNightMode.name()));

		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", REF_SHARED_FIELD, NO_GROUP_VALUE);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(BACKLOG_ITEM)
				).trackerTableContentComponent(content -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(BACKLOG_ITEM)
						.reportSelector(header -> header
								.chooseGroupByField(SPENT_PLAN)
								.chooseGroupByField(REF_SHARED_FIELD)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 40)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 2)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu3, 37)
								.isAllItemCountEquals(40)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by default and custom fields", dataProvider = "groupByParamsFourthGroup", enabled = false)
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithDefaultAndCustomFields2(
			String customField,
			String defaultField,
			String mainLevelValue,
			String subMenuValue
	) {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", defaultField, mainLevelValue);

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", customField, subMenuValue);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(defaultField)
								.chooseGroupByField(customField)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, COUNT_OF_ITEMS_FOUR_GROUP[0])
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, COUNT_OF_ITEMS_FOUR_GROUP[1])
								.groupIs(List.of(trackerItems.get(2), trackerItems.get(1), trackerItems.get(0)),
										groupingLevelLabelMain1, groupingLevelLabelSubMenu1)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Project and custom language fields")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithTrackerTypeAndCustomChoiceFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", TRACKER_TYPE, "Requirement");

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_CHOICE, "1");

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_CHOICE, "2");

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(TRACKER_TYPE)
								.chooseGroupByField(CUSTOM_CHOICE)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 3)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 2)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.groupIs(List.of(trackerItems.get(1), trackerItems.get(0)), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(2), groupingLevelLabelMain1, groupingLevelLabelSubMenu2)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Project and custom language fields")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithProjectAndCustomLanguageFields() {
		final String PROJECT = "Project";
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", PROJECT, project.name());

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_LANGUAGE, "English");

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_LANGUAGE, "Hindi");

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(PROJECT)
								.chooseGroupByField(CUSTOM_LANGUAGE)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 3)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 2)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.groupIs(List.of(trackerItems.get(2), trackerItems.get(0)), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(1), groupingLevelLabelMain1, groupingLevelLabelSubMenu2)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Tracker and custom field")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithStoryPointsAndCustomCountryFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", STORY_POINTS, String.valueOf(1));

		GroupingLevelLabel groupingLevelLabelMain2 = new GroupingLevelLabel("1", STORY_POINTS, String.valueOf(2));

		GroupingLevelLabel groupingLevelLabelMain3 = new GroupingLevelLabel("1", STORY_POINTS, String.valueOf(3));

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_COUNTRY, Country.NO.getName());

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_COUNTRY, Country.HU.getName());

		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", CUSTOM_COUNTRY, NO_GROUP_VALUE);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(STORY_POINTS)
								.chooseGroupByField(CUSTOM_COUNTRY)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 1)
								.isGroupItemCountEquals(groupingLevelLabelMain2, 1)
								.isGroupItemCountEquals(groupingLevelLabelMain3, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu3, 1)
								.groupIs(List.of(trackerItems.getFirst()), groupingLevelLabelMain1, groupingLevelLabelSubMenu1)
								.groupIs(List.of(trackerItems.get(1)), groupingLevelLabelMain2, groupingLevelLabelSubMenu2)
								.groupIs(List.of(trackerItems.get(2)), groupingLevelLabelMain3, groupingLevelLabelSubMenu3)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Team and custom date fields")
	public void abilityToUseGroupByOptionOfTheReferenceSelectorOverlayWithTeamAndCustomDateFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", TEAM_TRACKER_NAME, TEAM_VALUE);

		GroupingLevelLabel groupingLevelLabelMain2 = new GroupingLevelLabel("1", TEAM_TRACKER_NAME, NO_GROUP_VALUE);

		String currentEnglishDate = DateUtil.getEnglishFormattedDate();

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_DATE, currentEnglishDate);

		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_DATE, currentEnglishDate);

		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", CUSTOM_DATE, NO_GROUP_VALUE);

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(TEAM_TRACKER_NAME)
								.chooseGroupByField(CUSTOM_DATE)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.groupIs(trackerItems.get(0), groupingLevelLabelMain1, groupingLevelLabelSubMenu1)
								.groupIs(trackerItems.get(1), groupingLevelLabelMain2, groupingLevelLabelSubMenu2)
								.groupIs(trackerItems.get(2), groupingLevelLabelMain2, groupingLevelLabelSubMenu3)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/5290476")
	@Test(description = "User is able to group by on a reference modal by Tracker and custom field")
	public void abillityToUseGroupByOptionOfTheReferenceSelectorOverlayWithSubmittedByAndCustomDecimalFields() {
		// GIVEN
		GroupingLevelLabel groupingLevelLabelMain1 = new GroupingLevelLabel("1", SUBMITTED_BY, projectAdmin.getName());

		GroupingLevelLabel groupingLevelLabelSubMenu1 = new GroupingLevelLabel("2", CUSTOM_DECIMAL, String.valueOf(-1));
		GroupingLevelLabel groupingLevelLabelSubMenu2 = new GroupingLevelLabel("2", CUSTOM_DECIMAL, String.valueOf(1));
		GroupingLevelLabel groupingLevelLabelSubMenu3 = new GroupingLevelLabel("2", CUSTOM_DECIMAL, String.valueOf(0));

		// WHEN THEN
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerTableViewPage(taskTracker)
				.trackerTableContentComponent(content  -> content
						.getTableHeader().openColumnPopup(STATUS)
						.addColumnToTable(REQUIREMENTS_FIELD)
				).trackerTableContentComponent(content  -> content
						.getTableRow(taskTrackerItemId)
						.getFieldsComponent()
						.editReferenceFieldByModal(REQUIREMENTS_FIELD)
						.reportSelector(header -> header
								.chooseGroupByField(SUBMITTED_BY)
								.chooseGroupByField(CUSTOM_DECIMAL)
								.filter())
						.trackerItemsTable(table -> table.assertThat()
								.isGroupItemCountEquals(groupingLevelLabelMain1, 3)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu1, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu2, 1)
								.isGroupItemCountEquals(groupingLevelLabelSubMenu3, 1)
								.groupIs(List.of( trackerItems.getFirst()), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu1)
								.groupIs(List.of( trackerItems.get(1)), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu2)
								.groupIs(List.of( trackerItems.get(2)), groupingLevelLabelMain1,
										groupingLevelLabelSubMenu3)
								.isAllItemCountEquals(ALL_ITEMS)
						));
	}

	private List<GroupingLevelLabel> createSubGroupingLevelLabels(String[] labelOfCustomFieldGroups, String customFieldName) {
		return Arrays.stream(labelOfCustomFieldGroups)
				.map(label -> new GroupingLevelLabel("2", customFieldName, label))
				.toList();
	}

	private List<TrackerItem> createRequiredTrackerItems() {

		List<TrackerItem> result = new LinkedList<>();

		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, REQUIREMENT_TRACKER_NAME, builder -> builder
				.description(DESCRIPTION)
				.name(ITEM_1)
				.setBooleanFor(CUSTOM_BOOLEAN, true)
				.setRoleFor(CUSTOM_MEMBER, PROJECT_ADMIN_VALUE)
				.setDurationFor(CUSTOM_DURATION, Duration.ofHours(1))
				.setColorFor(CUSTOM_COLOR, COLOR_VALUE)
				.setCountryFor(CUSTOM_COUNTRY, Country.NO)
				.setLanguageFor(CUSTOM_LANGUAGE, Language.EN)
				.setTextFor(CUSTOM_TEXT, TEXT1_VALUE)
				.setDecimalFor(CUSTOM_DECIMAL, -1)
				.setChoiceOptionFor(CUSTOM_CHOICE, "1")
				.setIntegerFor(CUSTOM_INTEGER, 1)
				.setIntegerFor(STORY_POINTS, 1)
				.setUserFor(ASSIGNED_TO, projectAdmin.getName())
				.setTrackerItemFor(TEAM_TRACKER_NAME, project, TEAM_TRACKER_NAME, TEAM_VALUE)
				.setTrackerItemFor(CUSTOM_REF_FIELD, project, USER_STORY_TRACKER_NAME, "Basic Radio")
				.setDateFor(CUSTOM_DATE, new Date())
		);

		result.add(new TrackerItem(trackerItemId, ITEM_1));

		trackerItemId = trackerItemApiService.createTrackerItem(project, REQUIREMENT_TRACKER_NAME, builder -> builder
				.description(DESCRIPTION)
				.name(ITEM_2)
				.setBooleanFor(CUSTOM_BOOLEAN, false)
				.setUserFor(CUSTOM_MEMBER, projectAdmin.getName())
				.setDurationFor(CUSTOM_DURATION, Duration.ofHours(1))
				.setColorFor(CUSTOM_COLOR, COLOR_VALUE)
				.setIntegerFor(STORY_POINTS, 2)
				.setCountryFor(CUSTOM_COUNTRY, Country.HU)
				.setLanguageFor(CUSTOM_LANGUAGE, Language.HI)
				.setTextFor(CUSTOM_TEXT, TEXT2_VALUE)
				.setDecimalFor(CUSTOM_DECIMAL, 1)
				.setChoiceOptionFor(CUSTOM_CHOICE, "1")
				.setDateFor(CUSTOM_DATE, new Date())
				.setIntegerFor(CUSTOM_INTEGER, 1)
				.setTrackerItemFor(CUSTOM_REF_FIELD, project, USER_STORY_TRACKER_NAME, "Basic Radio")
				.setUserFor(ASSIGNED_TO, projectAdmin.getName()));

		result.add(new TrackerItem(trackerItemId, ITEM_2));

		trackerItemId = trackerItemApiService.createTrackerItem(project, REQUIREMENT_TRACKER_NAME, builder -> builder
				.description(DESCRIPTION)
				.name(ITEM_3)
				.setRoleFor(CUSTOM_MEMBER, QA_VALUE)
				.setDurationFor(CUSTOM_DURATION, Duration.ofHours(1))
				.setColorFor(CUSTOM_COLOR, COLOR_VALUE)
				.setIntegerFor(STORY_POINTS, 3)
				.setLanguageFor(CUSTOM_LANGUAGE, Language.EN)
				.setTextFor(CUSTOM_TEXT, TEXT3_VALUE)
				.setDecimalFor(CUSTOM_DECIMAL, 0)
				.setChoiceOptionFor(CUSTOM_CHOICE, "2")
				.setTrackerItemFor(CUSTOM_REF_FIELD, project, USER_STORY_TRACKER_NAME, "Night Mode")
				.setIntegerFor(CUSTOM_INTEGER, 2));

		result.add(new TrackerItem(trackerItemId, ITEM_3));
		return result;
	}

	@DataProvider(name = "groupByParamsFirstGroup")
	private Object[][] getDynamicAttributesDataFirstGroup() {
		return new Object[][] {
				{
						CUSTOM_BOOLEAN,
						"Tracker",
						REQUIREMENT_TRACKER_NAME,
						new String[] { "false", "true", NO_GROUP_VALUE },
						new int[] { 1, 0, 2 },
						new int[] { 3, 1, 1, 1 }
				},
				{
						CUSTOM_TEXT,
						SPENT_PLAN,
						"0%",
						new String[] { TEXT1_VALUE, TEXT2_VALUE, TEXT3_VALUE },
						new int[] { 0, 1, 2 },
						new int[] { 3, 1, 1, 1 }
				}
		};
	}

	@DataProvider(name = "groupByParamsFourthGroup")
	private Object[][] getDynamicAttributesDataFourthGroup() {
		return new Object[][] {
				{
						CUSTOM_COLOR,
						STATUS,
						"New",
						COLOR_VALUE
				},
				{
						CUSTOM_MEMBER2,
						SPENT_EFFORT,
						"0s",
						NO_GROUP_VALUE
				}
		};
	}

}