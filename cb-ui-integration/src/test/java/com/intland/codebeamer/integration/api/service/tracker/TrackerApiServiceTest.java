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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.builder.choice.MemberType;
import com.intland.codebeamer.integration.api.builder.permission.FieldPermissionBuilder;
import com.intland.codebeamer.integration.api.builder.reference.StatusMeaning;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.project.ProjectPermission;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermission;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.ChoiceMembers;
import com.intland.swagger.client.model.ChoiceOptions;
import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;
import com.intland.swagger.client.model.ChoiceWorkConfigItems;
import com.intland.swagger.client.model.ReferenceFilterBasedChoiceReferenceFilter;
import com.intland.swagger.client.model.RoleWithPermissions;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.TrackerPermissionReference;

public class TrackerApiServiceTest extends AbstractBaseNGTests {

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private Project project;

	@BeforeClass(alwaysRun = true)
	public void setup() throws Exception {
		projectApiService = new ProjectApiService(getApplicationConfiguration());
		trackerApiService = new TrackerApiService(getApplicationConfiguration());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration());

		project = projectApiService.createProjectFromTemplate();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		projectApiService.deleteProject(project.id());
	}

	@Test(description = "Default trackers can be created via API")
	public void createDefaultTrackersViaApi() throws Exception {
		// GIVEN
		Map<String, String> input = new HashMap<>();
		input.put("createDefaultUserStoryTracker", "MyDefaultUserStory");
		input.put("createDefaultBugTracker", "MyDefaultBug");
		input.put("createDefaultEpicTracker", "MyDefaultEpic");
		input.put("createDefaultChangeRequestTracker", "MyDefaultChangeRequest");
		input.put("createDefaultTeamTracker", "MyDefaultTeam");
		input.put("createDefaultTaskTracker", "MyDefaultTask");
		input.put("createDefaultReleaseTracker", "MyDefaultRelease");
		input.put("createDefaultTestConfigurationTracker", "MyDefaultTestConfig");
		input.put("createDefaultRiskTracker", "MyDefaultRisk");
		input.put("createDefaultTestCaseTracker", "MyDefaultTestCase");
		input.put("createDefaultTestConfigurationTracker", "MyDefaultTestConfiguration");
		input.put("createDefaultTestSetTracker", "MyDefaultTestSet");

		for (Map.Entry<String, String> entry : input.entrySet()) {
			// WHEN
			Method createMethod = TrackerApiService.class.getDeclaredMethod(entry.getKey(), Project.class, String.class);
			Tracker tracker = (Tracker) createMethod.invoke(trackerApiService, project, entry.getValue());

			// THEN
			TrackerConfiguration trackerResult = getTracker(tracker.id());
			assertEquals(trackerResult.getBasicInformation().getName(), entry.getValue());
		}
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
									.createDateField("My date", builder -> builder.displayYear(true).displayMonth(true)))
					.createMemberField("My Best Members", List.of(MemberType.USERS, MemberType.ROLES))
					.createChoiceField("My choice", List.of(
							new ChoiceOption(1, "op 1"),
							new ChoiceOption(2, "op 2")), trackerFieldBuilder -> trackerFieldBuilder
							.withPermissionSingle(permBuilder))
					.createTextField("Best text ever", builder -> builder
							.listable(true)
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
						.listable(true)
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
						.listable(true).defaultValuesInStatuses(defaultValueBuilder -> defaultValueBuilder
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
			assertPermissionsExistForRole("Test Engineer", trackerPermissions, List.of(TrackerPermission.ISSUE_ESCALATION_VIEW,
							TrackerPermission.EXPORT));

			assertPermissionsDoesNotExistForRole("Scrum Master", trackerPermissions, List.of(TrackerPermission.DATA_ADMINISTRATION));

			assertPermissionsDoesNotExistForRole("Permission tester", trackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_MASS_EDIT));

			// WHEN
			trackerApiService.updateTrackerPermissions(myBug, builder -> builder
					.addPermissionForRole("Permission tester", TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_MASS_EDIT)
					.addPermissionForRole("Permission tester", List.of(TrackerPermission.DATA_ADMINISTRATION))
					.addPermissionForRole("Scrum Master", List.of(TrackerPermission.DATA_ADMINISTRATION))
					.revokePermissionForRole("Test Engineer", TrackerPermission.DATA_ADMINISTRATION,
							TrackerPermission.ISSUE_ESCALATION_VIEW, TrackerPermission.EXPORT)
			);

			// THEN check roles
			List<RoleWithPermissions> modifiedTrackerPermissions = getTrackerPermissions(myBug);
			assertPermissionsExistForRole("Scrum Master", modifiedTrackerPermissions, List.of(TrackerPermission.DATA_ADMINISTRATION));

			assertPermissionsExistForRole("Permission tester", modifiedTrackerPermissions,
					List.of(TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_MASS_EDIT));

			assertPermissionsDoesNotExistForRole("Test Engineer", modifiedTrackerPermissions, List.of(TrackerPermission.EXPORT,
					TrackerPermission.DATA_ADMINISTRATION, TrackerPermission.ISSUE_ESCALATION_VIEW));
		} finally {
			projectApiService.deleteProject(permProject);
		}
	}

	private void assertPermissionsExistForRole(String roleName, List<RoleWithPermissions> actualTrackerPermissions,
			List<TrackerPermission> permissionsToTest) {
		List<Integer> actualIds = getActualPermissionIds(roleName, actualTrackerPermissions);

		assertTrue(permissionsToTest.stream()
				.map(Enum::ordinal)
				.allMatch(id -> actualIds.contains(id)));
	}

	private void assertPermissionsDoesNotExistForRole(String roleName, List<RoleWithPermissions> actualTrackerPermissions,
			List<TrackerPermission> permissionsToTest) {
		List<Integer> actualIds = getActualPermissionIds(roleName, actualTrackerPermissions);

		assertFalse(permissionsToTest.stream()
				.map(Enum::ordinal)
				.anyMatch(id -> actualIds.contains(id)));
	}

	private  List<Integer> getActualPermissionIds(String roleName, List<RoleWithPermissions> actualTrackerPermissions) {
		RoleWithPermissions roleWithPermissions = actualTrackerPermissions.stream()
				.filter(p -> p.getRole().getName().equals(roleName))
				.findFirst().get();

		return roleWithPermissions.getTrackerPermissions().stream()
				.map(TrackerPermissionReference::getId)
				.toList();
	}

	private ReferenceFilterBasedChoiceReferenceFilter getReferenceFilter(
			List<ReferenceFilterBasedChoiceReferenceFilter> referenceFilters,
			ProjectId id) {
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
				.get();
	}

}
