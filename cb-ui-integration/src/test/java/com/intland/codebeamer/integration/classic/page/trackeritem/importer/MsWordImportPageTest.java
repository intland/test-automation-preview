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

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordImportCondition;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordImportRule;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.ImportRuleConditionType;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "MsWordImportPage")
public class MsWordImportPageTest extends AbstractIntegrationClassicNGTests {

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

	@Test(description = "User is able to import Ms Word documents")
	public void testMsWordImport() {
		getClassicCodebeamerApplication(user)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(form -> form.addFile(getFilePath("ms-word.docx")))
				.next()
				.redirectedToMsWordImport()
				.centerPaneComponent(comp -> comp
						.toggleImportSwitch(2))
				.assertCenterPaneComponent(assertions -> assertions
						.areItemsIgnored(2)
						.areItemsSelected(0, 1, 3))
				.rightPaneFormComponent(form -> form
						.addNewRule(new MsWordImportRule(0, List.of("Export of User Stories"), false,
								List.of(new MsWordImportCondition(ImportRuleConditionType.NO_CONDITION),
										new MsWordImportCondition(ImportRuleConditionType.WHOLE_WORD_CIS, "test"),
										new MsWordImportCondition(ImportRuleConditionType.HAS_STYLE, List.of("Heading 1 Char")))))
						.preview()
						.confirmDialogYes())
				.assertRightPaneFormComponent(assertions -> assertions
						.hasStatistic(0, 6)
						.hasStatistic(1, 1))
				.save();
	}
}