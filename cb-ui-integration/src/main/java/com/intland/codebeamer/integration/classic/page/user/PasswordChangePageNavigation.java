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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.login.LoginPage;
import com.intland.codebeamer.integration.classic.page.user.component.ForgotPasswordPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class PasswordChangePageNavigation {

	private CodebeamerPage codebeamerPage;

	public PasswordChangePageNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToLoginPage")
	public LoginPage redirectedToLoginPage() {
		return new LoginPage(codebeamerPage);
	}

	@Action("redirectedToUserChangePasswordPage")
	public UserChangePasswordPage redirectedToUserChangePasswordPage() {
		return new UserChangePasswordPage(codebeamerPage).isActive();
	}

	@Action("redirectedToForgotPasswordPage")
	public ForgotPasswordPage redirectedToForgotPasswordPage() {
		return new ForgotPasswordPage(codebeamerPage).isActive();
	}
}
