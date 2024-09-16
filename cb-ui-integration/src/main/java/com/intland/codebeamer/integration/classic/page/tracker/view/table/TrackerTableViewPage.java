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

package com.intland.codebeamer.integration.classic.page.tracker.view.table;

import java.util.Objects;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerLayout;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarComponent;
import com.intland.codebeamer.integration.classic.component.loadingIndicator.LoadingIndicatorComponent;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemTableRowComponent;
import com.intland.codebeamer.integration.classic.page.tracker.actionbar.TrackerActionBarComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.content.TrackerTableViewContentComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerTableViewPage")
public class TrackerTableViewPage extends AbstractCodebeamerPage<TrackerTableViewPage> {

	private static final String TRACKER_TABLE_VIEW_PAGE_URL =
			"tracker/%s&layout_name=" + TrackerLayout.TABLE.name().toLowerCase();

	private static final String TRACKER_TABLE_VIEW_PAGE_URL_WITH_REVISION =
			"tracker/%s?revision=%s&layout_name=" + TrackerLayout.TABLE.name().toLowerCase();

	private final Tracker tracker;

	private final Baseline baseline;

	@Component("Tracker action menu bar")
	private final ActionMenuBarComponent actionMenuBarComponent;

	@Component("Tracker action bar")
	private final TrackerActionBarComponent trackerActionBarComponent;

	@Component(value = "Loading indicator", includeInSitemap = false)
	private final LoadingIndicatorComponent loadingIndicatorComponent;

	@Component(value = "Tracker table view content")
	private final TrackerTableViewContentComponent trackerTableViewContentComponent;

	public TrackerTableViewPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		this(codebeamerPage, tracker, null);
	}

	public TrackerTableViewPage(CodebeamerPage codebeamerPage, Tracker tracker, Baseline baseline) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.baseline = baseline;
		this.trackerActionBarComponent = new TrackerActionBarComponent(codebeamerPage, tracker);
		this.actionMenuBarComponent = new ActionMenuBarComponent(codebeamerPage);
		this.loadingIndicatorComponent = new LoadingIndicatorComponent(codebeamerPage);
		this.trackerTableViewContentComponent = new TrackerTableViewContentComponent(codebeamerPage);
	}

	@Action("Visit")
	public TrackerTableViewPage visit() {
		navigate(formatTrackerTableViewPageUrl());
		loadingIndicatorComponent.waitForDetached();
		return isActive();
	}

	@Override
	public TrackerTableViewPage isActive() {
		assertUrl(formatTrackerTableViewPageUrl(), "Tracker table view page should be the active page");
		return this;
	}

	public TrackerTableViewPage trackerItemTableRowComponent(TrackerItemId trackerItemId,
			Consumer<TrackerItemTableRowComponent> consumer) {
		consumer.accept(trackerTableViewContentComponent.getTableRow(trackerItemId));
		return this;
	}

	public TrackerTableViewPage trackerTableContentComponent(Consumer<TrackerTableViewContentComponent> consumer) {
		consumer.accept(trackerTableViewContentComponent);
		return this;
	}

	public TrackerActionBarComponent actionBarComponent() {
		return this.trackerActionBarComponent;
	}

	public ActionMenuBarComponent actionMenuBarComponent() {
		return this.actionMenuBarComponent;
	}

	private String formatTrackerTableViewPageUrl() {
		return Objects.isNull(baseline)
				? TRACKER_TABLE_VIEW_PAGE_URL.formatted(Integer.valueOf(tracker.id().id()))
				: TRACKER_TABLE_VIEW_PAGE_URL_WITH_REVISION.formatted(Integer.valueOf(tracker.id().id()),
						Integer.valueOf(baseline.id().id()));
	}

}
