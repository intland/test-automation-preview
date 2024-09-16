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
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminDashboardAssertions;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminDashboardComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("SystemAdminDashboardPage")
public class SystemAdminDashboardPage extends AbstractCodebeamerPage<SystemAdminDashboardPage> {

	private static final String SYSTEM_ADMIN_DASHBOARD_PAGE_URL = "sysadmin.spr";

	@Component("System admin dashboard component")
	private SystemAdminDashboardComponent systemAdminDashboardComponent;

	public SystemAdminDashboardPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.systemAdminDashboardComponent = new SystemAdminDashboardComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public SystemAdminDashboardPage visit() {
		navigate(SYSTEM_ADMIN_DASHBOARD_PAGE_URL);
		return isActive();
	}

	@Override
	public SystemAdminDashboardPage isActive() {
		assertUrl(SYSTEM_ADMIN_DASHBOARD_PAGE_URL, "System admin dashboard page should be the active page");
		return this;
	}

	public SystemAdminDashboardComponent getSystemAdminDashboardComponent() {
		return systemAdminDashboardComponent;
	}

	public SystemAdminDashboardPage assertSystemAdminDashboardComponent(Consumer<SystemAdminDashboardAssertions> assertion) {
		assertion.accept(getSystemAdminDashboardComponent().assertThat());
		return this;
	}
}
