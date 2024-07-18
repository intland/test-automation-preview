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
import com.intland.codebeamer.integration.classic.page.project.component.actionbar.wiki.ProjectWikiActionbarComponent;
import com.intland.codebeamer.integration.classic.page.project.component.tree.ProjectTreeComponent;
import com.intland.codebeamer.integration.classic.page.wiki.AbstractWikiDashboardPage;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("ProjectWikiPage")
public class ProjectWikiPage extends AbstractWikiDashboardPage {

	private final ProjectTreeComponent projectTreeComponent;

	private final ProjectWikiActionbarComponent wikiActionbarComponent;

	public ProjectWikiPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiActionbarComponent = new ProjectWikiActionbarComponent(getCodebeamerPage());
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
	}

	public ProjectWikiPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage, wikiPageId);
		this.wikiActionbarComponent = new ProjectWikiActionbarComponent(getCodebeamerPage());
		this.projectTreeComponent = new ProjectTreeComponent(getCodebeamerPage());
	}

	@Override
	public ProjectWikiPage isActive() {
		super.isActive();
		return this;
	}

	public ProjectWikiActionbarComponent getActionbarComponent() {
		return wikiActionbarComponent;
	}

	public ProjectWikiPage applyProjectTreeComponent(Consumer<ProjectTreeComponent> formConsumer) {
		formConsumer.accept(projectTreeComponent);
		return this;
	}
}
