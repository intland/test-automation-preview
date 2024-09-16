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

package com.intland.codebeamer.integration.classic.page.useraccount.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class UserAccountFormComponent extends AbstractCodebeamerComponent<UserAccountFormComponent, UserAccountPageAssertions> {

	public UserAccountFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#userDataTable");
	}

	public CodebeamerLocator getUsernameFieldLabel() {
		return getFieldLabel("userName", "userNameLabel");
	}

	public CodebeamerLocator getUsernameField() {
		return getField("userName", "userNameValue");
	}

	public CodebeamerLocator getLastLoginFieldLabel() {
		return getFieldLabel("lastLogin", "lastLoginLabel");
	}

	public CodebeamerLocator getLastLoginField() {
		return getField("lastLogin", "lastLoginValue");
	}

	public CodebeamerLocator getTitleFieldLabel() {
		return getFieldLabel("title", "titleLabel");
	}

	public CodebeamerLocator getTitleField() {
		return getField("title", "titleValue");
	}

	public CodebeamerLocator getUserAccountIdFieldLabel() {
		return getFieldLabel("accountId", "accountIdLabel");
	}

	public CodebeamerLocator getUserAccountIdField() {
		return getField("accountId", "accountIdValue");
	}

	public CodebeamerLocator getFirstNameFieldLabel() {
		return getFieldLabel("firstName", "firstNameLabel");
	}

	public CodebeamerLocator getFirstNameField() {
		return getField("firstName", "firstNameValue");
	}

	public CodebeamerLocator getLastNameFieldLabel() {
		return getFieldLabel("lastName", "lastNameLabel");
	}

	public CodebeamerLocator getLastNameField() {
		return getField("lastName", "lastNameValue");
	}

	public CodebeamerLocator getEmailFieldLabel() {
		return getFieldLabel("email", "emailLabel");
	}

	public CodebeamerLocator getEmailField() {
		return getField("email", "emailValue");
	}

	public CodebeamerLocator getPhoneFieldLabel() {
		return getFieldLabel("phone", "phoneLabel");
	}

	public CodebeamerLocator getPhoneField() {
		return getField("phone", "phoneValue");
	}

	public CodebeamerLocator getMobileFieldLabel() {
		return getFieldLabel("mobile", "mobileLabel");
	}

	public CodebeamerLocator getMobileField() {
		return getField("mobile", "mobileValue");
	}

	public CodebeamerLocator getCompanyFieldLabel() {
		return getFieldLabel("company", "companyLabel");
	}

	public CodebeamerLocator getCompanyField() {
		return getField("company", "companyValue");
	}

	public CodebeamerLocator getAddressFieldLabel() {
		return getFieldLabel("address", "addressLabel");
	}

	public CodebeamerLocator getAddressField() {
		return getField("address", "addressValue");
	}

	public CodebeamerLocator getCityFieldLabel() {
		return getFieldLabel("city", "cityLabel");
	}

	public CodebeamerLocator getCityField() {
		return getField("city", "cityValue");
	}

	public CodebeamerLocator getZipCodeFieldLabel() {
		return getFieldLabel("zipCode", "zipCodeLabel");
	}

	public CodebeamerLocator getZipCodeField() {
		return getField("zipCode", "zipCodeValue");
	}

	public CodebeamerLocator getStateFieldLabel() {
		return getFieldLabel("state", "stateLabel");
	}

	public CodebeamerLocator getStateField() {
		return getField("state", "stateValue");
	}

	public CodebeamerLocator getCountryFieldLabel() {
		return getFieldLabel("country", "countryLabel");
	}

	public CodebeamerLocator getCountryField() {
		return getField("country", "countryValue");
	}

	public CodebeamerLocator getLanguageFieldLabel() {
		return getFieldLabel("language", "languageLabel");
	}

	public CodebeamerLocator getLanguageField() {
		return getField("language", "languageValue");
	}

	public CodebeamerLocator getRegisteredDateTimeFieldLabel() {
		return getFieldLabel("registryDate", "registryDateLabel");
	}

	public CodebeamerLocator getRegisteredDateTimeField() {
		return getField("registryDate", "registryDateValue");
	}

	public CodebeamerLocator getSkillsFieldLabel() {
		return getFieldLabel("skills", "skillsLabel");
	}

	public CodebeamerLocator getSkillsField() {
		return getField("skills", "skillsValue");
	}

	public CodebeamerLocator getFieldLabel(String testId, String label) {
		return this.locator("tr[data-testid='%s'] > td:nth-of-type(1)".formatted(testId, label));
	}

	public CodebeamerLocator getField(String testId, String value) {
		return this.locator("tr[data-testid='%s'] > td:nth-of-type(2)".formatted(testId, value));
	}

	@Override
	public UserAccountPageAssertions assertThat() {
		return new UserAccountPageAssertions(this);
	}
}
