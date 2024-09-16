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

package com.intland.codebeamer.integration.classic.page.wiki.comment;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.comment.component.WikiDashboardAddCommentDialogFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class WikiDashboardAddCommentDialog extends AbstractCodebeamerDialog {

	@Component("Add wiki/dashboard comment form")
	private final WikiDashboardAddCommentDialogFormComponent wikiAddCommentDialogFormComponent;

	public WikiDashboardAddCommentDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='/proj/wiki/editComment.spr']");
		this.wikiAddCommentDialogFormComponent = new WikiDashboardAddCommentDialogFormComponent(getCodebeamerPage(), this.getDialogLocator());
	}

	public WikiDashboardAddCommentDialog wikiAddCommentForm(Consumer<WikiDashboardAddCommentDialogFormComponent> formConsumer) {
		formConsumer.accept(wikiAddCommentDialogFormComponent);
		return this;
	}

	public WikiDashboardAddCommentDialogNavigation save() {
		this.wikiAddCommentDialogFormComponent.save();
		return new WikiDashboardAddCommentDialogNavigation(getCodebeamerPage());
	}
}
