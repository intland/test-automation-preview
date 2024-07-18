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

package com.intland.codebeamer.integration.classic.page.user.child.wiki;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.UserWikiPage;
import com.intland.codebeamer.integration.classic.page.wiki.child.AbstractWikiNewChildDialogNavigation;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UserWikiNewChildDialogNavigation extends AbstractWikiNewChildDialogNavigation {

	public UserWikiNewChildDialogNavigation(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	@Action("redirectedToWikiNewChildDialog")
	public UserWikiNewChildDialog redirectedToWikiNewChildDialog() {
		return new UserWikiNewChildDialog(codebeamerPage);
	}

	@Action("redirectedToUserWikiPage")
	public UserWikiPage redirectedToUserWikiPage() {
		return new UserWikiPage(codebeamerPage).isActive();
	}
}
