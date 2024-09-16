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

import com.intland.codebeamer.integration.classic.page.user.PasswordChangePageNavigation;
import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class PasswordChangeFormComponent extends AbstractCodebeamerComponent<PasswordChangeFormComponent, PasswordChangeFormAssertions> {

	private CodebeamerPage codebeamerPage;

	public PasswordChangeFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "form#changePasswordForm");
		this.codebeamerPage = codebeamerPage;
	}

	@Action("visit forgot password")
	public PasswordChangePageNavigation visitForgotPasswordPage() {
		getForgotPasswordButton().click();
		return new PasswordChangePageNavigation(codebeamerPage);
	}

	public PasswordChangeFormComponent setOldPassword(String oldPassword) {
		getOldPasswordInput().fill(oldPassword);
		return this;
	}

	public PasswordChangeFormComponent setNewPassword(String newPassword) {
		getNewPasswordInput().fill(newPassword);
		return this;
	}

	public PasswordChangeFormComponent setNewPasswordAgain(String newPasswordAgain) {
		getNewPasswordAgainInput().fill(newPasswordAgain);
		return this;
	}

	public PasswordChangePageNavigation save() {
		getSaveChangePasswordButton().click();
		return new PasswordChangePageNavigation(this.getCodebeamerPage());
	}

	public CodebeamerLocator getOldPasswordInput() {
		return this.locator("input#oldPassword");
	}

	public CodebeamerLocator getNewPasswordInput() {
		return this.locator("input#newPassword");
	}

	public CodebeamerLocator getNewPasswordAgainInput() {
		return this.locator("input#newPasswordAgain");
	}

	public CodebeamerLocator getSaveChangePasswordButton() {
		return this.locator("input#changePasswordSubmit");
	}

	public CodebeamerLocator getForgotPasswordButton() {
		return this.locator("input#forgetPassword");
	}

	public CodebeamerLocator getErrorMessageOldPasswordElement() {
		return this.locator("span#oldPassword\\.errors");
	}

	public CodebeamerLocator getErrorMessageNewPasswordElement() {
		return this.locator("span#newPassword\\.errors");
	}

	@Override
	public PasswordChangeFormAssertions assertThat() {
		return new PasswordChangeFormAssertions(this);
	}
}
