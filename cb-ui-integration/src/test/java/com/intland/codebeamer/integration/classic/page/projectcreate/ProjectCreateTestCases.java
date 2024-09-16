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

package com.intland.codebeamer.integration.classic.page.projectcreate;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectCreateTestCases")
public class ProjectCreateTestCases extends AbstractIntegrationClassicNGTests {
	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Test(description = "User is able to create default project")
	public void userIsAbleToCreateANewProject() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitProjectCreatePage()
				.projectCreateSelectKindOfProjectFormComponent(c -> c.assertThat().isStepContentVisible())
				.projectCreateSelectKindOfProjectFormComponent(
						c -> c.assertThat().isNewProjectSelectorVisible().isNewProjectSelectorSelected()
								.isNewProjectTemplateSelectorVisible().isNewProjectTemplateDefaultSelected())
				.actionBarComponent(c -> c.assertThat().isNextStepButtonVisible())
				.getActionBarComponent()
				.next()
				.redirectedToProjectCreateGeneralSettingsStep()
				.actionBarComponent(c -> c.assertThat().isFinishButtonVisible())
				.getActionBarComponent()
				.finish()
				.redirectedToProjectLandingPage();
	}

}
