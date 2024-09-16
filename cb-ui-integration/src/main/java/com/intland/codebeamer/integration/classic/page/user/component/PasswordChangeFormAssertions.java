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

package com.intland.codebeamer.integration.classic.page.user.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class PasswordChangeFormAssertions extends AbstractCodebeamerComponentAssert<PasswordChangeFormComponent, PasswordChangeFormAssertions> {

	protected PasswordChangeFormAssertions(PasswordChangeFormComponent component) {
		super(component);
	}

	public PasswordChangeFormAssertions newPasswordNotMatched() {
		return assertAll("Provided new password confirmation is not same as new password",
				() -> assertThat(getComponent().getErrorMessageNewPasswordElement()).isVisible());
	}

	public PasswordChangeFormAssertions wrongCurrentPassword() {
		return assertAll("Provided password is incorrect",
				() -> assertThat(getComponent().getErrorMessageOldPasswordElement()).isVisible());
	}

	public PasswordChangeFormAssertions isReady() {
		return assertAll("Login form is ready", () -> {
			assertThat(getComponent().getOldPasswordInput()).isEditable();
			assertThat(getComponent().getNewPasswordInput()).isEditable();
			assertThat(getComponent().getNewPasswordAgainInput()).isEditable();
			assertThat(getComponent().getSaveChangePasswordButton()).isVisible();
			assertThat(getComponent().getForgotPasswordButton()).isVisible();
		});
	}
}
