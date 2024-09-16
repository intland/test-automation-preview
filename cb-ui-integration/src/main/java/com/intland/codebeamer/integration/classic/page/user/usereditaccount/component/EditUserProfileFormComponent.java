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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditUserProfileFormComponent
		extends AbstractCodebeamerComponent<EditUserProfileFormComponent, EditUserProfileFormAssertion> {

	public EditUserProfileFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "form#userForm");
	}

	public EditProfileFormBuilder fillOut() {
		return new EditProfileFormBuilder(this);
	}

	public CodebeamerLocator getUserNameInputField() {
		return this.locator("input#user\\.name");
	}

	public CodebeamerLocator getTitleInputField() {
		return this.locator("input#user\\.title");
	}

	public CodebeamerLocator getFirstNameInputField() {
		return this.locator("input#user\\.firstName");
	}

	public CodebeamerLocator getLastNameInputField() {
		return this.locator("input#user\\.lastName");
	}

	public CodebeamerLocator getEmailInputField() {
		return this.locator("input#user\\.email");
	}

	public CodebeamerLocator getPhoneInputField() {
		return this.locator("input#user\\.phone");
	}

	public CodebeamerLocator getMobileInputField() {
		return this.locator("input#user\\.mobile");
	}

	public CodebeamerLocator getCompanyInputField() {
		return this.locator("input#user\\.company");
	}

	public CodebeamerLocator getAddressInputField() {
		return this.locator("input#user\\.address");
	}

	public CodebeamerLocator getCityInputField() {
		return this.locator("input#user\\.city");
	}

	public CodebeamerLocator getZipCodeInputField() {
		return this.locator("input#user\\.zip");
	}

	public CodebeamerLocator getStateInputField() {
		return this.locator("input#user\\.state");
	}

	public CodebeamerLocator getCountrySelectorField() {
		return this.locator("select#user\\.country");
	}

	public CodebeamerLocator getLanguageSelectorField() {
		return this.locator("select#user\\.language");
	}

	public CodebeamerLocator getSkillsInputField() {
		return this.locator("textarea#user\\.skills");
	}

	public CodebeamerLocator getTimezoneSelectorField() {
		return this.locator("select#user\\.timeZonePattern");
	}

	public CodebeamerLocator getDateFormatSelectorField() {
		return this.locator("select#user\\.dateFormatPattern");
	}

	public CodebeamerLocator getUserNameErrorElement() {
		return this.locator("span#user\\.name\\.errors");
	}

	public CodebeamerLocator getFirstNameErrorElement() {
		return this.locator("span#user\\.firstName\\.errors");
	}

	public CodebeamerLocator getLastNameErrorElement() {
		return this.locator("span#user\\.lastName\\.errors");
	}

	public CodebeamerLocator getEmailErrorElement() {
		return this.locator("span#user\\.email\\.errors");
	}

	public CodebeamerLocator getPhoneErrorElement() {
		return this.locator("span#user\\.phone\\.errors");
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input[type='submit']:not([name])");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input[type='submit'][name='_cancel']");
	}

	public CodebeamerLocator getChangePasswordButton() {
		return this.locator("input[type='submit'][name='_changePassword']");
	}

	@Override
	public EditUserProfileFormAssertion assertThat() {
		return new EditUserProfileFormAssertion(this);
	}
}
