package com.intland.codebeamer.integration.testcase.classic.tracker.config;

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.common.tracker.TrackerLayoutLabel;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "TrackerConfigurationTestCases")
public class TrackerConfigurationTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private static final List<TrackerLayoutLabel> configTaskTrackerRowsIds = List.of(TrackerLayoutLabel.ID,
			TrackerLayoutLabel.PARENT,
			TrackerLayoutLabel.PRIORITY, TrackerLayoutLabel.NAME, TrackerLayoutLabel.STATUS, TrackerLayoutLabel.SUBJECT,
			TrackerLayoutLabel.TEAM, TrackerLayoutLabel.SEVERITY, TrackerLayoutLabel.RESOLUTION, TrackerLayoutLabel.PLATFORM,
			TrackerLayoutLabel.CATEGORY, TrackerLayoutLabel.VERSION, TrackerLayoutLabel.ASSIGNED_AT,
			TrackerLayoutLabel.ASSIGNED_TO, TrackerLayoutLabel.SUBMITTED_AT, TrackerLayoutLabel.SUBMITTED_BY,
			TrackerLayoutLabel.MODIFIED_AT, TrackerLayoutLabel.MODIFIED_BY, TrackerLayoutLabel.SUPERVISOR,
			TrackerLayoutLabel.START_DATE, TrackerLayoutLabel.END_DATE, TrackerLayoutLabel.STORY_POINTS,
			TrackerLayoutLabel.ESTIMATED_H, TrackerLayoutLabel.SPENT_H, TrackerLayoutLabel.SPENT_ESTIMATED_H,
			TrackerLayoutLabel.CLOSED_AT, TrackerLayoutLabel.DESCRIPTION, TrackerLayoutLabel.ATTACHMENTS);

	@Override
	protected void generateDataBeforeClass() throws Exception {

		activeUser = getDataManagerService().getUserApiServiceWithConfigUser()
				.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13345514")
	@Test(description = "Verify fields id in Choice Field tracker without offset")
	public void verifyFieldIdInChoiceFieldTrackerWithOutOffset() {
		// GIVEN
		final String trackerName = "Check custom field tracker";
		final String fieldChoiceName = "New Choice Field";
		final String fieldTextName = "New CustomText Field";
		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName)
				.createChoiceField(fieldChoiceName, List.of(
						new ChoiceOption(1, "op 1"),
						new ChoiceOption(2, "op 2")))
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newCustomField()
						.setLabel(fieldTextName)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.assertThat()
						.assertFieldOrder(configTaskTrackerRowsIds)
						.assertFieldIdBetween(fieldTextName, 10000, 19999))
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldChoiceName).assertThat()
						.assertLabel(fieldChoiceName));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13345512")
	@Test(description = "Verify fields id in Custom Field tracker without offset")
	public void verifyFieldIdInCustomFieldTrackerWithOutOffset() {
		// GIVEN
		final String trackerName = "Check Custom field tracker";
		final String fieldChoiceName = "New Choice Field";
		final String fieldTextName = "New CustomText Field";
		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName)
				.createTextField(fieldTextName)
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newChoiceField()
						.setLabel(fieldChoiceName)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.assertThat()
						.assertFieldOrder(configTaskTrackerRowsIds)
						.assertFieldIdBetween(fieldChoiceName, 1000, 9999))
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldTextName).assertThat()
						.assertLabel(fieldTextName));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13345516")
	@Test(description = "Verify fields id in Table Field tracker without offset")
	public void verifyFieldIdInTableFieldTrackerWithOutOffset() {
		// GIVEN
		final String trackerName = "Check Table field tracker";
		final String fieldTableName1 = "New Table Field1";
		final String fieldCol1TableName1 = "New Table1 Field1";
		final String fieldTableName2 = "New Table Field2";
		final String fieldCol1TableName2 = "New Table2 Field1";
		final String fieldCol2TableName2 = "New Table2 Field2";
		final String fieldCol3TableName2 = "New Table2 Field3";
		final String fieldCol4TableName2 = "New Table2 Field4";
		final String fieldCol5TableName2 = "New Table2 Field5";
		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName)
				.createTableField(fieldTableName1)
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName1)
						.setLabel(fieldCol1TableName1)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newTableField()
						.setLabel(fieldTableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol1TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol2TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol3TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol4TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol5TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.assertThat()
						.assertFieldOrder(configTaskTrackerRowsIds)
						.assertFieldIdBetween(fieldTableName1, 1000000, 1000000)
						.assertFieldIdBetween(fieldCol1TableName1, 1000001, 1000099)
						.assertFieldIdBetween(fieldTableName2, 2000000, 2000000)
						.assertFieldIdBetween(fieldCol1TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol2TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol3TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol4TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol5TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol5TableName2, 2000005, 2000005));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13345517")
	@Test(description = "Verify fields id in Mixed Field tracker without offset")
	public void verifyFieldIdInMixedFieldTrackerWithOutOffset() {
		// GIVEN
		final String trackerName = "Check Mixed fields tracker";
		final String fieldChoiceName1 = "New Choice Field1";
		final String fieldChoiceName2 = "New Choice Field2";
		final String fieldChoiceName3 = "New Choice Field3";
		final String fieldCustomFieldName1 = "New Custom Field 1";
		final String fieldCustomFieldName2 = "New Custom Field 2";
		final String fieldCustomFieldName3 = "New Custom Field 3";
		final String fieldTableName1 = "New Table Field1";
		final String fieldCol1TableName1 = "New Table1 Field1";
		final String fieldTableName2 = "New Table Field2";
		final String fieldCol1TableName2 = "New Table2 Field1";
		final String fieldCol2TableName2 = "New Table2 Field2";
		final String fieldTableName3 = "New Table Field3";
		final String fieldCol1TableName3 = "New Table3 Field1";
		final String fieldCol2TableName3 = "New Table3 Field2";
		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName)
				.createChoiceField(fieldChoiceName1)
				.createTextField(fieldCustomFieldName1)
				.createTableField(fieldTableName1)
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newCustomField()
						.setLabel(fieldCustomFieldName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newCustomField()
						.setLabel(fieldCustomFieldName3)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newChoiceField()
						.setLabel(fieldChoiceName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newChoiceField()
						.setLabel(fieldChoiceName3)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName1)
						.setLabel(fieldCol1TableName1)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newTableField()
						.setLabel(fieldTableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol1TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName2)
						.setLabel(fieldCol2TableName2)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newTableField()
						.setLabel(fieldTableName3)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName3)
						.setLabel(fieldCol1TableName3)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.addColumnToTableField(fieldTableName3)
						.setLabel(fieldCol2TableName3)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.assertThat()
						.assertFieldOrder(configTaskTrackerRowsIds)
						.assertFieldIdBetween(fieldCustomFieldName1, 10000, 19999)
						.assertFieldIdBetween(fieldCustomFieldName2, 10000, 19999)
						.assertFieldIdBetween(fieldCustomFieldName3, 10000, 19999)
						.assertFieldIdBetween(fieldChoiceName1, 1000, 9999)
						.assertFieldIdBetween(fieldChoiceName2, 1000, 9999)
						.assertFieldIdBetween(fieldChoiceName3, 1000, 9999)
						.assertFieldIdBetween(fieldTableName1, 1000000, 1000000)
						.assertFieldIdBetween(fieldCol1TableName1, 1000001, 1000099)
						.assertFieldIdBetween(fieldTableName2, 2000000, 2000000)
						.assertFieldIdBetween(fieldCol1TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldCol2TableName2, 2000001, 2000099)
						.assertFieldIdBetween(fieldTableName3, 3000000, 3000000)
						.assertFieldIdBetween(fieldCol1TableName3, 3000001, 3000099)
						.assertFieldIdBetween(fieldCol2TableName3, 3000001, 3000099)
				);

	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13264823")
	@Test(description = "User is able to set mandatory if in field configuration")
	public void setFieldMandatoryIfInTrackerConfiguration() {
		// GIVEN
		final String fieldName = "Reported bug(s)";
		final String mandatoryIfFormula = "resolutions[0].name == 'Failed' && status.name == 'Closed'";
		Tracker tracker = projectApiService.findTrackerByName(project, "Test Runs");

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.setMandatoryIf(mandatoryIfFormula)
						.save())
				.trackerConfigFieldsTab((c -> c.save()))
				.trackerConfigFieldsTab(c -> c.editFieldConfig(fieldName)
						.assertThat()
						.assertMandatoryIf(mandatoryIfFormula)
						.close());
	}
}