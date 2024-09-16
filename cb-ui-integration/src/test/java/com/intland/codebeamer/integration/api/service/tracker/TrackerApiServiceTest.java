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

package com.intland.codebeamer.integration.api.service.tracker;

import static com.intland.codebeamer.integration.api.builder.trackerview.SimpleTrackerViewCondition.HAS_REVIEW;
import static com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewCreationType.GUARD;
import static com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewType.ISSUE_CHANGES_FILTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.TrackerFieldBuilder;
import com.intland.codebeamer.integration.api.builder.TrackerFieldType;
import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.builder.choice.MemberType;
import com.intland.codebeamer.integration.api.builder.permission.FieldPermissionBuilder;
import com.intland.codebeamer.integration.api.builder.reference.StatusMeaning;
import com.intland.codebeamer.integration.api.builder.trackerview.TrackerView;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.project.ProjectPermission;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedField;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldBuilder;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermission;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.ActionParameter;
import com.intland.swagger.client.model.ChoiceFieldMultiValue;
import com.intland.swagger.client.model.ChoiceMembers;
import com.intland.swagger.client.model.ChoiceOptions;
import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;
import com.intland.swagger.client.model.ChoiceWorkConfigItems;
import com.intland.swagger.client.model.FiltersView;
import com.intland.swagger.client.model.ReferenceFilterBasedChoiceReferenceFilter;
import com.intland.swagger.client.model.RoleWithPermissions;
import com.intland.swagger.client.model.StateTransitionsResponse;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerBasicInformation;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.TrackerFieldDependency;
import com.intland.swagger.client.model.TrackerPermissionReference;
import com.intland.swagger.client.model.TrackerStateTransition;
import com.intland.swagger.client.model.TransitionActionInfo;
import com.intland.swagger.client.model.WikiTextFieldMultiValue;

@Test(groups = "TrackerApiServiceTest")
public class TrackerApiServiceTest extends AbstractBaseNGTests {

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private SharedFieldApiService sharedFieldApiService;

	private UserApiService userApiService;

	private Project project;

