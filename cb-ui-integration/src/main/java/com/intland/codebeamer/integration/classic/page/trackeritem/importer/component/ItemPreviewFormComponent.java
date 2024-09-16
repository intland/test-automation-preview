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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ItemPreviewFormComponent
		extends AbstractCodebeamerComponent<ItemPreviewFormComponent, ItemPreviewFormAssertions> {

	public ItemPreviewFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#importForm");
	}

	@Override
	public ItemPreviewFormAssertions assertThat() {
		return new ItemPreviewFormAssertions(this);
	}

	public ItemPreviewFormComponent fillTagWorkItems(String tag) {
		getTagWorkItemsLocator().fill(tag);
		return this;
	}

	public CodebeamerLocator getTagWorkItemsLocator() {
		return this.locator("input#tag");
	}

	public CodebeamerLocator getFinishLocator() {
		return this.locator("input#finishButton");
	}

	public CodebeamerLocator getBackLocator() {
		return this.locator("input#_eventId_back");
	}

	public CodebeamerLocator getCancelLocator() {
		return this.locator("input#_eventId_cancel");
	}
}
