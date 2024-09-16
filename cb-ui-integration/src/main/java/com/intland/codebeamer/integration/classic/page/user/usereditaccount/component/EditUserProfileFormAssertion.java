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

package com.intland.codebeamer.integration.classic.page.user.usereditaccount.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditUserProfileFormAssertion
		extends AbstractCodebeamerComponentAssert<EditUserProfileFormComponent, EditUserProfileFormAssertion> {

	public EditUserProfileFormAssertion(EditUserProfileFormComponent component) {
		super(component);
	}

	public EditUserProfileFormAssertion hasUserNameError() {
		return assertAll("Username error should be visible on the form",
				() -> assertThat(getComponent().getUserNameErrorElement()).isVisible());
	}

	public EditUserProfileFormAssertion hasFirstNameError() {
		return assertAll("FirstName error should be visible on the form",
				() -> assertThat(getComponent().getFirstNameErrorElement()).isVisible());
	}

	public EditUserProfileFormAssertion hasLastNameError() {
		return assertAll("LastName error should be visible on the form",
				() -> assertThat(getComponent().getLastNameErrorElement()).isVisible());
	}

	public EditUserProfileFormAssertion hasEmailError() {
		return assertAll("Email error should be visible on the form",
				() -> assertThat(getComponent().getEmailErrorElement()).isVisible());
	}

	public EditUserProfileFormAssertion hasPhoneError() {
		return assertAll("Phone error should be visible on the form",
				() -> assertThat(getComponent().getPhoneErrorElement()).isVisible());
	}

	public EditUserProfileFormAssertion isFormEditable() {
		return assertAll("User edit account form is editable", () -> {
			assertThat(getComponent().getUserNameInputField()).isEditable();
			assertThat(getComponent().getTitleInputField()).isEditable();
			assertThat(getComponent().getFirstNameInputField()).isEditable();
			assertThat(getComponent().getLastNameInputField()).isEditable();
			assertThat(getComponent().getEmailInputField()).isEditable();
			assertThat(getComponent().getPhoneInputField()).isEditable();
			assertThat(getComponent().getMobileInputField()).isEditable();
			assertThat(getComponent().getCompanyInputField()).isEditable();
			assertThat(getComponent().getAddressInputField()).isEditable();
			assertThat(getComponent().getCityInputField()).isEditable();
			assertThat(getComponent().getZipCodeInputField()).isEditable();
			assertThat(getComponent().getStateInputField()).isEditable();
			assertThat(getComponent().getCountrySelectorField()).isEditable();
			assertThat(getComponent().getLanguageSelectorField()).isEditable();
			assertThat(getComponent().getSkillsInputField()).isEditable();
			assertThat(getComponent().getTimezoneSelectorField()).isEditable();
			assertThat(getComponent().getDateFormatSelectorField()).isEditable();
			assertThat(getComponent().getSaveButton()).isVisible();
			assertThat(getComponent().getCancelButton()).isVisible();
			assertThat(getComponent().getChangePasswordButton()).isVisible();
		});
	}
}

