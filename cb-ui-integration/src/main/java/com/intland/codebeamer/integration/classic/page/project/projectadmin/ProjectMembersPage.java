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
import com.intland.codebeamer.integration.classic.page.project.projectadmin.component.ProjectMembersActionBarAssertion;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.component.ProjectMembersActionBarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectMembersPage")
public class ProjectMembersPage extends AbstractCodebeamerPage<ProjectMembersPage> {

	private static final String PROJECT_MEMBERS_PAGE_URL = "project/%s/members";

	private final Project project;

	@Component("Action bar")
	private ProjectMembersActionBarComponent projectMembersActionBarComponent;

	public ProjectMembersPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.projectMembersActionBarComponent = new ProjectMembersActionBarComponent(getCodebeamerPage(), this.project);
	}

	@Action("Visit")
	public ProjectMembersPage visit() {
		navigate(formatProjectMembersPageUrl());
		return isActive();
	}

	@Override
	public ProjectMembersPage isActive() {
		assertUrl(formatProjectMembersPageUrl(), "Project members page should be the active page");
		return this;
	}

	@Override
	public ProjectMembersPage assertPage(Consumer<ProjectMembersPage> assertion) {
		assertion.accept(this);
		return this;
	}

	public ProjectMembersActionBarComponent getProjectMembersActionBarComponent() {
		return projectMembersActionBarComponent;
	}

	public ProjectMembersPage assertProjectMembersActionBarComponent(Consumer<ProjectMembersActionBarAssertion> assertion) {
		assertion.accept(getProjectMembersActionBarComponent().assertThat());
		return this;
	}

	private String formatProjectMembersPageUrl() {
		return PROJECT_MEMBERS_PAGE_URL.formatted(project.id().id());
	}
}
