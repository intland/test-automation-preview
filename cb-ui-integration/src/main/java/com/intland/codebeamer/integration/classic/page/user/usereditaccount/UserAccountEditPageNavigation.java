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

package com.intland.codebeamer.integration.classic.page.user.usereditaccount;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.UserChangePasswordPage;
import com.intland.codebeamer.integration.classic.page.useraccount.MyUserAccountPage;
import com.intland.codebeamer.integration.classic.page.useraccount.UserAccountPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UserAccountEditPageNavigation {

	private CodebeamerPage codebeamerPage;

	private User user;

	public UserAccountEditPageNavigation(CodebeamerPage codebeamerPage, User user) {
		this.codebeamerPage = codebeamerPage;
		this.user = user;
	}

	@Action("redirectedToMyUserAccountPage")
	public MyUserAccountPage redirectedToMyUserAccountPage() {
		return new MyUserAccountPage(codebeamerPage).isActive();
	}

	@Action("redirectedToUserAccountPage")
	public UserAccountPage redirectedToUserAccountPage() {
		return new UserAccountPage(codebeamerPage, this.user).isActive();
	}

	@Action("redirectedToUserEditAccountPage")
	public UserAccountEditPage redirectedToUserEditAccountPage() {
		return new UserAccountEditPage(codebeamerPage, this.user).isActive();
	}

	@Action("redirectedToUserChangePasswordPage")
	public UserChangePasswordPage redirectedToUserChangePasswordPage() {
		return new UserChangePasswordPage(codebeamerPage).isActive();
	}
}

