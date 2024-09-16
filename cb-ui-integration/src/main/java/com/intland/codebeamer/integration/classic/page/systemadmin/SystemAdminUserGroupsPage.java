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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminUserGroupsAssertions;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminUserGroupsComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("SystemAdminUserGroupsPage")
public class SystemAdminUserGroupsPage extends AbstractCodebeamerPage<SystemAdminUserGroupsPage> {

	private static final String SYSTEM_ADMIN_USER_GROUPS_PAGE_URL = "sysadmin/userGroups.spr";

	@Component("System admin user groups component")
	private SystemAdminUserGroupsComponent systemAdminUserGroupsComponent;

	public SystemAdminUserGroupsPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.systemAdminUserGroupsComponent = new SystemAdminUserGroupsComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public SystemAdminUserGroupsPage visit() {
		navigate(SYSTEM_ADMIN_USER_GROUPS_PAGE_URL);
		return isActive();
	}

	@Override
	public SystemAdminUserGroupsPage isActive() {
		assertUrl(SYSTEM_ADMIN_USER_GROUPS_PAGE_URL, "System admin user groups page should be the active page");
		return this;
	}

	public SystemAdminUserGroupsComponent getSystemAdminUserGroupsComponent() {
		return systemAdminUserGroupsComponent;
	}

	public SystemAdminUserGroupsPage assertSystemAdminUserGroupsComponent(Consumer<SystemAdminUserGroupsAssertions> assertion) {
		assertion.accept(getSystemAdminUserGroupsComponent().assertThat());
		return this;
	}
}
