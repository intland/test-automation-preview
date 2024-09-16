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

package com.intland.codebeamer.integration.classic.page.projectcreate;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.projectcreate.component.ProjectCreateActionBarComponent;
import com.intland.codebeamer.integration.classic.page.projectcreate.component.ProjectCreateGeneralSettingsFormComponent;
import com.intland.codebeamer.integration.classic.page.projectcreate.component.ProjectCreateSelectKindOfProjectFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectCreatePage")
public class ProjectCreatePage extends AbstractCodebeamerPage<ProjectCreatePage> {

	private static final String PROJECT_CREATE_PAGE_PATH = "createProject.spr";

	private static final Pattern PROJECT_CREATE_PAGE_PATTERN = Pattern.compile(".*/createProject.spr.*");

	@Component("Action bar")
	private ProjectCreateActionBarComponent projectCreateActionBarComponent;

	@Component("Kind of Project selection step content")
	private ProjectCreateSelectKindOfProjectFormComponent projectCreateSelectKindOfProjectFormComponent;

	@Component("New Project general settings step content")
	private ProjectCreateGeneralSettingsFormComponent projectCreateGeneralSettingsFormComponent;

	public ProjectCreatePage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.projectCreateActionBarComponent = new ProjectCreateActionBarComponent(getCodebeamerPage());
		this.projectCreateSelectKindOfProjectFormComponent = new ProjectCreateSelectKindOfProjectFormComponent(
				getCodebeamerPage());
		this.projectCreateGeneralSettingsFormComponent = new ProjectCreateGeneralSettingsFormComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ProjectCreatePage visit() {
		navigate(PROJECT_CREATE_PAGE_PATH);
		return isActive();
	}

	@Override
	public ProjectCreatePage isActive() {
		assertUrl(PROJECT_CREATE_PAGE_PATTERN, "Project create page should be the active page");
		return this;
	}

	public ProjectCreateActionBarComponent getActionBarComponent() {
		return projectCreateActionBarComponent;
	}

	public ProjectCreatePage actionBarComponent(Consumer<ProjectCreateActionBarComponent> formConsumer) {
		formConsumer.accept(projectCreateActionBarComponent);
		return this;
	}

	public ProjectCreateSelectKindOfProjectFormComponent getProjectCreateSelectKindOfProjectFormComponent() {
		return projectCreateSelectKindOfProjectFormComponent;
	}

	public ProjectCreatePage projectCreateSelectKindOfProjectFormComponent(
			Consumer<ProjectCreateSelectKindOfProjectFormComponent> formConsumer) {
		formConsumer.accept(projectCreateSelectKindOfProjectFormComponent);
		return this;
	}

	public ProjectCreateGeneralSettingsFormComponent getProjectCreateGeneralSettingsFormComponent() {
		return projectCreateGeneralSettingsFormComponent;
	}

	public ProjectCreatePage projectCreateGeneralSettingsFormComponent(
			Consumer<ProjectCreateGeneralSettingsFormComponent> formConsumer) {
		formConsumer.accept(projectCreateGeneralSettingsFormComponent);
		return this;
	}

}
