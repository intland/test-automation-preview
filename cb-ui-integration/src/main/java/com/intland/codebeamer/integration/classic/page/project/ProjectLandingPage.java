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
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.classic.page.project.component.actionbar.dashboard.ProjectDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.project.component.tree.ProjectTreeComponent;
import com.intland.codebeamer.integration.classic.page.wiki.component.WikiContentComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectWikiPage")
public class ProjectLandingPage extends AbstractCodebeamerPage<ProjectLandingPage> {

	private static final Logger logger = LogManager.getLogger(ProjectLandingPage.class);

	private static final String PROJECT_LANDING_PAGE_URL = "project/%s";

	public static final Pattern PROJECT_LANDING_PAGE_PATTERN = Pattern.compile(".*/project/\\d+");

	private final Project project;

	@Component("Action bar")
	private final ProjectDashboardActionbarComponent dashboardActionbarComponent;

	@Component("Wiki tree")
	private final ProjectTreeComponent projectTreeComponent;

	@Component("Wiki content")
	private final WikiContentComponent wikiContentComponent;

	public ProjectLandingPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
		this.dashboardActionbarComponent = new ProjectDashboardActionbarComponent(getCodebeamerPage());
		this.wikiContentComponent = new WikiContentComponent(getCodebeamerPage(), "#centerDiv");
		logger.debug("New project wiki landing created. Url: {}", PROJECT_LANDING_PAGE_URL);
	}

	public ProjectLandingPage(CodebeamerPage codebeamerPage, ProjectId projectId) {
		super(codebeamerPage);
		this.project = new Project(projectId, "");
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
		this.dashboardActionbarComponent = new ProjectDashboardActionbarComponent(getCodebeamerPage());
		this.wikiContentComponent = new WikiContentComponent(getCodebeamerPage(), "#centerDiv");
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

	public ProjectLandingPage wikiContentComponent(Consumer<WikiContentComponent> formConsumer) {
		formConsumer.accept(wikiContentComponent);
		return this;
	}

	private String formatProjectLandingPageUrl() {
		return PROJECT_LANDING_PAGE_URL.formatted(Integer.valueOf(project.id().id()));
	}
}
