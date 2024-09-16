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

package com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.ProfilePhotoPage;
import com.intland.codebeamer.integration.classic.page.user.UserChangePasswordPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.UserAccountEditPage;
import com.intland.codebeamer.integration.classic.page.useraccount.UserAccountPageNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class UserAccountActionBarComponent extends
		AbstractCodebeamerComponent<UserAccountActionBarComponent, UserAccountActionBarAssertions> {

	private UserAccountPageNavigation userAccountPageNavigation;

	public UserAccountActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".actionBar");
		this.userAccountPageNavigation = new UserAccountPageNavigation(codebeamerPage);
	}

	public UserAccountActionBarComponent(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage, ".actionBar");
		this.userAccountPageNavigation = new UserAccountPageNavigation(codebeamerPage, user);
	}

	public UserMyWikiPage visitPersonalWikiPage() {
		getPersonalWikiPageLink().click();
		return userAccountPageNavigation.redirectToViewPersonalWikiPage();
	}

	public UserChangePasswordPage visitChangePasswordPage() {
		getChangePasswordPagePageLink().click();
		return userAccountPageNavigation.redirectToChangePasswordPage();
	}

	public UserAccountEditPage visitUserAccountEditPage() {
		getEditProfileLink().click();
		return userAccountPageNavigation.redirectedToUserEditAccountPage();
	}

	public ProfilePhotoPage visitEditProfilePhotoPage() {
		getProfilePhotoPageLink().click();
		return userAccountPageNavigation.redirectToProfilePhotoPage();
	}

	public void visitEditAccountSshKeyPage() {
		getEditAccountSshKeyPageLink().click();
		//Todo Need to return EditAccountSshKeyPage once EditAccountSshKeyPage task code merged.
	}

	public CodebeamerLocator getEditProfileLink() {
		return this.locator("a[href*='updateUser.spr']");
	}

	public CodebeamerLocator getPersonalWikiPageLink() {
		return this.locator("a[href*='/wiki/']");
	}

	public CodebeamerLocator getChangePasswordPagePageLink() {
		return this.locator("a[href*='changePassword.spr']");
	}

	public CodebeamerLocator getProfilePhotoPageLink() {
		return this.locator("a.actionLink[href*='updateUserPhoto']");
	}

	public CodebeamerLocator getEditAccountSshKeyPageLink() {
		return this.locator("a[href*='accountSshKeys']");
	}

	@Override
	public UserAccountActionBarAssertions assertThat() {
		return new UserAccountActionBarAssertions(this);
	}
}
