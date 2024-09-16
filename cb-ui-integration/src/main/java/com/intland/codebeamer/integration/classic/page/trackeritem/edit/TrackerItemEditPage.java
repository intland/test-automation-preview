package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.FooterComponent;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarAssertions;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemEditPage")
public class TrackerItemEditPage extends AbstractCodebeamerPage<TrackerItemEditPage> {

	private static final String TRACKER_ITEM_EDIT_PAGE_URL = "/issue/%s/edit";
	
	private TrackerItemId trackerItemId;

	@Component("Action menu bar")
	private final ActionMenuBarComponent trackerItemActionMenuBarComponent;

	@Component("Edit tracker item form")
	private TrackerItemFieldEditFormComponent trackerItemFieldEditFormComponent;

	@Component("Footer")
	private final FooterComponent footerComponent;

	private TrackerItemEditPageNavigation trackerItemEditPageNavigation;
	
	public TrackerItemEditPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		this(codebeamerPage, trackerId, trackerItemId, null);
	}

	public TrackerItemEditPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId, String frameLocator) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.trackerItemActionMenuBarComponent = new ActionMenuBarComponent(codebeamerPage);
		this.trackerItemFieldEditFormComponent = new TrackerItemFieldEditFormComponent(codebeamerPage, frameLocator);
		this.trackerItemEditPageNavigation = new TrackerItemEditPageNavigation(codebeamerPage, trackerId, trackerItemId);
		this.footerComponent = new FooterComponent(codebeamerPage);
	}

	@Action("Save")
	public TrackerItemEditPageNavigation save() {
		this.trackerItemFieldEditFormComponent.getSaveButton().click();
		return trackerItemEditPageNavigation;
	}

	@Action("Visit")
	public TrackerItemEditPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public TrackerItemEditPage isActive() {
		return this;
	}

	public TrackerItemEditPage assertActionMenuBarComponent(Consumer<ActionMenuBarAssertions> assertion) {
		assertion.accept(trackerItemActionMenuBarComponent.assertThat());
		return this;
	}

	public TrackerItemEditPage fieldFormComponent(Consumer<TrackerItemFieldEditFormComponent> formConsumer) {
		formConsumer.accept(trackerItemFieldEditFormComponent);
		return this;
	}

	public TrackerItemEditPage assertFieldFormComponent(Consumer<TrackerItemFieldEditFormAssertions> assertion) {
		assertion.accept(trackerItemFieldEditFormComponent.assertThat());
		return this;
	}

	public TrackerItemEditPage footerComponent(Consumer<FooterComponent> componentConsumer) {
		componentConsumer.accept(footerComponent);
		return this;
	}
	
	private String formatPageUrl() {
		return TRACKER_ITEM_EDIT_PAGE_URL.formatted(Integer.valueOf(trackerItemId.id()));
	}
	
}
