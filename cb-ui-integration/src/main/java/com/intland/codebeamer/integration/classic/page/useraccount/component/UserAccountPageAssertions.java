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

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class UserAccountPageAssertions
		extends AbstractCodebeamerComponentAssert<UserAccountFormComponent, UserAccountPageAssertions> {

	protected UserAccountPageAssertions(UserAccountFormComponent component) {
		super(component);
	}

	public UserAccountPageAssertions isReady() {
		return assertAll("All fields should visible on user account page", () -> {
			assertThat(getComponent().getUsernameFieldLabel()).isVisible();
			assertThat(getComponent().getUsernameField()).isVisible();
			assertThat(getComponent().getLastLoginFieldLabel()).isVisible();
			assertThat(getComponent().getLastLoginField()).isVisible();
			assertThat(getComponent().getTitleFieldLabel()).isVisible();
			assertThat(getComponent().getTitleField()).isVisible();
			assertThat(getComponent().getUserAccountIdFieldLabel()).isVisible();
			assertThat(getComponent().getUserAccountIdField()).isVisible();
			assertThat(getComponent().getFirstNameFieldLabel()).isVisible();
			assertThat(getComponent().getFirstNameField()).isVisible();
			assertThat(getComponent().getLastNameFieldLabel()).isVisible();
			assertThat(getComponent().getLastNameField()).isVisible();
			assertThat(getComponent().getEmailFieldLabel()).isVisible();
			assertThat(getComponent().getEmailField()).isVisible();
			assertThat(getComponent().getPhoneFieldLabel()).isVisible();
			assertThat(getComponent().getPhoneField()).isVisible();
			assertThat(getComponent().getMobileFieldLabel()).isVisible();
			assertThat(getComponent().getMobileField()).isVisible();
			assertThat(getComponent().getCompanyFieldLabel()).isVisible();
			assertThat(getComponent().getCompanyField()).isVisible();
			assertThat(getComponent().getAddressFieldLabel()).isVisible();
			assertThat(getComponent().getAddressField()).isVisible();
			assertThat(getComponent().getCityFieldLabel()).isVisible();
			assertThat(getComponent().getCityField()).isVisible();
			assertThat(getComponent().getZipCodeFieldLabel()).isVisible();
			assertThat(getComponent().getZipCodeField()).isVisible();
			assertThat(getComponent().getStateFieldLabel()).isVisible();
			assertThat(getComponent().getStateField()).isVisible();
			assertThat(getComponent().getCountryFieldLabel()).isVisible();
			assertThat(getComponent().getCountryField()).isVisible();
			assertThat(getComponent().getLanguageFieldLabel()).isVisible();
			assertThat(getComponent().getLanguageField()).isVisible();
			assertThat(getComponent().getRegisteredDateTimeFieldLabel()).isVisible();
			assertThat(getComponent().getRegisteredDateTimeField()).isVisible();
			assertThat(getComponent().getSkillsFieldLabel()).isVisible();
		});
	}

	public UserAccountPageAssertions hasUserName(String userName) {
		return assertAll("Username should be %s".formatted(userName),
				() -> assertThat(getComponent().getUsernameField()).hasText(userName));
	}

	public UserAccountPageAssertions hasTitle(String title) {
		return assertAll("title should be %s".formatted(title),
				() -> assertThat(getComponent().getTitleField()).hasText(title));
	}

	public UserAccountPageAssertions hasAccountId(Integer accountId) {
		return assertAll("Account id should be %s".formatted(accountId),
				() -> assertThat(getComponent().getUserAccountIdField()).hasText(String.valueOf(accountId)));
	}

	public UserAccountPageAssertions hasFirstName(String firstName) {
		return assertAll("User first name should be %s".formatted(firstName),
				() -> assertThat(getComponent().getFirstNameField()).hasText(firstName));
	}

	public UserAccountPageAssertions hasLastName(String lastName) {
		return assertAll("User last name should be %s".formatted(lastName),
				() -> assertThat(getComponent().getLastNameField()).hasText(lastName));
	}

	public UserAccountPageAssertions hasEmail(String email) {
		return assertAll("User email should be %s".formatted(email),
				() -> assertThat(getComponent().getEmailField()).hasText(email));
	}

	public UserAccountPageAssertions hasPhone(String phone) {
		return assertAll("User phone should be %s".formatted(phone),
				() -> assertThat(getComponent().getPhoneField()).hasText(phone));
	}

	public UserAccountPageAssertions hasMobile(String mobile) {
		return assertAll("User mobile should be %s".formatted(mobile),
				() -> assertThat(getComponent().getMobileField()).hasText(mobile));
	}

	public UserAccountPageAssertions hasCompany(String company) {
		return assertAll("User company should be %s".formatted(company),
				() -> assertThat(getComponent().getCompanyField()).hasText(company));
	}

	public UserAccountPageAssertions hasAddress(String address) {
		return assertAll("User company should be %s".formatted(address),
				() -> assertThat(getComponent().getAddressField()).hasText(address));
	}

	public UserAccountPageAssertions hasCity(String city) {
		return assertAll("User city should be %s".formatted(city),
				() -> assertThat(getComponent().getCityField()).hasText(city));
	}

	public UserAccountPageAssertions hasZipCode(String zipCode) {
		return assertAll("User zipCode should be %s".formatted(zipCode),
				() -> assertThat(getComponent().getZipCodeField()).hasText(zipCode));
	}

	public UserAccountPageAssertions hasState(String state) {
		return assertAll("User state should be %s".formatted(state),
				() -> assertThat(getComponent().getStateField()).hasText(state));
	}

	public UserAccountPageAssertions hasCountry(String country) {
		return assertAll("User country should be %s".formatted(country),
				() -> assertThat(getComponent().getCountryField()).hasText(Country.valueOf(country).getName()));
	}

	public UserAccountPageAssertions hasLanguage(String language) {
		return assertAll("User language should be %s".formatted(language),
				() -> assertThat(getComponent().getLanguageField()).hasText(Language.valueOf(language.toUpperCase()).getName()));
	}

	public UserAccountPageAssertions hasStatus(String status) {
		return assertAll("User status should be %s".formatted(status),
				() -> assertThat(getComponent().getStateField()).hasText(status));
	}

	public UserAccountPageAssertions hasSkills(String skills) {
		return assertAll("User skills should be %s".formatted(skills),
				() -> assertThat(getComponent().getSkillsField()).hasText(skills));
	}
}
