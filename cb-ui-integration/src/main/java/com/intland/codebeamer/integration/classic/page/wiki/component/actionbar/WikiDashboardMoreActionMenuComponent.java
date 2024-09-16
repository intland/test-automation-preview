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

package com.intland.codebeamer.integration.classic.page.wiki.component.actionbar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.comment.WikiDashboardAddCommentDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class WikiDashboardMoreActionMenuComponent extends AbstractCodebeamerComponent<WikiDashboardMoreActionMenuComponent, WikiDashboardMoreActionMenuAssertions> {

	public WikiDashboardMoreActionMenuComponent(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + " div.yuimenu");
	}
	
	public WikiDashboardAddCommentDialog addComment() {
		getAddCommentLinkMenuItem().click();
		return new WikiDashboardAddCommentDialog(getCodebeamerPage());
	}

	protected CodebeamerLocator getAddCommentLinkMenuItem() {
		return this.locator("a[onclick*='/proj/wiki/editComment.spr']");
	}

	@Override
	public WikiDashboardMoreActionMenuAssertions assertThat() {
		return new WikiDashboardMoreActionMenuAssertions(this);
	}
}
