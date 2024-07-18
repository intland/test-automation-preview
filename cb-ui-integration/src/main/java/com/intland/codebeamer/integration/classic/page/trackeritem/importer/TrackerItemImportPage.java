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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.TrackerItemImportFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.TrackerItemImportFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemImportPage")
public class TrackerItemImportPage extends AbstractCodebeamerPage<TrackerItemImportPage> {

	private static final String TRACKER_ITEM_IMPORT_PAGE_URL = "/importIssue.spr?tracker_id=%s";

	private final Tracker tracker;

	private final TrackerItemImportFormComponent trackerItemImportFormComponent;

	private final OverlayMessageBoxComponent overlayMessageBoxComponent;

	public TrackerItemImportPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.trackerItemImportFormComponent = new TrackerItemImportFormComponent(codebeamerPage);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public TrackerItemImportPage visit() {
		navigate(formatUrl());
		return isActive();
	}

	@Override
	public TrackerItemImportPage isActive() {
		assertUrl(formatUrl(), "Tracker item import page should be the active page");
		return this;
	}

	public TrackerItemImportPage importFormComponent(Consumer<TrackerItemImportFormComponent> formConsumer) {
		formConsumer.accept(trackerItemImportFormComponent);
		return this;
	}

	public TrackerItemImportPage assertImportFormComponent(Consumer<TrackerItemImportFormAssertions> assertion) {
		assertion.accept(trackerItemImportFormComponent.assertThat());
		return this;
	}

	public TrackerItemImportPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	private String formatUrl() {
		return TRACKER_ITEM_IMPORT_PAGE_URL.formatted(Integer.valueOf(tracker.id().id()));
	}
}
