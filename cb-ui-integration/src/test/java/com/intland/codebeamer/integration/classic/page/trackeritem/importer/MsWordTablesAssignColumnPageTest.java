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
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "MsWordTablesAssignColumnPage")
public class MsWordTablesAssignColumnPageTest extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to select Ms Word Tables specific attributes")
	public void testMsWordTablesImport() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.selectMsWordTables()
						.addFile(getFilePath("ms-word.docx")))
				.next()
				.redirectedToMsWordTablesImport()
				.assignColumnFormComponent(form -> form.fillStartImportAtRow(1))
				.assertAssignColumnFormComponent(assertions -> assertions.isStartImportAtRow(1))

				.assignColumnFormComponent(form -> form.selectDataHierarchyBasedOnColumn("0"))
				.assertAssignColumnFormComponent(assertions -> assertions.isDataHierarchyBasedOnColumn("0"));
	}
}