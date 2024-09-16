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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.ProfilePhotoPage;
import com.intland.codebeamer.integration.classic.page.user.UserChangePasswordPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.UserAccountEditPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UserAccountPageNavigation {

	private CodebeamerPage codebeamerPage;

	private User user;

	public UserAccountPageNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public UserAccountPageNavigation(CodebeamerPage codebeamerPage, User user) {
		this.codebeamerPage = codebeamerPage;
		this.user = user;
	}

	@Action("redirectToViewPersonalWikiPage")
	public UserMyWikiPage redirectToViewPersonalWikiPage() {
		return new UserMyWikiPage(codebeamerPage).visit();
	}

	@Action("redirectToChangePasswordPage")
	public UserChangePasswordPage redirectToChangePasswordPage() {
		return new UserChangePasswordPage(codebeamerPage).visit();
	}

	@Action("redirectToProfilePhotoPage")
	public ProfilePhotoPage redirectToProfilePhotoPage() {
		return new ProfilePhotoPage(codebeamerPage, user).visit();
	}
	
	@Action("redirectedToUserEditAccountPage")
	public UserAccountEditPage redirectedToUserEditAccountPage() {
		return new UserAccountEditPage(codebeamerPage, user).isActive();
	}

	// TODO Need to add other user account page action bar option methods.
}