	private User apiUser;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		projectApiService = new ProjectApiService(getApplicationConfiguration());
		trackerApiService = new TrackerApiService(getApplicationConfiguration());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration());
		sharedFieldApiService = new SharedFieldApiService(getApplicationConfiguration());
		userApiService = new UserApiService(getApplicationConfiguration());
		apiUser = userApiService.findUserByName(getApplicationConfiguration().getApiUser().username());
		project = projectApiService.createProjectFromTemplate();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		projectApiService.deleteProject(project.id());
	}

	@Test(description = "Default trackers can be created via API", dataProvider = "defaultTrackerCreation")
	public void createDefaultTrackersViaApi(String methodName, String trackerName, Boolean isActiveWorkflow) throws Exception {
		// GIVEN data provider

		// WHEN
		Method createMethod = TrackerApiService.class.getDeclaredMethod(methodName, ProjectId.class, String.class);
		Tracker tracker = (Tracker) createMethod.invoke(trackerApiService, project.id(), trackerName);

		// THEN
		TrackerBasicInformation basicInformation = getTracker(tracker.id()).getBasicInformation();

		assertEquals(basicInformation.getName(), trackerName);
		assertTrue(basicInformation.getSharedInWorkingSets());
		assertEquals(basicInformation.getWorkflowIsActive(), isActiveWorkflow);
	}

	@Test(description = "Fields can be added to existing tracker")
	public void addExtraFieldsToExistingTracker() {
		// GIVEN
		String fieldName = "Added extra bool field";

		// WHEN
		Tracker tracker = trackerApiService.updateTracker(project, "Tasks")
				.createBooleanField(fieldName)
				.buildAndAdd();

		// THEN
		Optional<TrackerField> field = getTracker(tracker.id())
				.getFields().stream()
				.filter(f -> fieldName.equals(f.getLabel()))
				.findFirst();

		assertTrue(field.isPresent());
		assertEquals(field.get().getTypeId(), TrackerFieldType.BOOLEAN.getTypeId());
	}

	@Test(description = "Tracker can be created via API")
	public void createTrackersAndCustomFieldsViaApi() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();

		try {
			trackerApiService.createDefaultBugTracker(project, "MyBugCustom");
			trackerApiService.createDefaultTaskTracker(project, "MyTaskCustom");

			Function<FieldPermissionBuilder, FieldPermissionBuilder> permBuilder = b -> b
					.addReadAccessForRole("Developer")
					.addWriteAccessForRole("Project Admin")
					.addReadAccessForField("Owner");

			Function<FieldPermissionBuilder, FieldPermissionBuilder> statusPermBuilder = b -> b
					.addWriteAccessForRoleAndStatus("Developer", "New")
					.addWriteAccessForRoleAndStatus("Project Admin", "New")
					.addReadAccessForRoleAndStatus("Scrum Master", "New")
					.addReadAccessForRoleAndStatus("Project Admin", "InQA")
					.addReadAccessForFieldAndStatus("Owner", "To Verify");

			// WHEN
			Tracker tracker = trackerApiService.createTaskTracker(project, "TaskCustom")
					.createTableField("My Table Field", trackerFieldBuilder -> trackerFieldBuilder,
							tableFieldFactory -> tableFieldFactory
									.createBooleanField("Bool")
									.createDateField("My date", builder -> builder.displayYear().displayMonth()))
					.createMemberField("My Best Members", List.of(MemberType.USERS, MemberType.ROLES))
					.createChoiceField("My choice", List.of(
							new ChoiceOption(1, "op 1"),
							new ChoiceOption(2, "op 2")), trackerFieldBuilder -> trackerFieldBuilder
							.withPermissionSingle(permBuilder))
					.createTextField("Best text ever", builder -> builder
							.listable()
							.withPermissionPerStatus(statusPermBuilder))
					.createReferenceFieldOfTrackers("My Reference1", referenceFilter -> referenceFilter
							.addTrackerFilterWithStatus(project, "MyBugCustom", StatusMeaning.INPROGRESS)
							.addTrackerFilterWithStatus(project, "MyTaskCustom", StatusMeaning.CLOSED)
							.addTrackerFilterWithStatus(project2, "Tasks", StatusMeaning.SUCCESSFUL)
					)
					.createReferenceFieldOfType("My Reference2", referenceFilter -> referenceFilter
							.addTrackerTypeFilterWithStatus(project,
									List.of(TrackerType.RELEASE, TrackerType.BUG, TrackerType.EPIC),
									StatusMeaning.INPROGRESS)
							.addTrackerTypeFilter(project2, List.of(TrackerType.TASK, TrackerType.BUG))
					)
					.buildAndAdd();

			// THEN
			TrackerConfiguration result = getTracker(tracker.id());
			List<TrackerField> fields = result.getFields();

			assertEquals(result.getBasicInformation().getName(), "TaskCustom");

			TrackerField myChoice = getField("My choice", fields);
			ChoiceOptions choiceOptions = (ChoiceOptions) myChoice.getChoiceOptionSetting();
			assertOptions(choiceOptions, "op 1", 1);
			assertOptions(choiceOptions, "op 2", 2);

			TrackerField myBestMembers = getField("My Best Members", fields);
			ChoiceMembers choiceMembers = (ChoiceMembers) myBestMembers.getChoiceOptionSetting();
			assertEquals(choiceMembers.getMembersTypeDataSourceTypes(), Set.of(ChoiceMembers.MembersTypeDataSourceTypesEnum.USERS,
					ChoiceMembers.MembersTypeDataSourceTypesEnum.ROLES));

			TrackerField myReference1 = getField("My Reference2", fields);
			ChoiceWorkConfigItems choiceWorkConfigItems = (ChoiceWorkConfigItems) myReference1.getChoiceOptionSetting();
			List<ReferenceFilterBasedChoiceReferenceFilter> referenceFilters = choiceWorkConfigItems.getReferenceFilters();

			ReferenceFilterBasedChoiceReferenceFilter referenceFilter1 = getReferenceFilter(referenceFilters, project.id());
			assertEquals(referenceFilter1.getDomainType(), ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.PROJECT);
			assertEquals(referenceFilter1.getDomainId(), project.id().id());
			assertEquals(referenceFilter1.getId(), tracker.id().id());
			assertEquals(referenceFilter1.getFilterStatusId(), StatusMeaning.INPROGRESS.getStatusCode());
			assertEquals(referenceFilter1.getTargetIds(),
					List.of(TrackerType.RELEASE.getTechnicalId(), TrackerType.BUG.getTechnicalId(),
							TrackerType.EPIC.getTechnicalId()));

			ReferenceFilterBasedChoiceReferenceFilter referenceFilter2 = getReferenceFilter(referenceFilters, project2.id());
			assertEquals(referenceFilter2.getDomainType(), ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.PROJECT);
			assertEquals(referenceFilter2.getDomainId(), project2.id().id());
			assertEquals(referenceFilter2.getId(), tracker.id().id());
			assertEquals(referenceFilter2.getFilterStatusId(), 0);
			assertEquals(referenceFilter2.getTargetIds(),
					List.of(TrackerType.TASK.getTechnicalId(), TrackerType.BUG.getTechnicalId()));

		} finally {
			projectApiService.deleteProject(project2.id());
		}
	}

	@Test(description = "Default values cannot be set via API", expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Cannot mix default value types!")
	public void testSettingDefaultValueThrowsException() {
		// GIVEN
		// WHEN
		trackerApiService.createTaskTracker(project, "TaskFailed")
				.createTextField("Best text ever", builder -> builder
						.listable()
						.defaultValuesInStatuses(defaultValueBuilder -> defaultValueBuilder
								.addDefaultValueInStatus("In Progress", "Value")
								.addDefaultRoleValueInStatus("New", "Tester"))
				)
				.buildAndAdd();

		// THEN exception
	}

	@Test(description = "Default values can be set via API")
	public void testSettingDefaultValues() {
		// GIVEN
		Tracker myBug = trackerApiService.createDefaultBugTracker(project, "MyBugDefault");

		TrackerItemId bug1 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug1")
				.description("description"));

		TrackerItemId bug2 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug2")
				.description("description"));

		TrackerItemId bug3 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug3")
				.description("description"));

		// WHEN
		Tracker tracker = trackerApiService.createTaskTracker(project, "TaskDefault")
				.createMemberField("My Best Members", List.of(MemberType.USERS, MemberType.ROLES),
						trackerFieldBuilder -> trackerFieldBuilder.defaultValuesInStatuses(defaultValueBuilder ->
								defaultValueBuilder.addDefaultRoleValueInStatus("New", "Developer")
										.addDefaultRoleValueInStatus("New", "Tester")
										.addDefaultUserValueInStatus("Completed", "bond")))
				.createTextField("Best text ever", builder -> builder
						.listable().defaultValuesInStatuses(defaultValueBuilder -> defaultValueBuilder
								.addDefaultValueInStatus("New", "text1")
								.addDefaultValueInStatus("In Progress", "text2")
						))
				.createReferenceFieldOfType("My Reference2",
						referenceFilter -> referenceFilter.addTrackerTypeFilter(project,
								List.of(TrackerType.RELEASE, TrackerType.BUG, TrackerType.EPIC)),
						fieldProperties -> fieldProperties
								.defaultValuesInStatuses(dvb -> dvb
										.addDefaultTrackerItemValueInStatus("New", project, myBug.name(), "Bug1")
										.addDefaultTrackerItemValueInStatus("New", project, myBug.name(), "Bug2")
										.addDefaultTrackerItemValueInStatus("In Progress", project, myBug.name(), "Bug3")
								)
				)
				.buildAndAdd();

		// THEN
		TrackerConfiguration result = getTracker(tracker.id());
		List<TrackerField> fields = result.getFields();

		assertEquals(result.getBasicInformation().getName(), tracker.name());

		TrackerField members = getField("My Best Members", fields);
		Map<String, String> memberDefaults = members.getDefaultValuesInStatuses();
		assertEquals(memberDefaults.get("1"), "13-2,13-7");
		assertEquals(memberDefaults.get("7"), "1-1");

		TrackerField text = getField("Best text ever", fields);
		Map<String, String> textDefaults = text.getDefaultValuesInStatuses();
		assertEquals(textDefaults.get("1"), "text1");
		assertEquals(textDefaults.get("3"), "text2");

		TrackerField ref = getField("My Reference2", fields);
		Map<String, String> refDefaults = ref.getDefaultValuesInStatuses();
		assertEquals(refDefaults.get("1"), "9-%s,9-%s".formatted(bug1.id(), bug2.id()));
		assertEquals(refDefaults.get("3"), "9-%s".formatted(bug3.id()));
	}

	@Test(description = "Allowed values can be set via API")
	public void testSettingAllowedValues() {
		// GIVEN
		Tracker myBug = trackerApiService.createDefaultBugTracker(project, "MyBugAllowed");

		TrackerItemId bug1 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug1")
				.description("description"));

		TrackerItemId bug2 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug2")
				.description("description"));

		TrackerItemId bug3 = trackerItemApiService.createTrackerItem(project, myBug.name(), builder -> builder
				.name("Bug3")
				.description("description"));

		// WHEN
		Tracker tracker = trackerApiService.createTaskTracker(project, "TaskAllowed")
				.createMemberField("My Best Members", List.of(MemberType.USERS, MemberType.ROLES),
						trackerFieldBuilder -> trackerFieldBuilder.allowedValuesInStatuses(defaultValueBuilder ->
								defaultValueBuilder.addAllowedRoleValueInStatus("New", "Developer")
										.addAllowedRoleValueInStatus("New", "Tester")
										.addAllowedUserValueInStatus("Completed", "bond")))
				.createReferenceFieldOfType("My Reference2",
						referenceFilter -> referenceFilter.addTrackerTypeFilter(project,
								List.of(TrackerType.RELEASE, TrackerType.BUG, TrackerType.EPIC)),
						fieldProperties -> fieldProperties
								.allowedValuesInStatuses(dvb -> dvb
										.addAllowedTrackerItemValueInStatus("New", project, myBug.name(), "Bug1")
										.addAllowedTrackerItemValueInStatus("New", project, myBug.name(), "Bug2")
										.addAllowedTrackerItemValueInStatus("In Progress", project, myBug.name(), "Bug3")
								)
				)
				.buildAndAdd();

		// THEN
		TrackerConfiguration result = getTracker(tracker.id());
		List<TrackerField> fields = result.getFields();

		assertEquals(result.getBasicInformation().getName(), tracker.name());

		TrackerField members = getField("My Best Members", fields);
		Map<String, String> memberDefaults = members.getAllowedValuesInStatuses();
		assertEquals(memberDefaults.get("1"), "13-2,13-7");
		assertEquals(memberDefaults.get("7"), "1-1");

		TrackerField ref = getField("My Reference2", fields);
		Map<String, String> refDefaults = ref.getAllowedValuesInStatuses();
		assertEquals(refDefaults.get("1"), "9-%s,9-%s".formatted(bug1.id(), bug2.id()));
		assertEquals(refDefaults.get("3"), "9-%s".formatted(bug3.id()));
	}

	@Test(description = "Updating tracker permissions via API")
	public void testUpdatingTrackerPermissions() {
		// GIVEN
		Project permProject = projectApiService
				.createProject("Tracker Permission Test")
				.addRole("Permission tester", ProjectPermission.developer())
				.build();

		try {
			Tracker myBug = trackerApiService.createDefaultBugTracker(permProject, "MyBugPermission");

			List<RoleWithPermissions> trackerPermissions = getTrackerPermissions(myBug);
			assertPermissionsExistForRole("Test Engineer", trackerPermissions,
					List.of(TrackerPermission.ISSUE_ESCALATION_VIEW, TrackerPermission.EXPORT));

			assertPermissionsDoesNotExistForRole("Scrum Master", trackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION));

			assertPermissionsDoesNotExistForRole("Permission tester", trackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_MASS_EDIT));

			// WHEN
			trackerApiService.updateTrackerPermissions(myBug, builder -> builder
					.addPermissionForRole("Permission tester", TrackerPermission.DATA_ADMINISTRATION,
							TrackerPermission.ISSUE_MASS_EDIT)
					.addPermissionForRole("Permission tester", List.of(TrackerPermission.DATA_ADMINISTRATION))
					.addPermissionForRole("Scrum Master", List.of(TrackerPermission.DATA_ADMINISTRATION))
					.revokePermissionForRole("Test Engineer", TrackerPermission.DATA_ADMINISTRATION,
							TrackerPermission.ISSUE_ESCALATION_VIEW, TrackerPermission.EXPORT)
			);

			// THEN check roles
			List<RoleWithPermissions> modifiedTrackerPermissions = getTrackerPermissions(myBug);
			assertPermissionsExistForRole("Scrum Master", modifiedTrackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION));

			assertPermissionsExistForRole("Permission tester", modifiedTrackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_MASS_EDIT));

			assertPermissionsDoesNotExistForRole("Test Engineer", modifiedTrackerPermissions, List.of(TrackerPermission.EXPORT,
					TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_ESCALATION_VIEW));
		} finally {
			projectApiService.deleteProject(permProject);
		}
	}

	@Test(description = "Check tracker general settings can be modified")
	public void testSetTrackerGeneralSettings() {
		// GIVEN WHEN
		String key = "ETG";

		Tracker tracker = trackerApiService
				.createEpicTracker(project.id(), "Epic Tracker general",
						builder -> builder.color(HtmlColor.COLOR_454545)
								.key(key)
								.showAncestorItems())
				.createBooleanField("My Boolean", builder -> builder.mandatory())
				.createDateField("My Date field", builder -> builder.displayYear().displayMonth())
				.buildAndAdd();

		// THEN
		TrackerConfiguration result = getTracker(tracker.id());
		TrackerBasicInformation basicInformation = result.getBasicInformation();

		assertEquals(basicInformation.getKey(), key);
		assertEquals(basicInformation.getColor(), HtmlColor.COLOR_454545.getHexCode());
		assertTrue(basicInformation.getShowAncestorItems());
	}

	@Test(description = "Check tracker can be set and used as a template")
	public void testUseTrackerAsTemplate() {
		// GIVEN WHEN
		Tracker template = trackerApiService
				.createEpicTracker(project.id(), "Epic template",
						builder -> builder.template())
				.createBooleanField("My Boolean", builder -> builder.mandatory())
				.createDateField("My Date field", builder -> builder.displayYear().displayMonth())
				.buildAndAdd();

		Tracker trackerWithTemplate = trackerApiService
				.createEpicTracker(project.id(), "Epic with template",
						builder -> builder.templateId(template))
				.buildAndAdd();

		// THEN
		TrackerConfiguration templateResult = getTracker(template.id());
		assertTrue(templateResult.getBasicInformation().getTemplate());

		TrackerConfiguration withTemplateResult = getTracker(trackerWithTemplate.id());
		assertEquals(withTemplateResult.getBasicInformation().getTemplateId(), template.id().id());
	}

	@Test(description = "Existing fields can be updated in a tracker")
	public void testUpdateExistingFields() {
		// GIVEN
		Tracker epic = trackerApiService
				.createEpicTracker(project.id(), "Epic change")
				.createBooleanField("My First Boolean", builder -> builder.mandatory())
				.createTableField("My First Table", columns -> columns
						.createTextField("first table text")
						.createDurationField("Duration in table")
				)
				.createChoiceField("My Choice field")
				.buildAndAdd();

		List<TrackerField> fields = getTracker(epic.id()).getFields();

		TrackerField status = getField("Status", fields);
		assertNull(status.getDescription());

		TrackerField requirements = getField("Requirements", fields);
		assertTrue(requirements.getListable());
		assertFalse(requirements.getNewLine());
		assertTrue(requirements.getMultipleSelection());

		TrackerField myFirstBoolean = getField("My First Boolean", fields);
		assertTrue(myFirstBoolean.getMandatory());
		assertEquals(myFirstBoolean.getReferenceId(), 10_001);

		TrackerField table1 = getField("My First Table", fields);
		assertEquals(table1.getReferenceId(), 1_000_000);

		TrackerField table1Field1 = getField("first table text", fields);
		assertEquals(table1Field1.getReferenceId(), 1_000_001);
		assertEquals(table1Field1.getPosition(), 10);
		assertNull(table1Field1.getDescription());

		TrackerField table1Field2 = getField("Duration in table", fields);
		assertEquals(table1Field2.getReferenceId(), 1_000_002);
		assertEquals(table1Field2.getPosition(), 20);

		TrackerField myChoiceField = getField("My Choice field", fields);
		assertEquals(myChoiceField.getReferenceId(), 1_000);
		assertNull(myChoiceField.getTitle());

		// WHEN
		trackerApiService
				.updateTracker(project, epic.name())
				.createBooleanField("My Boolean", TrackerFieldBuilder::mandatory)
				.createDateField("My Date field", builder -> builder.displayYear().displayMonth())
				.updateField("Status", builder -> builder.description("Changed description of status field"))
				.updateReferenceFieldOfType("Requirements", referenceFilter ->
								referenceFilter.addTrackerTypeFilter(project, List.of(TrackerType.REQUIREMENT)),
						builder -> builder.listable(false)
								.newLine()
								.multipleSelection())
				.createTableField("My Table", columns -> columns
						.createTextField("yeah")
						.createDurationField("Second Duration in table")
				)
				.updateTableField("My First Table", columns -> columns
						.updateField("first table text", builder -> builder.description("desc set"))
						.createColorField("Table color field", TrackerFieldBuilder::mandatory)
				)
				.updateChoiceField("My Choice field", List.of(), builder -> builder.title("Best choice field title"))
				.buildAndAdd();

		// THEN
		fields = getTracker(epic.id()).getFields();

		status = getField("Status", fields);
		assertEquals(status.getDescription(), "Changed description of status field");

		requirements = getField("Requirements", fields);
		assertFalse(requirements.getListable());
		assertTrue(requirements.getNewLine());
		assertTrue(requirements.getMultipleSelection());

		myFirstBoolean = getField("My First Boolean", fields);
		assertTrue(myFirstBoolean.getMandatory());
		assertEquals(myFirstBoolean.getReferenceId(), 10_001);

		TrackerField myBoolean = getField("My Boolean", fields);
		assertTrue(myBoolean.getMandatory());
		assertEquals(myBoolean.getReferenceId(), 10_002);

		TrackerField myDateField = getField("My Date field", fields);
		assertTrue(myDateField.getDateFieldSettings().getDisplayYear());
		assertTrue(myDateField.getDateFieldSettings().getDisplayMonth());
		assertEquals(myDateField.getReferenceId(), 10_003);

		table1 = getField("My First Table", fields);
		assertEquals(table1.getReferenceId(), 1_000_000);

		table1Field1 = getField("first table text", fields);
		assertEquals(table1Field1.getReferenceId(), 1_000_001);
		assertEquals(table1Field1.getPosition(), 10);
		assertEquals(table1Field1.getDescription(), "desc set");

		table1Field2 = getField("Duration in table", fields);
		assertEquals(table1Field2.getReferenceId(), 1_000_002);
		assertEquals(table1Field2.getPosition(), 20);

		TrackerField table1Field3 = getField("Table color field", fields);
		assertEquals(table1Field3.getReferenceId(), 1_000_003);
		assertEquals(table1Field3.getPosition(), 30);
		assertTrue(table1Field3.getMandatory());

		TrackerField table2 = getField("My Table", fields);
		assertEquals(table2.getReferenceId(), 2_000_000);

		TrackerField table2Field1 = getField("yeah", fields);
		assertEquals(table2Field1.getReferenceId(), 2_000_001);
		assertEquals(table2Field1.getPosition(), 10);

		TrackerField table2Field2 = getField("Second Duration in table", fields);
		assertEquals(table2Field2.getReferenceId(), 2_000_002);
		assertEquals(table2Field2.getPosition(), 20);

		myChoiceField = getField("My Choice field", fields);
		assertEquals(myChoiceField.getReferenceId(), 1_000);
		assertEquals(myChoiceField.getTitle(), "Best choice field title");
	}

	@Test(description = "Requirements tracker can be updated with new reference field")
	public void testUpdateRequirementsTracker() {
		// GIVEN
		String fieldName = "New CRS referenced field";
		Tracker crsTracker = projectApiService.findTrackerByName(project, "Customer Requirement Specifications");

		// WHEN
		Tracker srsTracker = trackerApiService.updateTracker(project, "System Requirement Specifications")
				.createReferenceFieldOfTrackers(fieldName, referenceFilter -> referenceFilter
						.addTrackerFilterWithStatus(project, crsTracker.name(), StatusMeaning.INPROGRESS))
				.buildAndAdd();

		// THEN
		TrackerConfiguration config = getTracker(srsTracker.id());
		TrackerField field = getField(fieldName, config.getFields());

		assertNotNull(field);
		assertEquals(field.getReferenceId(), 1002);

		List<ReferenceFilterBasedChoiceReferenceFilter> filters = ((ChoiceWorkConfigItems) field.getChoiceOptionSetting())
				.getReferenceFilters();

		assertEquals(filters.size(), 1);

		ReferenceFilterBasedChoiceReferenceFilter filter = filters.getFirst();
		assertEquals(filter.getFilterStatusId().intValue(), 7);
		assertEquals(filter.getId(), srsTracker.id().id());
		assertEquals(filter.getDomainId(), crsTracker.id().id());
		assertEquals(filter.getDomainType(), ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.TRACKER);
	}

	@Test(description = "Test setting field dependencies")
	public void testSetFieldDependencies() {
		// GIVEN
		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldDep")
				.updateChoiceField("Severity", List.of(new ChoiceOption(1, "Blocker"),
								new ChoiceOption(2, "Critical"), new ChoiceOption(3, "Major"),
								new ChoiceOption(4, "Minor"), new ChoiceOption(5, "Trivial")),
						// WHEN
						trackerFieldBuilder -> trackerFieldBuilder.dependsOnField("Status",
								builder -> builder
										.addDependencyForOptions(List.of("1", "2"), List.of("1"))
										.addDependencyForEmptyDependant("2")))
				.buildAndAdd();

		// THEN
		List<TrackerField> fields = getTracker(tracker.id()).getFields();
		TrackerField status = getField("Status", fields);

		TrackerField severityWithDepends = getField("Severity", fields);

		TrackerFieldDependency dependency = severityWithDepends.getDependency();
		assertNotNull(dependency);
		assertEquals(dependency.getDependentFieldId(), status.getReferenceId());
		Map<String, String> valueCombinations = dependency.getValueCombinations();

		assertEquals(valueCombinations.size(), 2);
		assertThat(valueCombinations, hasEntry("1,2", "1"));
		assertThat(valueCombinations, hasEntry("NULL", "2"));
	}

	@Test(description = "Test setting field dependencies using Country and Language fields")
	public void testSetFieldDependenciesForCountryAndLanguageFields() {
		// GIVEN
		String languageFieldName = "Language";
		String countryFieldName = "Country";
		String choice1FieldName = "Choice depends on Language";
		String choice2FieldName = "Choice depends on Country";

		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldDep2")
				.createLanguageField(languageFieldName)
				.createCountryField(countryFieldName)
				.buildAndAdd();

		// WHEN
		// current limitation, depends on fields need to be created before
		trackerApiService.updateTracker(project, tracker.name())
				.createChoiceField(choice1FieldName, List.of(new ChoiceOption(10, "Choice1"),
								new ChoiceOption(20, "Choice2")),
						trackerFieldBuilder -> trackerFieldBuilder.dependsOnField(languageFieldName,
								builder -> builder
										.addDependencyForOptions(List.of(Language.DE.getShortName(), Language.EN.getShortName()),
												List.of("10"))
										.addDependencyForAnyDependant("20")))

				.createChoiceField(choice2FieldName, List.of(new ChoiceOption(5, "Choice5"),
								new ChoiceOption(8, "Choice8")),
						trackerFieldBuilder -> trackerFieldBuilder.dependsOnField(countryFieldName,
								builder -> builder
										.addDependencyForOptions(List.of(Country.AQ.getShortName(), Country.BB.getShortName(),
												Country.DM.getShortName()), List.of("8"))
										.addDependencyForAnyCurrentField(Country.PL.getShortName())))
				.buildAndAdd();

		// THEN
		List<TrackerField> fields = getTracker(tracker.id()).getFields();
		TrackerField languageField = getField(languageFieldName, fields);
		TrackerField countryField = getField(countryFieldName, fields);

		TrackerField choiceDependsOnLanguage = getField(choice1FieldName, fields);

		TrackerFieldDependency dependency = choiceDependsOnLanguage.getDependency();
		assertNotNull(dependency);
		assertEquals(dependency.getDependentFieldId(), languageField.getReferenceId());
		Map<String, String> valueCombinations = dependency.getValueCombinations();

		assertEquals(valueCombinations.size(), 2);
		assertThat(valueCombinations, hasEntry("DE,EN", "10"));
		assertThat(valueCombinations, hasEntry("*", "20"));

		// Country field
		TrackerField choiceDependsOnCountry = getField(choice2FieldName, fields);

		dependency = choiceDependsOnCountry.getDependency();
		assertNotNull(dependency);
		assertEquals(dependency.getDependentFieldId(), countryField.getReferenceId());
		valueCombinations = dependency.getValueCombinations();

		assertEquals(valueCombinations.size(), 2);
		assertThat(valueCombinations, hasEntry("AQ,BB,DM", "8"));
		assertThat(valueCombinations, hasEntry("PL", "*"));
	}

	@Test(description = "Test setting field dependencies for reference fields")
	public void testSetFieldDependenciesBetweenReferenceFields() {
		// GIVEN
		String bugs = "Bugs";
		String releases = "Releases";
		String bugReferenceFieldName = "Bug reference field";
		String releaseField = "Release";

		TrackerItemId bug1 = trackerItemApiService.createTrackerItem(project, bugs, builder -> builder.name("bug 1")
				.description(getRandomText()));

		TrackerItemId bug2 = trackerItemApiService.createTrackerItem(project, bugs, builder -> builder.name("bug 2")
				.description(getRandomText()));

		TrackerItemId release1 = trackerItemApiService.createTrackerItem(project, releases, builder -> builder.name("r1")
				.description(getRandomText()));

		TrackerItemId release2 = trackerItemApiService.createTrackerItem(project, releases, builder -> builder.name("r2")
				.description(getRandomText()));

		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldDep3")
				.createReferenceFieldOfType(bugReferenceFieldName,
						filter -> filter.addTrackerTypeFilter(project, List.of(TrackerType.BUG)))
				.buildAndAdd();

		// WHEN
		// current limitation, depends on fields need to be created before
		trackerApiService.updateTracker(project, tracker.name())
				.updateReferenceFieldOfType(releaseField,
						filter -> filter.addTrackerTypeFilter(project, List.of(TrackerType.RELEASE)),
						trackerFieldBuilder -> trackerFieldBuilder.dependsOnField(bugReferenceFieldName,
								builder -> builder
										.addDependencyForReferences(List.of(bug1, bug2), List.of(release1))
										.addDependencyForEmptyCurrentFieldReference(bug1)
										.addDependencyForAnyDependantReference(release2)))
				.buildAndAdd();

		// THEN
		List<TrackerField> fields = getTracker(tracker.id()).getFields();
		TrackerField bugReferenceField = getField(bugReferenceFieldName, fields);

		TrackerField releaseDependsOnBugRef = getField(releaseField, fields);

		TrackerFieldDependency dependency = releaseDependsOnBugRef.getDependency();
		assertNotNull(dependency);
		assertEquals(dependency.getDependentFieldId(), bugReferenceField.getReferenceId());
		Map<String, String> valueCombinations = dependency.getValueCombinations();

		assertEquals(valueCombinations.size(), 3);

		String referencePattern = "9-%s";
		String bug1Andbug2 = referencePattern.formatted(bug1.id()) + "," + referencePattern.formatted(bug2.id());
		assertThat(valueCombinations, hasEntry(bug1Andbug2, referencePattern.formatted(release1.id())));
		assertThat(valueCombinations, hasEntry(referencePattern.formatted(bug1.id()), "NULL"));
		assertThat(valueCombinations, hasEntry("*", referencePattern.formatted(release2.id())));
	}

	@Test(description = "Test create choice field with shared fields")
	public void testCreateChoiceFieldWithSharedFields() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();
		sharedFieldApiService.createSharedField("my_shared_field_1", SharedFieldBuilder::choiceField);
		sharedFieldApiService.createSharedField("my_shared_field_2", SharedFieldBuilder::choiceField);
		SharedField sharedField1 = sharedFieldApiService.getSharedFieldByName("my_shared_field_1");
		SharedField sharedField2 = sharedFieldApiService.getSharedFieldByName("my_shared_field_2");

		try {
			// WHEN
			Tracker tracker = trackerApiService.createTaskTracker(project, getRandomText())
					.createChoiceField("My choice", List.of(
							new ChoiceOption(1, "op 1"),
							new ChoiceOption(2, "op 2")), trackerFieldBuilder ->
							trackerFieldBuilder.sharedFields("my_shared_field_1", "my_shared_field_2"))
					.buildAndAdd();

			// THEN
			TrackerConfiguration result = getTracker(tracker.id());
			List<TrackerField> fields = result.getFields();

			TrackerField choiceFieldWithSharedFields = getField("My choice", fields);
			List<Integer> sharedFieldIdList = choiceFieldWithSharedFields.getGlobalTypeIds();
			assertNotNull(sharedFieldIdList);
			assertEquals(sharedFieldIdList.size(), 2);
			assertEquals(sharedFieldIdList.getFirst(), sharedField1.id().id());
			assertEquals(sharedFieldIdList.get(1), sharedField2.id().id());

		} finally {
			projectApiService.deleteProject(project2.id());
			sharedFieldApiService.deleteSharedFieldByName("my_shared_field_1");
			sharedFieldApiService.deleteSharedFieldByName("my_shared_field_2");
		}
	}

	@Test(description = "Test create reference field with tracker item type shared field")
	public void testCreateChoiceFieldWithReferenceSharedFields() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();
		sharedFieldApiService.createSharedField("my_shared_field", SharedFieldBuilder::referenceFieldWithTrackerItemType);
		SharedField sharedField = sharedFieldApiService.getSharedFieldByName("my_shared_field");
		String trackerName = getRandomText();
		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName).buildAndAdd();
		trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder.name("Task1")
				.description(getRandomText()));

		try {
			// WHEN
			trackerApiService
					.updateTracker(project, trackerName)
					.createReferenceFieldOfTrackers("My Reference",
							referenceFilter -> referenceFilter.addTrackerFilter(project, trackerName),
							builder -> builder.sharedFields("my_shared_field"))
					.buildAndAdd();

			// THEN
			List<TrackerField> fields = getTracker(tracker.id()).getFields();

			TrackerField referenceFieldWithSharedFields = getField("My Reference", fields);
			List<Integer> sharedFieldIdList = referenceFieldWithSharedFields.getGlobalTypeIds();
			assertNotNull(sharedFieldIdList);
			assertEquals(sharedFieldIdList.getFirst(), sharedField.id().id());
		} finally {
			projectApiService.deleteProject(project2.id());
			sharedFieldApiService.deleteSharedFieldByName("my_shared_field");
		}
	}

	@Test(description = "Test setting mandatory except in statuses list")
	public void testSetMandatoryExceptInStatuses() {
		// GIVEN
		String fieldName = "Color mandatory test";

		// WHEN
		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldMandatoryExcept1")
				.createColorField(fieldName, builder -> builder
						.mandatory(true)
						.mandatoryExceptInStatuses("New", "In Progress", "InQA"))
				.buildAndAdd();

		// THEN
		assertMandatoryFields(tracker, fieldName);
	}

	@Test(description = "Test updating mandatory except in statuses list")
	public void tesUpdatingMandatoryExceptInStatuses() {
		// GIVEN
		String fieldName = "Color mandatory test";

		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldMandatoryExcept2")
				.createColorField(fieldName)
				.buildAndAdd();

		List<Integer> mandatoryExceptInStatus = getField(fieldName, getTracker(tracker.id()).getFields())
				.getMandatoryExceptInStatus();
		assertNotNull(mandatoryExceptInStatus);
		assertEquals(mandatoryExceptInStatus.size(), 0);

		// WHEN
		trackerApiService.updateTracker(project, tracker.name())
				.updateField(fieldName, builder -> builder
						.mandatory(true)
						.mandatoryExceptInStatuses("New", "In Progress", "InQA"))
				.buildAndAdd();

		// THEN
		assertMandatoryFields(tracker, fieldName);
	}

	@Test(description = "Test updating mandatory except in statuses list")
	public void tesClearMandatoryExceptInStatuses() {
		// GIVEN
		String fieldName = "Color mandatory test";

		Tracker tracker = trackerApiService
				.createTaskTracker(project.id(), "TaskFieldMandatoryExcept3")
				.createColorField(fieldName, builder -> builder
						.mandatory(true)
						.mandatoryExceptInStatuses("New", "In Progress", "InQA"))
				.buildAndAdd();

		assertMandatoryFields(tracker, fieldName);

		// WHEN
		trackerApiService.updateTracker(project, tracker.name())
				.updateField(fieldName, TrackerFieldBuilder::clearMandatoryExceptInStatuses)
				.buildAndAdd();

		// THEN
		List<Integer> mandatoryExceptInStatus = getField(fieldName, getTracker(tracker.id()).getFields())
				.getMandatoryExceptInStatus();
		assertNotNull(mandatoryExceptInStatus);
		assertEquals(mandatoryExceptInStatus.size(), 0);
	}

	@Test(description = "Test State transition update with a new guard")
	public void testAddHasReviewGuardToAStateTransition() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();
		try {
			// WHEN
			String transitionName = "Define";
			String guardName = "Has review guard";
			Tracker tracker = trackerApiService.createDefaultRequirementTracker(project2, getRandomText());
			TrackerView guard = trackerApiService.createTrackerView(tracker.id(), trackerViewBuilder -> trackerViewBuilder
					.name(guardName)
					.condition(HAS_REVIEW)
					.isPublic(true)
					.type(ISSUE_CHANGES_FILTER)
					.creationType(GUARD));
			trackerApiService.updateStateTransitions(tracker)
					.updateStateTransition(transitionName, transitionBuilder -> transitionBuilder
							.name(transitionName)
							.guard(guard.id())
					)
					.buildAndAdd();

			// THEN
			FiltersView trackerView = findTrackerView(tracker.id(), guard.id());
			assertEquals(trackerView.getName(), guardName);
			assertEquals(trackerView.getPublic(), Boolean.TRUE);
			assertEquals(trackerView.getType(), 4);
			assertEquals(trackerView.getCreationType(), "guard");

			TrackerStateTransition updatedTransition = findStateTransitions(tracker.id()).getTransitionsJson().stream()
					.filter(it -> it.getName().equals(transitionName))
					.findFirst()
					.orElseThrow();
			assertEquals(updatedTransition.getGuard().getId(), guard.id().id());
			assertEquals(updatedTransition.getGuard().getName(), guardName);
		} finally {
			projectApiService.deleteProject(project2.id());
		}
	}

	@Test(description = "Test State transition creation")
	public void testCreateStateTransitionWithActions() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();

		try {
			// WHEN
			Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project2, getRandomText());
			Tracker usTracker = trackerApiService.createDefaultUserStoryTracker(project2, getRandomText());
			TrackerItemId userStoryId = trackerItemApiService.createTrackerItem(usTracker, builder -> builder
					.name("UserStory")
					.description("Item desc 2"));

			String transitionName = "MyTransition";
			trackerApiService.updateStateTransitions(taskTracker)
					.addStateTransition(transitionBuilder -> transitionBuilder
							.fromStatus("New")
							.toStatus("Suspended")
							.name(transitionName)
							.updateItemPropertiesAction(actionBuilder -> actionBuilder
									.fieldUpdates(fieldUpdateBuilder -> fieldUpdateBuilder
											.field("Assigned to")
											.memberValue(apiUser)
									)
							)
							.updateReferringItemsAction(usTracker.id(), actionBuilder -> actionBuilder
									.upstreamField("Subject")
									.statuses("ToDo")
									.fieldUpdates(fieldUpdateBuilder -> fieldUpdateBuilder
											.field("Assigned to")
											.memberValue(apiUser)
									)
							)
							.createReferringItemsAction(taskTracker.id(), actionBuilder -> actionBuilder
									.downstreamField("Parent")
									.fieldUpdates(
										fieldUpdateBuilder -> fieldUpdateBuilder
														.field("Summary")
														.textValue("apple"),
										fieldUpdateBuilder -> fieldUpdateBuilder
												.field("Description")
												.textValue("description"),
										fieldUpdateBuilder -> fieldUpdateBuilder
														.field("Subject")
														.itemReference(userStoryId)
									)
							)
					)
					.buildAndAdd();

			// THEN
			StateTransitionsResponse stateTransitions = findStateTransitions(taskTracker.id());
			TrackerStateTransition newTransition = stateTransitions.getTransitionsJson().stream()
					.filter(t -> t.getName().equals(transitionName))
					.findFirst()
					.orElseThrow();
			assertNull(newTransition.getGuard());
			assertEquals(newTransition.getActions().size(), 3);

			TransitionActionInfo updateItemPropertiesAction = newTransition.getActions().get(0);
			assertEquals(updateItemPropertiesAction.getFieldUpdates().size(), 1);
			assertEquals(updateItemPropertiesAction.getFieldUpdates().get(0).getFieldId(), 5);
			assertEquals(getChoiceIds(((ChoiceFieldMultiValue) updateItemPropertiesAction.getFieldUpdates().get(0).getFieldValue())), List.of(apiUser.getId()));

			TransitionActionInfo updateReferringItemsAction = newTransition.getActions().get(1);
			assertEquals(updateReferringItemsAction.getFieldUpdates().size(), 1);
			assertEquals(updateReferringItemsAction.getFieldUpdates().get(0).getFieldId(), 5);
			assertEquals(getChoiceIds(((ChoiceFieldMultiValue) updateReferringItemsAction.getFieldUpdates().get(0).getFieldValue())), List.of(apiUser.getId()));
			assertEquals(updateReferringItemsAction.getReference().getTracker(), usTracker.id().id());
			assertEquals(updateReferringItemsAction.getReference().getRefType(), "17|10");
			assertEquals(updateReferringItemsAction.getReference().getStatuses(), List.of(1));

			TransitionActionInfo createReferringItemsAction = newTransition.getActions().get(2);
			assertEquals(createReferringItemsAction.getFieldUpdates().size(), 3);
			assertEquals(createReferringItemsAction.getFieldUpdates().get(0).getFieldId(), 3);
			assertEquals(((TextFieldValue)createReferringItemsAction.getFieldUpdates().get(0).getFieldValue()).getValue(), "apple");
			assertEquals(createReferringItemsAction.getFieldUpdates().get(2).getFieldId(), 80);
			assertEquals(((WikiTextFieldMultiValue)createReferringItemsAction.getFieldUpdates().get(2).getFieldValue()).getValue(), "description");
			assertEquals(createReferringItemsAction.getFieldUpdates().get(1).getFieldId(), 17);
			assertEquals(((ChoiceFieldMultiValue)createReferringItemsAction.getFieldUpdates().get(1).getFieldValue()).getValues().getFirst().getId(), userStoryId.id());
			assertEquals(createReferringItemsAction.getReference().getTracker(), taskTracker.id().id());
			assertEquals(createReferringItemsAction.getReference().getRefType(), "Parent|6");
		} finally {
			projectApiService.deleteProject(project2.id());
		}
	}

	@Test(description = "Test change handler creation")
	public void testCreateChangeHandler() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();

		try {
			// WHEN
			Tracker tracker = trackerApiService.createDefaultTaskTracker(project2, getRandomText());
			trackerApiService.updateStateTransitions(tracker)
					.addChangeHandler(builder -> builder
							.status("New")
							.startReviewAction(actionBuilder -> actionBuilder
									.params(reviewParamsBuilder -> reviewParamsBuilder
											.reviewer(apiUser)
											.reviewParams(1, 1)
									)
							)
					)
					.buildAndAdd();

			// THEN
			StateTransitionsResponse stateTransitions = findStateTransitions(tracker.id());
			TrackerStateTransition changeHandler = stateTransitions.getTransitionsJson().stream()
					.filter(t -> t.getName().equals("FieldUpdate"))
					.findFirst()
					.orElseThrow();
			assertNull(changeHandler.getGuard());
			assertEquals(changeHandler.getEventId(), 3);
			assertEquals(changeHandler.getActions().size(), 1);

			TransitionActionInfo startReviewAction = changeHandler.getActions().get(0);
			ActionParameter parameters = startReviewAction.getParameters();
			assertEquals(parameters.getReview().getValue().getApprovals(), 1);
			assertEquals(parameters.getReview().getValue().getRejects(), 1);
			assertEquals(parameters.getReview().getValue().getSignature(), 0);
			assertEquals(parameters.getReviewers().getValue().getFirst().getId(), "1-" + apiUser.getUserId().id());
			assertEquals(parameters.getReviewers().getValue().getFirst().getLabel(), apiUser.getName());
		} finally {
			projectApiService.deleteProject(project2.id());
		}
	}

	@Test(description = "Test state transition deletion")
	public void testDeleteStateTransition() {
		// GIVEN
		Project project2 = projectApiService.createProjectFromTemplate();
		try {
			Tracker tracker = trackerApiService.createDefaultRequirementTracker(project2, getRandomText());

			String transitionName = "Define";
			List<TrackerStateTransition> allTransitions = findStateTransitions(tracker.id()).getTransitionsJson();
			assertTrue(allTransitions.stream().anyMatch(t -> t.getName().equals(transitionName)));

			// WHEN
			trackerApiService.updateStateTransitions(tracker)
					.deleteTransition(transitionName)
					.buildAndAdd();

			// THEN
			List<TrackerStateTransition> remainingTransitions = findStateTransitions(tracker.id()).getTransitionsJson();
			assertFalse(remainingTransitions.stream().anyMatch(t -> t.getName().equals(transitionName)));

		} finally {
			projectApiService.deleteProject(project2.id());
		}
	}

	@DataProvider
	private Object[][] defaultTrackerCreation() {
		return new Object[][] {
				{ "createDefaultUserStoryTracker", "MyDefaultUserStory", Boolean.TRUE },
				{ "createDefaultBugTracker", "MyDefaultBug", Boolean.TRUE },
				{ "createDefaultEpicTracker", "MyDefaultEpic", Boolean.TRUE },
				{ "createDefaultChangeRequestTracker", "MyDefaultChangeRequest", Boolean.TRUE },
				{ "createDefaultTeamTracker", "MyDefaultTeam", Boolean.FALSE },
				{ "createDefaultTaskTracker", "MyDefaultTask", Boolean.TRUE },
				{ "createDefaultReleaseTracker", "MyDefaultRelease", Boolean.TRUE },
				{ "createDefaultTestConfigurationTracker", "MyDefaultTestConfig", Boolean.TRUE },
				{ "createDefaultRiskTracker", "MyDefaultRisk", Boolean.TRUE },
				{ "createDefaultTestCaseTracker", "MyDefaultTestCase", Boolean.TRUE },
				{ "createDefaultTestConfigurationTracker", "MyDefaultTestConfiguration", Boolean.TRUE },
				{ "createDefaultTestSetTracker", "MyDefaultTestSet", Boolean.TRUE },
				{ "createDefaultAreaTracker", "MyDefaultArea", Boolean.FALSE },
				{ "createDefaultIssueTracker", "MyDefaultIssue", Boolean.TRUE },
				{ "createDefaultWorkLogTracker", "MyDefaultWorklog", Boolean.TRUE },
				{ "createDefaultTestRunTracker", "MyDefaultTestRun", Boolean.TRUE },
				{ "createDefaultDocumentTracker", "MyDefaultDocument", Boolean.TRUE },
				{ "createDefaultConfigItemTracker", "MyDefaultConfigItem", Boolean.TRUE },
				{ "createDefaultComponentTracker", "MyDefaultComponent", Boolean.TRUE },
				{ "createDefaultPlatformTracker", "MyDefaultPlatform", Boolean.TRUE },
				{ "createDefaultContactTracker", "MyDefaultContact", Boolean.TRUE },
				{ "createDefaultRpeReportTracker", "MyDefaultRpeReport", Boolean.TRUE }
		};
	}

	private void assertMandatoryFields(Tracker tracker, String fieldName) {
		List<TrackerField> fields = getTracker(tracker.id()).getFields();
		TrackerField field = getField(fieldName, fields);

		List<Integer> mandatoryExceptInStatus = field.getMandatoryExceptInStatus();
		assertNotNull(mandatoryExceptInStatus);
		assertEquals(mandatoryExceptInStatus.size(), 3);
		assertEquals(mandatoryExceptInStatus, List.of(1, 3 ,6));
	}

	private void assertPermissionsExistForRole(String roleName, List<RoleWithPermissions> actualTrackerPermissions,
			List<TrackerPermission> permissionsToTest) {
		List<Integer> actualIds = getActualPermissionIds(roleName, actualTrackerPermissions);

		assertTrue(permissionsToTest.stream()
				.map(Enum::ordinal)
				.allMatch(actualIds::contains));
	}

	private void assertPermissionsDoesNotExistForRole(String roleName, List<RoleWithPermissions> actualTrackerPermissions,
			List<TrackerPermission> permissionsToTest) {
		List<Integer> actualIds = getActualPermissionIds(roleName, actualTrackerPermissions);

		assertFalse(permissionsToTest.stream()
				.map(Enum::ordinal)
				.anyMatch(actualIds::contains));
	}

	private List<Integer> getActualPermissionIds(String roleName, List<RoleWithPermissions> actualTrackerPermissions) {
		RoleWithPermissions roleWithPermissions = actualTrackerPermissions.stream()
				.filter(p -> p.getRole().getName().equals(roleName))
				.findFirst().get();

		return roleWithPermissions.getTrackerPermissions().stream()
				.map(TrackerPermissionReference::getId)
				.toList();
	}

	private ReferenceFilterBasedChoiceReferenceFilter getReferenceFilter(
			List<ReferenceFilterBasedChoiceReferenceFilter> referenceFilters, ProjectId id) {
		return referenceFilters.stream().filter(f -> f.getDomainId().equals(id.id())).findFirst().get();
	}

	private void assertOptions(ChoiceOptions choiceOptions, String opName, int expectedId) {
		ChoiceOptionsChoiceOption op1 = choiceOptions.getChoiceOptions().stream()
				.filter(option -> option.getName().equals(opName))
				.findFirst().get();

		assertEquals(op1.getId(), expectedId);
	}

	private TrackerField getField(String fieldName, List<TrackerField> fields) {
		return fields.stream()
				.filter(f -> fieldName.equals(f.getLabel()))
				.findFirst()
				.orElse(null);
	}

	private List<Integer> getChoiceIds(ChoiceFieldMultiValue choiceFieldMultiValue) {
		return choiceFieldMultiValue.getValues().stream()
				.map(AbstractReference::getId)
				.toList();
	}

}
