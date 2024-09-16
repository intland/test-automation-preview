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

package com.intland.codebeamer.integration.classic.page.tracker.view.document;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerLayout;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.actionbar.DocumentViewCenterActionBarComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.actionbar.DocumentViewLeftActionBarComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.center.TrackerDocumentViewTableComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.leftpane.TrackerItemTreeComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.rightpane.TrackerDocumentViewRightPaneComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerDocumentViewPage")
public class TrackerDocumentViewPage extends AbstractCodebeamerPage<TrackerDocumentViewPage> {

	private static final String TRACKER_DOCUMENT_VIEW_PAGE_URL =
			"tracker/%s?layout_name=" + TrackerLayout.DOCUMENT.name().toLowerCase();

	private final Tracker tracker;

	@Component("Overlay Messages")
	private final OverlayMessageBoxComponent overlayMessageBoxComponent;

	@Component("Tracker action bar")
	private final DocumentViewLeftActionBarComponent leftActionBarComponent;

	@Component("Center action bar")
	private final DocumentViewCenterActionBarComponent centerActionBarComponent;

	@Component("TrackerItem tree")
	private final TrackerItemTreeComponent trackerItemTreeComponent;

	@Component("Tracker document view table")
	private final TrackerDocumentViewTableComponent trackerDocumentViewTableComponent;

	@Component("Tracker document view right pane")
	private final TrackerDocumentViewRightPaneComponent trackerDocumentViewRightPaneComponent;

	public TrackerDocumentViewPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.leftActionBarComponent = new DocumentViewLeftActionBarComponent(codebeamerPage, tracker);
		this.centerActionBarComponent = new DocumentViewCenterActionBarComponent(codebeamerPage, tracker);
		this.trackerItemTreeComponent = new TrackerItemTreeComponent(codebeamerPage);
		this.trackerDocumentViewTableComponent = new TrackerDocumentViewTableComponent(codebeamerPage);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.trackerDocumentViewRightPaneComponent = new TrackerDocumentViewRightPaneComponent(codebeamerPage);
	}

	@Action("Visit")
	public TrackerDocumentViewPage visit() {
		navigate(formatTrackerDocumentViewPageUrl());
		return isActive();
	}

	@Override
	public TrackerDocumentViewPage isActive() {
		assertUrl(formatTrackerDocumentViewPageUrl(), "Tracker document view page should be the active page");
		return this;
	}

	public DocumentViewLeftActionBarComponent leftActionBarComponent() {
		return this.leftActionBarComponent;
	}

	public DocumentViewCenterActionBarComponent centerActionBarComponent() {
		return this.centerActionBarComponent;
	}

	public TrackerDocumentViewPage trackerItemTreeComponent(Consumer<TrackerItemTreeComponent> trackerItemTreeComponentConsumer) {
		trackerItemTreeComponentConsumer.accept(trackerItemTreeComponent);
		return this;
	}

	public TrackerDocumentViewPage trackerDocumentViewTableComponent(
			Consumer<TrackerDocumentViewTableComponent> trackerDocumentViewTableComponentConsumer) {
		trackerDocumentViewTableComponentConsumer.accept(trackerDocumentViewTableComponent);
		return this;
	}

	public TrackerDocumentViewPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public OverlayMessageBoxComponent getOverlayMessageBoxComponent() {
		return overlayMessageBoxComponent;
	}

	public TrackerDocumentViewPage rightPaneComponent(
			Consumer<TrackerDocumentViewRightPaneComponent> rightPaneConsumer) {
		rightPaneConsumer.accept(trackerDocumentViewRightPaneComponent);
		return this;
	}

	public TrackerDocumentViewRightPaneComponent rightPaneComponent() {
		return trackerDocumentViewRightPaneComponent;
	}

	private String formatTrackerDocumentViewPageUrl() {
		return TRACKER_DOCUMENT_VIEW_PAGE_URL.formatted(Integer.valueOf(tracker.id().id()));
	}
}
