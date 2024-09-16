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

package com.intland.codebeamer.integration.classic.page.project.projectadmin;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.component.AddRoleFormAssertion;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.component.AddRoleFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("AddRolePage")
public class AddRolePage extends AbstractCodebeamerPage<AddRolePage> {

	private static final String ADD_ROLE_PAGE_URL = "proj/admin/editRole.spr?proj_id=%s";

	private final Project project;

	@Component("Add role form component")
	private AddRoleFormComponent addRoleFormComponent;

	public AddRolePage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.addRoleFormComponent = new AddRoleFormComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public AddRolePage visit() {
		navigate(formatAddRolePageUrl());
		return isActive();
	}

	@Override
	public AddRolePage isActive() {
		assertUrl(formatAddRolePageUrl(), "Add role page should be the active page");
		return this;
	}

	@Override
	public AddRolePage assertPage(Consumer<AddRolePage> assertion) {
		assertion.accept(this);
		return this;
	}

	public AddRoleFormComponent getAddRoleFormComponent() {
		return this.addRoleFormComponent;
	}

	public AddRolePage assertAddRoleFormComponent(Consumer<AddRoleFormAssertion> assertion) {
		assertion.accept(getAddRoleFormComponent().assertThat());
		return this;
	}

	private String formatAddRolePageUrl() {
		return ADD_ROLE_PAGE_URL.formatted(this.project.id().id());
	}
}
