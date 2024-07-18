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

package com.intland.codebeamer.integration.classic.page.tracker.main;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.tracker.main.component.TrackerTreeComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackersPage")
public class TrackersPage extends AbstractCodebeamerPage<TrackersPage> {

	private static final String TRACKERS_PAGE_URL = "project/%s/tracker";

	private final Project project;

	private TrackerTreeComponent trackerTreeComponent;

	public TrackersPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.trackerTreeComponent = new TrackerTreeComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public TrackersPage visit() {
		navigate(formatTrackersPageUrl());
		return isActive();
	}

	@Override
	public TrackersPage isActive() {
		assertUrl(formatTrackersPageUrl(), "Trackers page should be the active page");
		return this;
	}

	public TrackerTreeComponent getTrackerTreeComponent() {
		return trackerTreeComponent;
	}

	public TrackersPage trackerTreeComponent(Consumer<TrackerTreeComponent> trackerTreeComponentConsumer) {
		trackerTreeComponentConsumer.accept(trackerTreeComponent);
		return this;
	}

	private String formatTrackersPageUrl() {
		return TRACKERS_PAGE_URL.formatted(Integer.valueOf(project.id().id()));
	}
}
