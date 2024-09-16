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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.AssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.AssignColumnFormComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.FieldSeparator;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.NumberFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "MsExcelAssignColumnPage")
public class MsExcelAssignColumnPageTest extends AbstractIntegrationClassicNGTests {

	private User user;

	private Project project;

	private Tracker tracker;

	@Override
	protected void generateDataBeforeClass() {
		user = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
		project = getDataManagerService().getProjectApiService(user).createProjectFromTemplate();
		tracker = getDataManagerService().getTrackerApiService(user).createDefaultBugTracker(project, "My Bug");
	}

	@Override
	protected void cleanUpDataAfterClass() {
		getDataManagerService().getProjectApiService(user).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(user);
	}

	@Test(description = "User is able to configure common assign column attributes")
	public void testAssignColumnPageCommonAttributes() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.addFile(getFilePath("sample.csv"))
						.selectFieldSeparator(FieldSeparator.COMMA))
				.next()
				.redirectedToMsExcelCsvImport()

				.assignColumnFormComponent(form -> form.selectDateFormat(DateFormat.FORMAT3))
				.assertAssignColumnFormComponent(assertion -> assertion.isDateFormat(DateFormat.FORMAT3))

				.assignColumnFormComponent(form -> form.selectTimeZone(TimeZone.AMERICA_PUERTO_RICO))
				.assertAssignColumnFormComponent(assertion -> assertion.isTimeZone(TimeZone.AMERICA_PUERTO_RICO))

				.assignColumnFormComponent(form -> form.selectNumberFormat(NumberFormat.DE))
				.assertAssignColumnFormComponent(assertion -> assertion.isNumberFormat(NumberFormat.DE))

				.assignColumnFormComponent(form -> form.selectDescriptionFormat(WikiTextType.HTML))
				.assertAssignColumnFormComponent(assertion -> assertion.isDescriptionFormat(WikiTextType.HTML))

				.assignColumnFormComponent(form -> form.fillSkipDataOrCell("skip - data"))
				.assertAssignColumnFormComponent(assertion -> assertion.isSkipDataOrCell("skip - data"))

				.assignColumnFormComponent(form -> form.selectFindAndUpdateExistingItems("Category"))
				.assertAssignColumnFormComponent(
						assertion -> assertion.findAndUpdateExistingItemsBy(c -> c.isSelected("ID", "Category")))

				.assignColumnFormComponent(AssignColumnFormComponent::selectDataOrCellTypeMapping)
				.assertAssignColumnFormComponent(AssignColumnFormAssertions::isDataOrCellTypeMappingNotSelected)

				.assignColumnFormComponent(AssignColumnFormComponent::selectMultipleFieldMappings)
				.assertAssignColumnFormComponent(AssignColumnFormAssertions::isMultipleFieldMappingsSelected);
	}

	@Test(description = "User is able to configure MsExcel specific assign column attributes")
	public void testMsExcelAssignColumnPage() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.addFile(getFilePath("ms-excel.xlsx")))
				.next()
				.redirectedToMsExcelImport()
				.assignColumnFormComponent(form -> form.fillStartImportAtRow(2))
				.assertAssignColumnFormComponent(assertions -> assertions.isStartImportAtRow(2))

				.assignColumnFormComponent(form -> form.selectDataHierarchyBasedOnColumn("2"))
				.assertAssignColumnFormComponent(assertions -> assertions.isDataHierarchyBasedOnColumn("2"));
	}

}