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

package com.intland.codebeamer.integration.classic.page.wiki.comment.component;

import java.nio.file.Path;
import java.util.function.Function;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.test.testdata.CopiedFile;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class WikiDashboardAddCommentDialogFormComponent
		extends AbstractCodebeamerComponent<WikiDashboardAddCommentDialogFormComponent, WikiDashboardAddCommentDialogFormAssertions> {

	@Component(value = "Wikitext edit", includeInSitemap = false)
	private final FroalaComponent froala;
	
	public WikiDashboardAddCommentDialogFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form#documentCommentForm");
		this.froala = new FroalaComponent(codebeamerPage, frameLocator, getSelector());
	}

	public WikiDashboardAddCommentDialogFormComponent setComment(String text) {
		this.froala.fill(text);
		return this;
	}

	public WikiDashboardAddCommentDialogFormComponent setComment(Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		this.froala.fill(markupBuilder.apply(new WikiMarkupBuilder()).build(), FroalaComponent.Type.MARKUP);
		return this;
	}

	public WikiDashboardAddCommentDialogFormComponent addAttachment(CopiedFile attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public WikiDashboardAddCommentDialogFormComponent addAttachment(Path attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public WikiDashboardAddCommentDialogFormComponent save() {
		this.collectRequests(() ->  this.froala.getSaveButton().click())
			.assertUrlCallExecuted("/wysiwyg/convertHTMLToWiki.spr");
		return this;
	}

	@Override
	public WikiDashboardAddCommentDialogFormAssertions assertThat() {
		return new WikiDashboardAddCommentDialogFormAssertions(this);
	}
}
