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

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.TrackerItemImportFormComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.CharsetName;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.FieldSeparator;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "TrackerItemImportPage")
public class TrackerItemImportPageTest extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to upload file")
	public void testFileUpload() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.addFile(getFilePath("sample.csv")))
				.assertImportFormComponent(assertions -> assertions
						.isMsExcelCsvSelected()
						.isOnlyOneFileFormatSelected());
	}

	@Test(description = "User is unable to upload an empty file")
	public void testEmptyFileCantBeUploaded() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.addFile(getFilePath("empty.csv")))
				.overlayMessageBoxComponent(c -> c.assertThat().hasError());
	}

	@Test(description = "User is able to select Microsoft Word format")
	public void testSelectingMsWord() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.selectMsWord()
						.selectMsWordTables())
				.assertImportFormComponent(assertions -> assertions
						.isMsWordSelected()
						.isMsWordTablesSelected()
						.isOnlyOneFileFormatSelected());
	}

	@Test(description = "User is able to select Microsoft Excel format")
	public void testSelectingMsExcel() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(TrackerItemImportFormComponent::selectMsExcel)
				.assertImportFormComponent(assertions -> assertions
						.isMsExcelSelected()
						.isOnlyOneFileFormatSelected());
	}

	@Test(description = "User is able to select Microsoft Project format")
	public void testSelectingMsProject() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(TrackerItemImportFormComponent::selectMsProject)
				.assertImportFormComponent(assertions -> assertions
						.isMsProjectSelected()
						.isOnlyOneFileFormatSelected());
	}

	@Test(description = "User is able to select Microsoft Excel CSV format")
	public void testSelectingMsExcelCsv() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.selectMsExcelCsv()
						.selectCharset(CharsetName.ENGLISH_US_ASCII)
						.selectFieldSeparator(FieldSeparator.HASH))
				.assertImportFormComponent(assertions -> assertions
						.isMsExcelCsvSelected()
						.isOnlyOneFileFormatSelected()
						.isCharset(CharsetName.ENGLISH_US_ASCII)
						.isFieldSeparator(FieldSeparator.HASH));
	}

}

