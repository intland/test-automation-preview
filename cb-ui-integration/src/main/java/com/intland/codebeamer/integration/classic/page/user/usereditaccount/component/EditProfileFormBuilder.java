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

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class EditProfileFormBuilder {

	@Component("Edit user profile form")
	private EditUserProfileFormComponent editUserProfileFormComponent;

	public EditProfileFormBuilder(EditUserProfileFormComponent editUserProfileFormComponent) {
		this.editUserProfileFormComponent = editUserProfileFormComponent;
	}

	public EditProfileFormBuilder userName(String userName) {
		this.editUserProfileFormComponent.getUserNameInputField().fill(userName);
		return this;
	}

	public EditProfileFormBuilder title(String title) {
		this.editUserProfileFormComponent.getTitleInputField().fill(title);
		return this;
	}

	public EditProfileFormBuilder firstName(String firstName) {
		this.editUserProfileFormComponent.getFirstNameInputField().fill(firstName);
		return this;
	}

	public EditProfileFormBuilder lastName(String lastName) {
		this.editUserProfileFormComponent.getLastNameInputField().fill(lastName);
		return this;
	}

	public EditProfileFormBuilder email(String email) {
		this.editUserProfileFormComponent.getEmailInputField().fill(email);
		return this;
	}

	public EditProfileFormBuilder phone(String phone) {
		this.editUserProfileFormComponent.getPhoneInputField().fill(phone);
		return this;
	}

	public EditProfileFormBuilder mobile(String mobile) {
		this.editUserProfileFormComponent.getMobileInputField().fill(mobile);
		return this;
	}

	public EditProfileFormBuilder company(String company) {
		this.editUserProfileFormComponent.getCompanyInputField().fill(company);
		return this;
	}

	public EditProfileFormBuilder address(String address) {
		this.editUserProfileFormComponent.getAddressInputField().fill(address);
		return this;
	}

	public EditProfileFormBuilder city(String city) {
		this.editUserProfileFormComponent.getCityInputField().fill(city);
		return this;
	}

	public EditProfileFormBuilder zip(String zip) {
		this.editUserProfileFormComponent.getZipCodeInputField().fill(zip);
		return this;
	}

	public EditProfileFormBuilder state(String state) {
		this.editUserProfileFormComponent.getStateInputField().fill(state);
		return this;
	}

	public EditProfileFormBuilder country(Country country) {
		this.editUserProfileFormComponent.getCountrySelectorField().selectOption(country.getName());
		return this;
	}

	public EditProfileFormBuilder language(Language language) {
		this.editUserProfileFormComponent.getLanguageSelectorField().selectOption(language.getName());
		return this;
	}

	public EditProfileFormBuilder skills(String skills) {
		this.editUserProfileFormComponent.getSkillsInputField().fill(skills);
		return this;
	}

	public EditProfileFormBuilder timeZone(TimeZone timeZone) {
		this.editUserProfileFormComponent.getTimezoneSelectorField().selectOption(timeZone.getValue());
		return this;
	}

	public EditProfileFormBuilder dateFormat(DateFormat dateFormat) {
		this.editUserProfileFormComponent.getDateFormatSelectorField().selectOption(dateFormat.getValue());
		return this;
	}
}
