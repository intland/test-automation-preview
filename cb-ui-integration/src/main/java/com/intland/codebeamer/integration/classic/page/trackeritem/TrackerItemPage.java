package com.intland.codebeamer.integration.classic.page.trackeritem;

import java.util.Objects;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.CommentsAndAttachmentsTabAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.CommentsAndAttachmentsTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemPage")
public class TrackerItemPage extends AbstractCodebeamerPage<TrackerItemPage> {

	private final TrackerItemId trackerItemId;

	private final Baseline baseline;

	private final TrackerItemFieldFormComponent trackerItemFieldFormComponent;

	public TrackerItemPage(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		this(codebeamerPage, trackerItemId, null);
	}

	public TrackerItemPage(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.baseline = baseline;
		this.trackerItemFieldFormComponent = new TrackerItemFieldFormComponent(codebeamerPage);
	}

	@Action("Visit")
	public TrackerItemPage visit() {
		this.navigate(createUrl());
		return isActive();
	}

	@Action("Edit")
	public TrackerItemEditPage edit() {
		return new TrackerItemEditPage(getCodebeamerPage());
	}

	@Override
	public TrackerItemPage isActive() {
		getCodebeamerPage().waitForURL(createUrl());
		return this;
	}
	
	public TrackerItemPage assertFieldFormComponent(Consumer<TrackerItemFieldFormAssertions> assertion) {
		assertion.accept(trackerItemFieldFormComponent.assertThat());
		return this;
	}

	public CommentsAndAttachmentsTabComponent commentsAndAttachmentsTabComponent() {
		return new CommentsAndAttachmentsTabComponent(getCodebeamerPage(), this.trackerItemId);
	}

	public TrackerItemPage commentsAndAttachmentsTabComponent(Consumer<CommentsAndAttachmentsTabComponent> componentConsumer) {
		componentConsumer.accept(new CommentsAndAttachmentsTabComponent(getCodebeamerPage(), this.trackerItemId));
		return this;
	}

	public TrackerItemPage commentsAndAttachmentsTabAssertion(Consumer<CommentsAndAttachmentsTabAssertions> assertion) {
		assertion.accept(new CommentsAndAttachmentsTabComponent(getCodebeamerPage(), this.trackerItemId).assertThat());
		return this;
	}

	private String createUrl() {
		return Objects.isNull(baseline)
				? "/issue/%s".formatted(Integer.valueOf(trackerItemId.id()))
				: "/issue/%s?revision=%s".formatted(Integer.valueOf(trackerItemId.id()), Integer.valueOf(baseline.id().id()));
	}
}