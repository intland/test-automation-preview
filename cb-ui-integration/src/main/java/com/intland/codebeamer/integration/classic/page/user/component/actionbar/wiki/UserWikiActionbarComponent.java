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

package com.intland.codebeamer.integration.classic.page.user.component.actionbar.wiki;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.child.dashboard.UserDashboardNewChildDialog;
import com.intland.codebeamer.integration.classic.page.user.child.wiki.UserWikiNewChildDialog;
import com.intland.codebeamer.integration.classic.page.wiki.component.actionbar.AbstractWikiDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.wiki.component.actionbar.WikiDashboardMoreActionMenuComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class UserWikiActionbarComponent extends AbstractWikiDashboardActionbarComponent {

	private final UserWikiNewChildDialog wikiNewChildDialog;

	private final UserDashboardNewChildDialog dashboardNewChildDialog;

	@Component(value = "More menu", includeInSitemap = false)
	private final WikiDashboardMoreActionMenuComponent wikiMoreActionMenuComponent;

	public UserWikiActionbarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiNewChildDialog = new UserWikiNewChildDialog(codebeamerPage);
		dashboardNewChildDialog = new UserDashboardNewChildDialog(codebeamerPage);
		wikiMoreActionMenuComponent = new WikiDashboardMoreActionMenuComponent(codebeamerPage, getSelector());
	}

	public UserWikiNewChildDialog createNewWiki() {
		getWikiPageCreateButton().click();
		return wikiNewChildDialog;
	}

	@Override
	public CodebeamerLocator getWikiPageCreateButton() {
		return this.locator("[id*='middleHeaderDiv'] [src*='icon_new_childpage']");
	}

	@Override
	public UserDashboardNewChildDialog createNewDashBoard() {
		getDashboardCreateButton().click();
		return dashboardNewChildDialog;
	}

	@Override
	public WikiDashboardMoreActionMenuComponent openMoreActionMenu() {
		getMoreActionButton().click();
		return wikiMoreActionMenuComponent;
	}

	@Override
	public UserWikiActionbarAssertion assertThat() {
		return new UserWikiActionbarAssertion(this);
	}

}
