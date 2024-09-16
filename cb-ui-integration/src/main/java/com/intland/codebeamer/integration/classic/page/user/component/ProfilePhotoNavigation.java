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
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.ProfilePhotoPage;
import com.intland.codebeamer.integration.classic.page.useraccount.MyUserAccountPage;

public class ProfilePhotoNavigation {

	private CodebeamerPage codebeamerPage;

	private User user;

	public ProfilePhotoNavigation(CodebeamerPage codebeamerPage, User user) {
		this.codebeamerPage = codebeamerPage;
		this.user = user;
	}

	public ProfilePhotoPage redirectedToProfilePhotoPage() {
		return new ProfilePhotoPage(codebeamerPage, user).isActive();
	}

	public MyUserAccountPage redirectedToMyUserAccountPage() {
		return new MyUserAccountPage(codebeamerPage, user).visit();
	}
}