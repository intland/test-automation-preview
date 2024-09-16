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

package com.intland.codebeamer.integration.classic.page.user.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ForgotPasswordPage")
public class ForgotPasswordPage extends AbstractCodebeamerPage<ForgotPasswordPage> {

	private static final String FORGOTPASSWORD = "lostPassword.spr";

	public ForgotPasswordPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Visit")
	public ForgotPasswordPage visit() {
		navigate(FORGOTPASSWORD);
		return isActive();
	}

	@Override
	public ForgotPasswordPage isActive() {
		assertUrl(FORGOTPASSWORD, "Forgot password page should be the active page");
		return this;
	}
}
