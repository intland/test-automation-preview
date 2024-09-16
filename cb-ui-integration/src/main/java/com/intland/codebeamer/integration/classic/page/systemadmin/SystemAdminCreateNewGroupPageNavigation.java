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

package com.intland.codebeamer.integration.classic.page.systemadmin;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class SystemAdminCreateNewGroupPageNavigation {

	private CodebeamerPage codebeamerPage;

	public SystemAdminCreateNewGroupPageNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToUserGroupsPage")
	public SystemAdminUserGroupsPage redirectedToUserGroupsPage() {
		return new SystemAdminUserGroupsPage(codebeamerPage).isActive();
	}

	@Action("redirectedToCreateNewGroupPage")
	public SystemAdminCreateNewGroupPage redirectedToCreateNewGroupPage() {
		return new SystemAdminCreateNewGroupPage(codebeamerPage).isActive();
	}
}
