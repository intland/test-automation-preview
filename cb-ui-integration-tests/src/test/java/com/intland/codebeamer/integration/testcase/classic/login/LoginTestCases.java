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

package com.intland.codebeamer.integration.testcase.classic.login;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.login.component.LoginFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "LoginTestCases")
public class LoginTestCases extends AbstractIntegrationClassicNGTests {

	private static final String DEFAULT_PASSWORD = "007";

	private User activeUser;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {	
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToClassicUI(activeUser);
	}
	
	@Override
	protected void cleanUpDataAfterClass() throws Exception {

	}

	@Test(description = "User is able to visit login page")
	public void loginPageCanBeLoaded() {
		getClassicCodebeamerApplication()
				.visitLoginPage();
	}

	@Test(description = "User is able to use login form")
	public void loginFormIsReady() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.assertLoginFormComponent(LoginFormAssertions::isReady);
	}
	
	@Test(description = "User is not able to authenticate when user credentials are invalid")
	public void userIsNotAbleToAuthenticateWhenUserCredentialsAreInvalid() {
		getClassicCodebeamerApplication()
			.visitLoginPage()
			.getLoginFormComponent().login("non_existing_username", "non_existing_password")
			.redirectedToLoginPage()
			.assertLoginFormComponent(assertions -> assertions
					.loginUnsuccessfull()
					.passwordCleared());
	}
	
	@Test(description = "User is able to authenticate when user credentials are valid")
	public void userIsAbleToAuthenticate() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent().login(activeUser.getName(), DEFAULT_PASSWORD)
				.redirectedToUserMyWikiPage();
	}
		
}
