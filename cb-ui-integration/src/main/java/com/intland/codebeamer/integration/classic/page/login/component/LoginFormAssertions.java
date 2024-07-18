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

package com.intland.codebeamer.integration.classic.page.login.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class LoginFormAssertions extends AbstractCodebeamerComponentAssert<LoginFormComponent, LoginFormAssertions> {

	LoginFormAssertions(LoginFormComponent component) {
		super(component);
	}

	public LoginFormAssertions loginUnsuccessfull() {
		return assertAll("Login should have been unsuccessfull", () -> assertThat(getComponent().getErrorMessageElement()).isVisible());
	}

	public LoginFormAssertions passwordCleared() {
		return assertAll("Password field should have been cleared", () -> assertThat(getComponent().getPasswordField()).isEmpty());
	}

	public LoginFormAssertions isReady() {
		return assertAll("Login form is ready", () -> {
			assertThat(getComponent().getUsernameField()).isEditable();
			assertThat(getComponent().getPasswordField()).isEditable();
			assertThat(getComponent().getForgotPasswordLink()).isVisible();
			assertThat(getComponent().getUserRegistrationLink()).isVisible(); // TODO it could be configurable
			assertThat(getComponent().getLoginButton()).isVisible();
		});
	}
	
}
