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
import com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar.UserAccountActionBarAssertions;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("MyUserAccountPage")
public class MyUserAccountPage extends AbstractAccountPage<MyUserAccountPage> {

	private static final String USER_ACCOUNT = "myaccount.spr";

	public MyUserAccountPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	public MyUserAccountPage(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage, user);
	}

	@Action("Visit")
	public MyUserAccountPage visit() {
		navigate(USER_ACCOUNT);
		return isActive();
	}

	@Override
	public MyUserAccountPage isActive() {
		assertUrl(USER_ACCOUNT, "My user account page should be the active page");
		return this;
	}

	public MyUserAccountPage assertUserAccountFormComponent(Consumer<UserAccountPageAssertions> assertion) {
		assertion.accept(getUserAccountFormComponent().assertThat());
		return this;
	}

	public MyUserAccountPage assertUserAccountActionBarPageComponent(Consumer<UserAccountActionBarAssertions> assertion) {
		assertion.accept(getUserAccountActionBarComponent().assertThat());
		return this;
	}

	@Override
	public MyUserAccountPage assertPage(Consumer<MyUserAccountPage> assertion) {
		assertion.accept(this);
		return this;
	}
}
