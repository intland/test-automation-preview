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

package com.intland.codebeamer.integration.classic.page.projectadmin;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.component.AddRoleFormAssertion;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "AddRolePageTestCases")
public class AddRolePageTestCases extends AbstractIntegrationClassicNGTests {

	private User user;

	private Project project;

	private ProjectApiService projectApiService;

	private UserApiService userApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		user = userApiService.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(user);
		project = projectApiService.createProjectFromTemplate();

		switchToClassicUI(user);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (project != null) {
			projectApiService.deleteProject(project);
		}
		if (user != null) {
			userApiService.disableUser(user);
		}
	}

	@Test(description = "Add role form is ready")
	public void testAddRoleFormIsReady() {
		// When / Then
		getClassicCodebeamerApplication(user)
				.visitAddRolePage(project)
				.assertAddRoleFormComponent(AddRoleFormAssertion::isReady);
	}
}
