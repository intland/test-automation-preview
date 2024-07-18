package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemCreatePage")
public class TrackerItemCreatePage extends AbstractCodebeamerPage<TrackerItemCreatePage> {

	private static final String TRACKER_ITEM_CREATE_PAGE_URL = "/tracker/%s/create";
	
	private Tracker tracker;

	private TrackerItemCreatePageNavigation trackerItemCreatePageNavigation;

	private TrackerItemFieldCreateFormComponent trackerItemFieldCreateFormComponent;

	public TrackerItemCreatePage(DataManagerService dataManagerService, CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.trackerItemCreatePageNavigation = new TrackerItemCreatePageNavigation(dataManagerService,codebeamerPage, tracker);
		this.trackerItemFieldCreateFormComponent = new TrackerItemFieldCreateFormComponent(dataManagerService, codebeamerPage, tracker);
	}

	@Action("Visit")
	public TrackerItemCreatePage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public TrackerItemCreatePage isActive() {
		// assertUrl(formatPageUrl(), "Trackers page should be the active page");
		return this;
	}
	
	public TrackerItemCreatePageNavigation save() {
		this.trackerItemFieldCreateFormComponent.getSaveButton().click();
		return trackerItemCreatePageNavigation;
	}
	
	public TrackerItemCreatePage fieldFormComponent(Consumer<TrackerItemFieldCreateFormComponent> formConsumer) {
		formConsumer.accept(trackerItemFieldCreateFormComponent);
		return this;
	}

	public TrackerItemCreatePage assertFieldFormComponent(Consumer<TrackerItemFieldCreateFormAssertions> assertion) {
		assertion.accept(trackerItemFieldCreateFormComponent.assertThat());
		return this;
	}
	
	private String formatPageUrl() {
		return TRACKER_ITEM_CREATE_PAGE_URL.formatted(Integer.valueOf(tracker.id().id()));
	}

}
