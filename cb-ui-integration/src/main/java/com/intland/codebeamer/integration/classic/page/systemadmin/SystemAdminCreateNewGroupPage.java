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
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminCreateNewGroupAssertions;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminCreateNewGroupComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("SystemAdminCreateNewGroupPage")
public class SystemAdminCreateNewGroupPage extends AbstractCodebeamerPage<SystemAdminCreateNewGroupPage> {

	private static final String SYSTEM_ADMIN_CREATE_NEW_GROUP_PAGE_URL = "sysadmin/editGroup.spr";

	@Component("System admin create new group component")
	private SystemAdminCreateNewGroupComponent systemAdminCreateNewGroupComponent;

	public SystemAdminCreateNewGroupPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.systemAdminCreateNewGroupComponent = new SystemAdminCreateNewGroupComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public SystemAdminCreateNewGroupPage visit() {
		navigate(SYSTEM_ADMIN_CREATE_NEW_GROUP_PAGE_URL);
		return isActive();
	}

	@Override
	public SystemAdminCreateNewGroupPage isActive() {
		assertUrl(SYSTEM_ADMIN_CREATE_NEW_GROUP_PAGE_URL, "System admin create new group page should be the active page");
		return this;
	}

	public SystemAdminCreateNewGroupPage systemAdminCreateNewGroupComponent(Consumer<SystemAdminCreateNewGroupComponent> formConsumer) {
		formConsumer.accept(systemAdminCreateNewGroupComponent);
		return this;
	}

	public SystemAdminCreateNewGroupComponent getSystemAdminCreateNewGroupComponent() {
		return systemAdminCreateNewGroupComponent;
	}

	public SystemAdminCreateNewGroupPage assertSystemAdminCreateNewGroupComponent(Consumer<SystemAdminCreateNewGroupAssertions> assertion) {
		assertion.accept(getSystemAdminCreateNewGroupComponent().assertThat());
		return this;
	}

	@Action("Save")
	public SystemAdminCreateNewGroupPageNavigation save() {
		systemAdminCreateNewGroupComponent.getSaveButton().click();
		return new SystemAdminCreateNewGroupPageNavigation(getCodebeamerPage());
	}

	@Action("Cancel")
	public SystemAdminCreateNewGroupPageNavigation cancel() {
		systemAdminCreateNewGroupComponent.getCancelButton().click();
		return new SystemAdminCreateNewGroupPageNavigation(getCodebeamerPage());
	}
}
