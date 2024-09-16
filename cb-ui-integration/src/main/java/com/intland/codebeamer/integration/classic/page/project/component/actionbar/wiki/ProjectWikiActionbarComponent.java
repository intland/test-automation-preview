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

package com.intland.codebeamer.integration.classic.page.project.component.actionbar.wiki;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.project.child.dashboard.ProjectDashboardNewChildDialog;
import com.intland.codebeamer.integration.classic.page.project.child.wiki.ProjectWikiNewChildDialog;
import com.intland.codebeamer.integration.classic.page.wiki.component.actionbar.AbstractWikiDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.wiki.component.actionbar.WikiDashboardMoreActionMenuComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class ProjectWikiActionbarComponent extends AbstractWikiDashboardActionbarComponent {

	private final ProjectWikiNewChildDialog wikiNewChildDialog;

	private final ProjectDashboardNewChildDialog dashboardNewChildDialog;

	@Component(value = "More menu", includeInSitemap = false)
	private final WikiDashboardMoreActionMenuComponent wikiMoreActionMenuComponent;

	public ProjectWikiActionbarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiNewChildDialog = new ProjectWikiNewChildDialog(codebeamerPage);
		dashboardNewChildDialog = new ProjectDashboardNewChildDialog(codebeamerPage);
		wikiMoreActionMenuComponent = new WikiDashboardMoreActionMenuComponent(codebeamerPage, getSelector());
	}

	@Override
	public ProjectWikiNewChildDialog createNewWiki() {
		getWikiPageCreateButton().click();
		return wikiNewChildDialog;
	}

	@Override
	public CodebeamerLocator getWikiPageCreateButton() {
		return this.locator("[id*='middleHeaderDiv'] [src*='icon_new_childpage']");
	}

	@Override
	public ProjectDashboardNewChildDialog createNewDashBoard() {
		getDashboardCreateButton().click();
		return dashboardNewChildDialog;
	}

	@Override
	public WikiDashboardMoreActionMenuComponent openMoreActionMenu() {
		getMoreActionButton().click();
		return wikiMoreActionMenuComponent;
	}

	@Override
	public ProjectWikiActionbarAssertion assertThat() {
		return new ProjectWikiActionbarAssertion(this);
	}

}
