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

package com.intland.codebeamer.integration.classic.page.user.child.dashboard;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.AbstractDashboardNewChildDialogNavigation;
import com.intland.codebeamer.integration.classic.page.user.UserDashboardPage;
import com.intland.codebeamer.integration.classic.page.user.UserWikiPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UserDashboardNewChildDialogNavigation extends AbstractDashboardNewChildDialogNavigation {

	public UserDashboardNewChildDialogNavigation(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	@Action("redirectedToWikiNewChildDialog")
	public UserDashboardNewChildDialog redirectedToDashboardNewChildDialog() {
		return new UserDashboardNewChildDialog(codebeamerPage);
	}

	@Action("redirectedToUserDashboardPage")
	public UserDashboardPage redirectedToUserDashboardPage() {
		return new UserDashboardPage(codebeamerPage).isActive();
	}

}
