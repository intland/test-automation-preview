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

package com.intland.codebeamer.integration.classic.page.user;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.component.actionbar.dashboard.UserDashboardActionbarComponent;
import com.intland.codebeamer.integration.classic.page.user.component.tree.UserTreeComponent;
import com.intland.codebeamer.integration.classic.page.wiki.AbstractWikiDashboardPage;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("UserWikiPage")
public class UserDashboardPage extends AbstractWikiDashboardPage {

	private final UserDashboardActionbarComponent actionbarComponent;

	private final UserTreeComponent userTreeComponent;

	public UserDashboardPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.actionbarComponent = new UserDashboardActionbarComponent(getCodebeamerPage());
		userTreeComponent = new UserTreeComponent(getCodebeamerPage());
	}

	public UserDashboardPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage, wikiPageId);
		this.actionbarComponent = new UserDashboardActionbarComponent(getCodebeamerPage());
		userTreeComponent = new UserTreeComponent(getCodebeamerPage());
	}

	@Override
	public UserDashboardActionbarComponent getActionbarComponent() {
		return actionbarComponent;
	}

	@Override
	public UserDashboardPage isActive() {
		super.isActive();
		return this;
	}

	public UserDashboardPage applyUserTreeComponent(Consumer<UserTreeComponent> formConsumer) {
		formConsumer.accept(userTreeComponent);
		return this;
	}

}
