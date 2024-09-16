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

package com.intland.codebeamer.integration.nextgen.page.topbar.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.useraccount.UserAccountPage;
import com.intland.codebeamer.integration.nextgen.page.login.LoginPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProfileMenuNavigation {

	private CodebeamerPage codebeamerPage;

	public ProfileMenuNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("Redirected to userAccount page")
	public UserAccountPage redirectedToUserAccountPage(User user) {
		return new UserAccountPage(codebeamerPage, user).visit();
	}

	@Action("Redirected to login page")
	public LoginPage redirectedToLoginPage() {
		return new LoginPage(codebeamerPage).visit();
	}
}
