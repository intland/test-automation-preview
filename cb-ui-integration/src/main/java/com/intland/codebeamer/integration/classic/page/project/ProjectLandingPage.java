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

package com.intland.codebeamer.integration.classic.page.project;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.project.component.actionbar.dashboard.ProjectDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.project.component.tree.ProjectTreeComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectWikiPage")
public class ProjectLandingPage extends AbstractCodebeamerPage<ProjectLandingPage> {

	private static final Logger logger = LogManager.getLogger(ProjectLandingPage.class);

	private static final String PROJECT_LANDING_PAGE_URL = "project/%s";

	private final Project project;

	private final ProjectDashboardActionbarComponent dashboardActionbarComponent;

	private final ProjectTreeComponent projectTreeComponent;

	public ProjectLandingPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
		this.dashboardActionbarComponent = new ProjectDashboardActionbarComponent(getCodebeamerPage());
		logger.debug("New project wiki landing created. Url: {}", PROJECT_LANDING_PAGE_URL);
	}

	@Action("Visit")
	public ProjectLandingPage visit() {
		navigate(formatProjectLandingPageUrl());
		return isActive();
	}

	@Override
	public ProjectLandingPage isActive() {
		assertUrl(formatProjectLandingPageUrl(), "Project landing page should be the active page");
		return this;
	}

	@Override
	public ProjectLandingPage assertPage(Consumer<ProjectLandingPage> assertion) {
		assertion.accept(this);
		return this;
	}

	public ProjectDashboardActionbarComponent getDashboardActionbarComponent() {
		return dashboardActionbarComponent;
	}

	public ProjectLandingPage applyProjectTreeComponent(Consumer<ProjectTreeComponent> formConsumer) {
		formConsumer.accept(projectTreeComponent);
		return this;
	}

	private String formatProjectLandingPageUrl() {
		return PROJECT_LANDING_PAGE_URL.formatted(Integer.valueOf(project.id().id()));
	}
}
