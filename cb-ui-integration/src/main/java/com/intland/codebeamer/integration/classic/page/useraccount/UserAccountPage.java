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

package com.intland.codebeamer.integration.classic.page.useraccount;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.useraccount.component.UserAccountPageAssertions;
import com.intland.codebeamer.integration.classic.page.useraccount.component.UserAccountFormComponent;
import com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar.UserAccountActionBarAssertions;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("UserAccountPage")
public class UserAccountPage extends AbstractAccountPage<UserAccountPage> {

	private static final String USER_ACCOUNT = "/userdata/%s";

	protected User user;

	public UserAccountPage(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage, user);
		this.user = user;
	}

	@Action("Visit")
	public UserAccountPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public UserAccountPage isActive() {
		assertUrl(formatPageUrl(), "User account page should be the active page");
		return this;
	}

	public UserAccountPage userAccountComponent(Consumer<UserAccountFormComponent> assertion) {
		assertion.accept(userAccountFormComponent);
		return this;
	}

	public UserAccountPage assertUserAccountFormComponent(Consumer<UserAccountPageAssertions> assertion) {
		assertion.accept(getUserAccountFormComponent().assertThat());
		return this;
	}

	public UserAccountPage assertUserAccountActionBarPageComponent(Consumer<UserAccountActionBarAssertions> assertion) {
		assertion.accept(getUserAccountActionBarComponent().assertThat());
		return this;
	}

	@Override
	public UserAccountPage assertPage(Consumer<UserAccountPage> assertion) {
		assertion.accept(this);
		return this;
	}

	private String formatPageUrl() {
		return USER_ACCOUNT.formatted(user.getId());
	}
}
