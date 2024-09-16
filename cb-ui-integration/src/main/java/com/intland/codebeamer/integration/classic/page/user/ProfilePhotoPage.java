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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.UserPhoto;
import com.intland.codebeamer.integration.classic.page.user.component.ProfilePhotoAssertions;
import com.intland.codebeamer.integration.classic.page.user.component.ProfilePhotoComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;

@Page("ProfilePhotoPage")
public class ProfilePhotoPage extends AbstractCodebeamerPage<ProfilePhotoPage> {

	private static final String USER_PROFILE_PHOTO_PAGE_PATH = "/updateUserPhoto?userId=%s";

	private User user;

	@Component("User profile photo component")
	private ProfilePhotoComponent profilePhotoComponent;

	public ProfilePhotoPage(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage);
		this.profilePhotoComponent = new ProfilePhotoComponent(codebeamerPage, user);
		this.user = user;
	}

	@Action("Visit")
	public ProfilePhotoPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public ProfilePhotoPage isActive() {
		assertUrl(formatPageUrl(), "User Profile Photo should be an active page");
		return this;
	}

	public ProfilePhotoPage profilePhotoComponent(Consumer<ProfilePhotoComponent> formConsumer) {
		formConsumer.accept(profilePhotoComponent);
		return this;
	}

	public ProfilePhotoComponent getProfilePhotoComponent() {
		return new ProfilePhotoComponent(getCodebeamerPage(), this.user);
	}

	public ProfilePhotoPage assertProfilePhotoComponent(Consumer<ProfilePhotoAssertions> assertion) {
		assertion.accept(getProfilePhotoComponent().assertThat());
		return this;
	}

	public UserPhoto getProfilePhotoSrcAttribute() {
		return new UserPhoto(profilePhotoComponent.getUserPhoto().getLocator().getAttribute("src"));
	}

	private String formatPageUrl() {
		return USER_PROFILE_PHOTO_PAGE_PATH.formatted(user.getUserId().id());
	}
}