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
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminCreateNewGroupPageNavigation;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminUserGroupsPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class SystemAdminDashboardComponent extends AbstractCodebeamerComponent<SystemAdminDashboardComponent, SystemAdminDashboardAssertions> {

	private SystemAdminCreateNewGroupPageNavigation systemAdminCreateNewGroupPageNavigation;

	public SystemAdminDashboardComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".contentArea");
		this.systemAdminCreateNewGroupPageNavigation = new SystemAdminCreateNewGroupPageNavigation(codebeamerPage);
	}

	public CodebeamerLocator getUserGroupElement() {
		return this.locator("[href*='userGroups.spr']");
	}

	public SystemAdminUserGroupsPage visitUserGroupPage() {
		getUserGroupElement().click();
		return systemAdminCreateNewGroupPageNavigation.redirectedToUserGroupsPage();
	}

	@Override
	public SystemAdminDashboardAssertions assertThat() {
		return new SystemAdminDashboardAssertions(this);
	}
}
