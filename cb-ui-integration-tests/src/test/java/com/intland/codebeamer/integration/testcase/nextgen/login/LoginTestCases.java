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

package com.intland.codebeamer.integration.testcase.nextgen.login;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationNextgenNGTests;

@Test(groups = "LoginTestCasesNextgen")
public class LoginTestCases extends AbstractIntegrationNextgenNGTests {
	
	private static final String DEFAULT_PASSWORD = "007";

	private User activeUser;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {	
		activeUser = getDataManagerService().getUserApiService().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToNextgenUI(activeUser);
	}
	
	@Test(description = "User is not able to authenticate when user credentials are invalid")
	public void userIsNotAbleToAuthenticateWhenUserCredentialsAreInvalid() {
		getNextgenCodebeamerApplication()
			.visitLoginPage()
			.getLoginFormComponent().login("non_existing_username", "non_existing_password")
			.redirectedToLoginPage()
			.assertLoginFormComponent(assertions -> assertions
					.usernameNotCleared()
					.passwordNotCleared())
			.assertToastComponent(assertions -> assertions
					.hasError());
	}
	
	@Test(description = "User is able to authenticate to nextgen UI when user credentials are valid")
	public void userIsAbleToAuthenticate() {
		getNextgenCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent().login(activeUser.getName(), DEFAULT_PASSWORD)
				.redirectedToProjectBrowserPage();
	}
	
}
