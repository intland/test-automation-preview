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

package com.intland.codebeamer.integration.classic.page.tracker.view.document.center;

import java.util.function.Function;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerDocumentViewTableRowComponent extends
		AbstractCodebeamerComponent<TrackerDocumentViewTableRowComponent, TrackerDocumentViewTableRowAssertions> {

	@Component(value = "Wikitext edit", includeInSitemap = false)
	private final FroalaComponent froalaComponent;

	protected TrackerDocumentViewTableRowComponent(CodebeamerPage codebeamerPage, String trackerItemName) {
		super(codebeamerPage, "tr:has(span:has-text('%s'))".formatted(trackerItemName));
		this.froalaComponent = new FroalaComponent(codebeamerPage, "");
	}

	public CodebeamerLocator openContextMenu() {
		return this.locator(getSelector("a.displayTitleWrapper")).click();
	}

	public CodebeamerLocator showUpstreamReferences() {
		return this.locator(getSelector("div.upstreamRelations")).hover();
	}

	public CodebeamerLocator showDownstreamReferences() {
		return this.locator(getSelector("div.downstreamRelations")).hover();
	}

	public TrackerDocumentViewTableRowComponent edit() {
		this.locator("div.edit-overlay-container").hover();
		this.locator("div.edit-description").click();
		return this;
	}

	public TrackerDocumentViewTableRowComponent setName(String newName) {
		this.edit();
		this.locator("input.nested-summary-editor").clear().fill(newName);
		return this;
	}

	public TrackerDocumentViewTableRowComponent setDescription(String description) {
		this.edit();
		froalaComponent.fill(description, FroalaComponent.Type.RICH_TEXT);
		froalaComponent.getSaveButton().click();
		return this;
	}

	public TrackerDocumentViewTableRowComponent setDescription(Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		this.edit();
		froalaComponent.fill(markupBuilder.apply(new WikiMarkupBuilder()).build());
		froalaComponent.getSaveButton().click();
		return this;
	}

	public CodebeamerLocator getDescriptionInput() {
		return this.locator("span.wikiContent");
	}

	public CodebeamerLocator getNameInput() {
		return this.locator(".name span.no-numbering");
	}

	@Override
	public TrackerDocumentViewTableRowAssertions assertThat() {
		return new TrackerDocumentViewTableRowAssertions(this);
	}
}
