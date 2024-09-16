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

package com.intland.codebeamer.integration.classic.page.systemadmin.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminCreateNewGroupPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminCreateNewGroupPageNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class SystemAdminUserGroupsComponent extends AbstractCodebeamerComponent<SystemAdminUserGroupsComponent, SystemAdminUserGroupsAssertions> {

	private SystemAdminCreateNewGroupPageNavigation systemAdminCreateNewGroupPageNavigation;

	public SystemAdminUserGroupsComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".contentArea");
		this.systemAdminCreateNewGroupPageNavigation = new SystemAdminCreateNewGroupPageNavigation(codebeamerPage);
	}

	public CodebeamerLocator getNewUserGroupButton() {
		return this.locator("a.actionLink[href*='/editGroup.spr']");
	}

	public SystemAdminCreateNewGroupPage visitNewUserGroupPage() {
		getNewUserGroupButton().click();
		return systemAdminCreateNewGroupPageNavigation.redirectedToCreateNewGroupPage();
	}

	@Override
	public SystemAdminUserGroupsAssertions assertThat() {
		return new SystemAdminUserGroupsAssertions(this);
	}
}
