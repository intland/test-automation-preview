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
import com.intland.codebeamer.integration.classic.page.user.component.PasswordChangeFormAssertions;
import com.intland.codebeamer.integration.classic.page.user.component.PasswordChangeFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("UserPasswordChangePage")
public class UserChangePasswordPage extends AbstractCodebeamerPage<UserChangePasswordPage> {

	private static final String CHANGEPASSWORD = "changePassword.spr";

	@Component("Password Change form")
	private PasswordChangeFormComponent passwordChangeFormComponent;

	public UserChangePasswordPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.passwordChangeFormComponent = new PasswordChangeFormComponent(codebeamerPage);
	}

	@Action("Visit")
	public UserChangePasswordPage visit() {
		navigate(CHANGEPASSWORD);
		return isActive();
	}

	@Override
	public UserChangePasswordPage isActive() {
		assertUrl(CHANGEPASSWORD, "Change password page should be the active page");
		return this;
	}

	public PasswordChangeFormComponent getPasswordChangeFormComponent() {
		return passwordChangeFormComponent;
	}

	public PasswordChangeFormComponent passwordChangeFormComponent(Consumer<PasswordChangeFormComponent> formConsumer) {
		formConsumer.accept(passwordChangeFormComponent);
		return passwordChangeFormComponent;
	}

	public UserChangePasswordPage assertPasswordChangeFormComponent(Consumer<PasswordChangeFormAssertions> assertion) {
		assertion.accept(getPasswordChangeFormComponent().assertThat());
		return this;
	}
}
