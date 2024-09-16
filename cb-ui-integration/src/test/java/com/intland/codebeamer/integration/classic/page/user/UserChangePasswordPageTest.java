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

package com.intland.codebeamer.integration.classic.page.user;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.classic.page.user.component.PasswordChangeFormAssertions;

@Test(groups = "UserChangePasswordPage")
public class UserChangePasswordPageTest extends AbstractIntegrationClassicNGTests {

	private static final String DEFAULT_PASSWORD = "007";

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "User is able to visit password change page")
	public void passwordChangePageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser.getName(), DEFAULT_PASSWORD)
				.visitChangePasswordPage();
	}

	@Test(description = "User is able to use password change form")
	public void passwordChangeFormIsReady() {
		getClassicCodebeamerApplication(activeUser.getName(), DEFAULT_PASSWORD)
				.visitChangePasswordPage()
				.assertPasswordChangeFormComponent(PasswordChangeFormAssertions::isReady);
	}
}
