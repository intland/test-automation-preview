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

package com.intland.codebeamer.integration.classic.page.userregistration.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class UserRegistrationFormAssertions extends AbstractCodebeamerComponentAssert<UserRegistrationFormComponent, UserRegistrationFormAssertions> {

	UserRegistrationFormAssertions(UserRegistrationFormComponent component) {
		super(component);
	}

	public UserRegistrationFormAssertions hasUsernameError() {
		return assertAll("Username error should be visible on the form", () -> assertThat(getComponent().getUsernameErrorElement()).isVisible());
	}
	
	public UserRegistrationFormAssertions hasPasswordError() {
		return assertAll("Password error should be visible on the form", () -> assertThat(getComponent().getPasswordErrorElement()).isVisible());
	}
			
	public UserRegistrationFormAssertions isReady() {
		return assertAll("User registration form is ready", () -> {
			assertThat(getComponent().getUsernameField()).isEditable();
			assertThat(getComponent().getPasswordField()).isVisible();
			assertThat(getComponent().getConfirmPasswordField()).isVisible();
			assertThat(getComponent().getFirstNameField()).isEditable();
			assertThat(getComponent().getLastNameField()).isEditable();
			assertThat(getComponent().getEmailField()).isEditable();
			assertThat(getComponent().getCompanyField()).isEditable();
		});
	}
	
}
