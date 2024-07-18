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
import com.intland.codebeamer.integration.classic.page.user.component.actionbar.wiki.UserWikiActionbarComponent;
import com.intland.codebeamer.integration.classic.page.user.component.tree.UserTreeComponent;
import com.intland.codebeamer.integration.classic.page.wiki.AbstractWikiDashboardPage;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("UserWikiPage")
public class UserWikiPage extends AbstractWikiDashboardPage {

	private final UserWikiActionbarComponent wikiActionbarComponent;

	private final UserTreeComponent userTreeComponent;

	public UserWikiPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiActionbarComponent = new UserWikiActionbarComponent(getCodebeamerPage());
		userTreeComponent = new UserTreeComponent(getCodebeamerPage());
	}

	public UserWikiPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage, wikiPageId);
		this.wikiActionbarComponent = new UserWikiActionbarComponent(getCodebeamerPage());
		userTreeComponent = new UserTreeComponent(getCodebeamerPage());
	}

	@Override
	public UserWikiActionbarComponent getActionbarComponent() {
		return wikiActionbarComponent;
	}

	@Override
	public UserWikiPage isActive() {
		super.isActive();
		return this;
	}

	public UserWikiPage applyUserTreeComponent(Consumer<UserTreeComponent> formConsumer) {
		formConsumer.accept(userTreeComponent);
		return this;
	}

}
