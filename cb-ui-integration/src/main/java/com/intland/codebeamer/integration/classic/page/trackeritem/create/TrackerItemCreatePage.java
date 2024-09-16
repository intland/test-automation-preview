package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemCreatePage")
public class TrackerItemCreatePage extends AbstractCodebeamerPage<TrackerItemCreatePage> {

	private static final String TRACKER_ITEM_CREATE_PAGE_URL = "/tracker/%s/create";
	
	private TrackerId trackerId;

	private TrackerItemCreatePageNavigation trackerItemCreatePageNavigation;

	@Component("Edit fields")
	private TrackerItemFieldEditFormComponent trackerItemFieldFormComponent;

	public TrackerItemCreatePage(CodebeamerPage codebeamerPage, TrackerId trackerId) {
		this(codebeamerPage, trackerId, null);
	}

	public TrackerItemCreatePage(CodebeamerPage codebeamerPage, TrackerId trackerId, String frameLocator) {
		super(codebeamerPage);
		this.trackerId = trackerId;
		this.trackerItemCreatePageNavigation = new TrackerItemCreatePageNavigation(codebeamerPage, trackerId, frameLocator);
		this.trackerItemFieldFormComponent = new TrackerItemFieldEditFormComponent(codebeamerPage, frameLocator);
	}

	@Action("Visit")
	public TrackerItemCreatePage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public TrackerItemCreatePage isActive() {
		return this;
	}
	
	public TrackerItemCreatePageNavigation save() {
		this.trackerItemFieldFormComponent.getSaveButton().click();
		return trackerItemCreatePageNavigation;
	}
	
	public TrackerItemCreatePage fieldFormComponent(Consumer<TrackerItemFieldEditFormComponent> formConsumer) {
		formConsumer.accept(trackerItemFieldFormComponent);
		return this;
	}

	public TrackerItemCreatePage assertFieldFormComponent(Consumer<TrackerItemFieldEditFormAssertions> assertion) {
		assertion.accept(trackerItemFieldFormComponent.assertThat());
		return this;
	}
	
	private String formatPageUrl() {
		return TRACKER_ITEM_CREATE_PAGE_URL.formatted(Integer.valueOf(trackerId.id()));
	}

}
