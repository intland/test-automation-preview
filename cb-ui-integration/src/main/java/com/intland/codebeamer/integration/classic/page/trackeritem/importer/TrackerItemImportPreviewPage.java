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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer;

import static com.intland.codebeamer.integration.classic.page.trackeritem.importer.AssignColumnPage.IMPORT_ISSUE_PAGE_PATTERN;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.ItemPreviewFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.ItemPreviewFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemImportPreviewPage")
public class TrackerItemImportPreviewPage extends AbstractCodebeamerPage<TrackerItemImportPreviewPage> {

	@Component("Import preview")
	private final ItemPreviewFormComponent itemPreviewFormComponent;

	public TrackerItemImportPreviewPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.itemPreviewFormComponent = new ItemPreviewFormComponent(codebeamerPage);
	}

	@Override
	public TrackerItemImportPreviewPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Import preview page should be the active page");
		return this;
	}

	public TrackerItemImportPreviewPage itemPreviewFormComponent(
			Consumer<ItemPreviewFormComponent> formConsumer) {
		formConsumer.accept(itemPreviewFormComponent);
		return this;
	}

	public TrackerItemImportPreviewPage assertItemPreviewFormComponent(
			Consumer<ItemPreviewFormAssertions> assertions) {
		assertions.accept(itemPreviewFormComponent.assertThat());
		return this;
	}

	public void finish() {
		//todo navigate to tracker item list page, could be table or doc view
		itemPreviewFormComponent.getFinishLocator().click();
	}
}
