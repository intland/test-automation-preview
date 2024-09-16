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

package com.intland.codebeamer.integration.api.service.user;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.swagger.client.internal.api.UserApi;
import com.intland.swagger.client.model.UpdateUser;
import com.intland.swagger.client.model.UpdateUserRequest;
import com.intland.swagger.client.model.User;

public class UserAttributeBuilder {

	private final UpdateUserRequest updateUserRequest;

	private UpdateUser updateUser;

	private UserApi userApi;

	private User user;

	public UserAttributeBuilder(com.intland.swagger.client.model.User user, UpdateUserRequest updateUserRequest,
			UserApi userApi) {
		this.user = user;
		this.updateUserRequest = updateUserRequest;
		this.updateUser = new UpdateUser().status(Status.ACTIVATED.getValue());
		this.updateUser = setUpdatedUser(user);
		this.userApi = userApi;
	}

	public UserAttributeBuilder name(String name) {
		this.updateUser.setName(name);
		return this;
	}

	public UserAttributeBuilder firstName(String firstName) {
		this.updateUser.setFirstName(firstName);
		return this;
	}

	public UserAttributeBuilder lastName(String lastName) {
		this.updateUser.setLastName(lastName);
		return this;
	}

	public UserAttributeBuilder email(String email) {
		this.updateUser.setEmail(email);
		return this;
	}

	public UserAttributeBuilder status(Status status) {
		this.updateUser.setStatus(status.name());
		return this;
	}

	public UserAttributeBuilder title(String title) {
		this.updateUser.setTitle(title);
		return this;
	}

	public UserAttributeBuilder company(String company) {
		this.updateUser.setCompany(company);
		return this;
	}

	public UserAttributeBuilder city(String city) {
		this.updateUser.setCity(city);
		return this;
	}

	public UserAttributeBuilder address(String address) {
		this.updateUser.setAddress(address);
		return this;
	}

	public UserAttributeBuilder zip(String zip) {
		this.updateUser.setZip(zip);
		return this;
	}

	public UserAttributeBuilder state(String state) {
		this.updateUser.setState(state);
		return this;
	}

	public UserAttributeBuilder country(Country country) {
		this.updateUser.setCountry(country.getName());
		return this;
	}

	public UserAttributeBuilder dateFormat(DateFormat dateFormat) {
		this.updateUser.setDateFormat(dateFormat.getValue());
		return this;
	}

	public UserAttributeBuilder timeZone(TimeZone timeZone) {
		this.updateUser.setTimeZone(timeZone.getValue());
		return this;
	}

	public UserAttributeBuilder phone(String phone) {
		this.updateUser.setPhone(phone);
		return this;
	}

	public UserAttributeBuilder skills(String skills) {
		this.updateUser.setSkills(skills);
		return this;
	}

	public UserAttributeBuilder mobile(String mobile) {
		this.updateUser.setMobile(mobile);
		return this;
	}

	public UserAttributeBuilder language(Language language) {
		this.updateUser.setLanguage(language.toString().toLowerCase());
		return this;
	}

	public void update() {
		try {
			updateUserRequest.setUser(updateUser);
			this.userApi.updateUserDetails(user.getId(), updateUserRequest);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public UpdateUser setUpdatedUser(com.intland.swagger.client.model.User user) {
		return new UpdateUser()
				.name(user.getName())
				.title(user.getTitle())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.mobile(user.getMobile())
				.status(user.getStatus().name())
				.company(user.getCompany())
				.address(user.getAddress())
				.city(user.getCity())
				.zip(user.getZip())
				.state(user.getState())
				.country(user.getCountry())
				.language(user.getLanguage())
				.skills(user.getSkills())
				.dateFormat(user.getDateFormat())
				.timeZone(user.getTimeZone());
	}
}
