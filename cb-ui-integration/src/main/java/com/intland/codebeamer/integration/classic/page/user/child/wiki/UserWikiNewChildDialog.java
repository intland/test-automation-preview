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

package com.intland.codebeamer.integration.classic.page.user.child.wiki;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.child.wiki.component.UserWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.user.child.wiki.component.UserWikiNewChildDialogFormComponent;
import com.intland.codebeamer.integration.classic.page.wiki.child.AbstractWikiNewChildDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class UserWikiNewChildDialog
		extends AbstractWikiNewChildDialog<UserWikiNewChildDialogFormComponent, UserWikiNewChildDialogFormAssertions> {

	@Component("New child wiki dialog")
	private final UserWikiNewChildDialogFormComponent wikiNewChildDialogFormComponent;

	public UserWikiNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiNewChildDialogFormComponent = new UserWikiNewChildDialogFormComponent(getCodebeamerPage(),
				this.getDialogLocator());
	}

	@Override
	public UserWikiNewChildDialog wikiNewChildDialogFormComponent(
			Consumer<UserWikiNewChildDialogFormComponent> formConsumer) {
		formConsumer.accept(this.wikiNewChildDialogFormComponent);
		return this;
	}

	@Override
	public UserWikiNewChildDialog assertWikiNewChildDialogFormComponent(
			Consumer<UserWikiNewChildDialogFormAssertions> assertion) {
		assertion.accept(this.wikiNewChildDialogFormComponent.assertThat());
		return this;
	}

	@Override
	public UserWikiNewChildDialogNavigation save() {
		this.wikiNewChildDialogFormComponent.save();
		return new UserWikiNewChildDialogNavigation(getCodebeamerPage());
	}

}
