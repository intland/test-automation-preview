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
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsProjectAssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsProjectAssignColumnFormComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "MsProjectAssignColumnPage")
public class MsProjectAssignColumnPageTest extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to configure MSProject specific attributes")
	public void testMsProjectImport() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form
						.addFile(getFilePath("ms-project.xml")))
				.next()
				.redirectedToMsProjectImport()
				.assignColumnFormComponent(MsProjectAssignColumnFormComponent::selectImportHierarchies)
				.assertAssignColumnFormComponent(MsProjectAssignColumnFormAssertions::isImportHierarchiesNotSelected);
	}

}