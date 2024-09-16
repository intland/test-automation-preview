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

package com.intland.codebeamer.integration.classic.page.projectbrowser;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectBrowserNewProjectTestCases")
public class ProjectBrowserNewProjectTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToClassicUI(activeUser);
	}

	@Test(description = "User is able to start creating a new project")
	public void userIsAbleToStartCreatingANewProject() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitProjectBrowserPage()
				.actionBarComponent(c -> c.assertThat().isNewProjectButtonVisible())
				.getActionBarComponent()
				.createNewProject()
				.redirectedToProjectCreatePage();
	}

}
