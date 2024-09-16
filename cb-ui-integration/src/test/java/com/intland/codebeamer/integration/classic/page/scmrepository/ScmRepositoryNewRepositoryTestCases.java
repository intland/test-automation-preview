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

package com.intland.codebeamer.integration.classic.page.scmrepository;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
@Test(groups = "ScmRepositoryNewRepositoryTestCases")
public class ScmRepositoryNewRepositoryTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	Project project;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		project = getProjectApiService().createProjectFromTemplate();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getProjectApiService().deleteProject(project);
	}

	@Test(description = "User is able to create a new repository")
	public void userIsAbleToStartCreatingANewRepository() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitScmRepositoryMainPage(project)
				.actionBarComponent(c -> c.assertThat().isNewRepositoryButtonVisible())
				.contentComponent(c -> c.assertThat().isWarningMessageVisible())
				.getActionBarComponent()
				.newRepository()
				.redirectedToRepositoryCreatePage(project)
				.actionBarComponent(c -> c.assertThat().isSubmitScmTypeButtonVisible())
				.getActionBarComponent()
				.configure()
				.redirectedToRepositoryCreatePage(project)
				.actionBarComponent(c -> c.assertThat()
						.isNewOrExistingButtonVisible()
						.isBackButtonVisible()
						.isCancelButtonVisible())
				.getActionBarComponent()
				.next()
				.redirectedToRepositoryCreatePage(project)
				.actionBarComponent(c -> c.assertThat()
						.isFinishButtonVisible()
						.isBackButtonVisible()
						.isCancelButtonVisible())
				.contentComponent(c -> c.assertThat().isRepositoryNameTextFieldVisible())
				.getActionBarComponent()
				.finish()
				.redirectedToRepositoryItemPage();
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(activeUser);
	}

}