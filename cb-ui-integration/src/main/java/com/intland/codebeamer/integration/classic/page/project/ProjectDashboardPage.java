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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.project.component.actionbar.dashboard.ProjectDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.project.component.tree.ProjectTreeComponent;
import com.intland.codebeamer.integration.classic.page.wiki.AbstractWikiDashboardPage;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("ProjectWikiPage")
public class ProjectDashboardPage extends AbstractWikiDashboardPage {

	private final ProjectDashboardActionbarComponent dashboardActionbarComponent;

	private final ProjectTreeComponent projectTreeComponent;

	public ProjectDashboardPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.dashboardActionbarComponent = new ProjectDashboardActionbarComponent(getCodebeamerPage());
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
	}

	public ProjectDashboardPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage, wikiPageId);
		this.dashboardActionbarComponent = new ProjectDashboardActionbarComponent(getCodebeamerPage());
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
	}

	@Override
	public ProjectDashboardActionbarComponent getActionbarComponent() {
		return dashboardActionbarComponent;
	}

	@Override
	public ProjectDashboardPage isActive() {
		super.isActive();
		return this;
	}

	public ProjectDashboardPage applyProjectTreeComponent(Consumer<ProjectTreeComponent> formConsumer) {
		formConsumer.accept(projectTreeComponent);
		return this;
	}
}
