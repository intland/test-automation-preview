package com.intland.codebeamer.integration.testcase.classic.tracker.config;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = { "TrackerConfigurationFieldsTabTestCases" })
public class TrackerConfigurationFieldsTabTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13280621")
	@Test(description = "User is able to create a new choice field with union option")
	public void createAChoiceFieldDependencyWithUnionOption() {
		final String fieldName = "Custom Choice";
		// GIVEN
		Tracker tracker = trackerApiService.createTaskTracker(project, "TaskCustom")
				.createChoiceField(fieldName)
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.setDependsOnSelector("Priority")
						.setUnion(true)
						.save())
				.trackerConfigFieldsTab((c -> c.save()))
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.assertThat()
						.assertLabel(fieldName)
						.assertType(FieldType.CHOICE)
						.assertDependsOnIsNotDefault()
						.assertUnion(true)
						.close());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264781")
	@Test(description = "User is able to edit Field Description in Tracker Configuration")
	public void editFieldDescriptionInTrackerConfiguration() {
		// GIVEN
		final String fieldName = "Resolution";
		final String trackerName = "Tasks";
		final String newDescription = "\"one pair\" and \"another pair\"";
		Tracker tracker = projectApiService.findTrackerByName(project, trackerName);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.setDescription(newDescription)
						.save())
				.trackerConfigFieldsTab((c -> c.save()))
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.assertThat()
						.assertLabel(fieldName)
						.assertType(FieldType.CHOICE)
						.assertDescription(newDescription)
						.close());
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13626494")
	@Test(description = "User can transition items to a new state if the current field values are among the allowed ones.")
	public void statusDependentDefaultValueWithOtherReferenceItemStatusIsChanged() {
		// GIVEN
		TrackerItem release = new TrackerItem(trackerItemApiService.createTrackerItem(project, "Releases",
				builder -> builder.name("Sprint 1")), "Sprint 1");

		Tracker tracker = trackerApiService.createTaskTracker(project, "My Tasks 1")
				.updateField("Release", fieldBuilder -> fieldBuilder
						.defaultValuesInStatuses(s -> s.addDefaultTrackerItemValueInStatus("In Progress", release.id()))
						.allowedValuesInStatuses(s -> s.addAllowedTrackerItemValueInStatus("In Progress", release.id())))
				.buildAndAdd();

		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, tracker.name(),
				builder -> builder.name("My Item").description("My Description"));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tracker, trackerItem)
				.initiateOverlayStateTransition("Start")
				.assertFieldFormComponent(a -> a
						.choiceField("Status", c -> c.is("In progress"))
						.referenceField("Release", r -> r.is(release)))
				.save()
				.redirectedToTrackerItemPage()
				.assertFieldFormComponent(a -> a.choiceField("Status", c -> c.is("In progress")));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13626493")
	@Test(description = "User can’t transition items to a new state if the current field values aren’t among the allowed ones.")
	public void statusDependentDefaultValueWithOtherReferenceItemErrorOnSavingTheStateTransition() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, "My Tasks 2");
		TrackerItemId allowedRelease = trackerItemApiService.createTrackerItem(project, "Releases",
				builder -> builder.name("Sprint 1"));
		TrackerItem otherRelease = new TrackerItem(trackerItemApiService.createTrackerItem(project, "Releases",
				builder -> builder.name("Sprint 2")), "Sprint 2");
		TrackerItemId item = trackerItemApiService.createTrackerItem(project, tracker.name(),
				builder -> builder
						.name("Name")
						.description("Description")
						.setTrackerItemFor("Release", otherRelease));

		trackerApiService.updateTracker(project, tracker.name())
				.updateField("Release", fieldBuilder -> fieldBuilder
						.defaultValuesInStatuses(s -> s.addDefaultTrackerItemValueInStatus("In Progress", allowedRelease))
						.allowedValuesInStatuses(s -> s.addAllowedTrackerItemValueInStatus("In Progress", allowedRelease)))
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tracker, item)
				.initiateOverlayStateTransition("Start")
				.assertFieldFormComponent(a -> a
						.choiceField("Status", c -> c.is("In progress"))
						.referenceField("Release", r -> r.is(otherRelease)))
				.clickSave()
				.assertFieldFormComponent(a -> a
						.hasError()
						.choiceField("Status", c -> c.is("In progress"))
						.referenceField("Release", r -> r.is(otherRelease)));

	}
}
