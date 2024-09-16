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

package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditUrlFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.artifactlinksdialog.ArtifactLinksDialog;
import com.microsoft.playwright.Locator;

public class InlineEditUrlFieldComponent extends
		AbstractEditUrlFieldComponent<InlineEditUrlFieldComponent, InlineEditUrlFieldAssertions> {

	public InlineEditUrlFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditUrlFieldComponent editUrl(String url, String alias) {
		waitForReferenceInlineEditStart();

		getPopupButton().click();
		new ArtifactLinksDialog(getCodebeamerPage())
				.artifactLinksComponent(f -> f
						.switchToWikiLinkUrlTab()
						.setUrl(url, alias))
				.save();

		getElement(alias).waitForAttached();

		stopInlineEdit();
		return this;
	}

	public InlineEditUrlFieldComponent editUrl(TrackerItemId trackerItemIdToSet, String alias) {
		waitForReferenceInlineEditStart();

		getPopupButton().click();
		new ArtifactLinksDialog(getCodebeamerPage())
				.artifactLinksComponent(f -> f
						.switchToWikiLinkUrlTab()
						.setWikiLink(trackerItemIdToSet, alias))
				.save();

		getElement(alias).waitForAttached();

		stopInlineEdit();
		return this;
	}

	public InlineEditUrlFieldComponent remove() {
		waitForReferenceInlineEditStart();

		getUrl()
				.map(UrlFieldValueConverter.UrlFieldValue::alias)
				.ifPresent(c -> getElement(c).click());

		stopInlineEdit();
		return this;
	}

	public InlineEditUrlFieldComponent remove(String referenceName) {
		waitForReferenceInlineEditStart();
		getElement(referenceName).click();

		stopInlineEdit();
		return this;
	}

	@Override
	public InlineEditUrlFieldAssertions assertThat() {
		return new InlineEditUrlFieldAssertions(this);
	}

	@Override
	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2).setDelay(500));
		waitForReferenceInlineEditEnd();
	}

}