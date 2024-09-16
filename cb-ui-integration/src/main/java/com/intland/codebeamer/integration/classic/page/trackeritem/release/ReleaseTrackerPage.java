package com.intland.codebeamer.integration.classic.page.trackeritem.release;

import java.util.Objects;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarAssertions;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarComponent;
import com.intland.codebeamer.integration.classic.page.tracker.actionbar.TrackerActionBarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleasesRightPanelComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ReleaseTrackerPage")
public class ReleaseTrackerPage extends AbstractCodebeamerPage<ReleaseTrackerPage> {

	private static final String PATH = "category/%d";

	private static final String PATH_WITH_REVISION = "category/%d?revision=%d";

	private final Tracker tracker;

	private final Baseline baseline;

	@Component("Action menu bar")
	private final ActionMenuBarComponent actionMenuBarComponent;

	@Component("Action bar")
	private final TrackerActionBarComponent actionBarComponent;

	@Component("Release dashboard listing releases")
	private final ReleaseDashboardComponent releaseDashboardComponent;

	@Component("Right panel")
	private final ReleasesRightPanelComponent releasesRightPanelComponent;

	public ReleaseTrackerPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		this(codebeamerPage, tracker, null);
	}

	public ReleaseTrackerPage(CodebeamerPage codebeamerPage, Tracker tracker, Baseline baseline) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.baseline = baseline;
		this.actionMenuBarComponent = new ActionMenuBarComponent(getCodebeamerPage());
		this.actionBarComponent = new TrackerActionBarComponent(getCodebeamerPage(), tracker);
		this.releaseDashboardComponent = new ReleaseDashboardComponent(getCodebeamerPage(), tracker, baseline);
		this.releasesRightPanelComponent = new ReleasesRightPanelComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ReleaseTrackerPage visit() {
		navigate(getUrl());
		return isActive();
	}

	@Override
	public ReleaseTrackerPage isActive() {
		assertUrl(getUrl(), "Releases page should be the active page");
		return this;
	}

	public ReleaseTrackerPage releaseDashboardComponent(Consumer<ReleaseDashboardComponent> formConsumer) {
		formConsumer.accept(releaseDashboardComponent);
		return this;
	}

	public ReleaseDashboardComponent getReleaseDashboardComponent() {
		return releaseDashboardComponent;
	}

	public ReleaseTrackerPage assertReleaseDashboardComponent(Consumer<ReleaseDashboardAssertions> formConsumer) {
		formConsumer.accept(releaseDashboardComponent.assertThat());
		return this;
	}

	public ReleaseTrackerPage actionBarComponent(Consumer<TrackerActionBarComponent> formConsumer) {
		formConsumer.accept(actionBarComponent);
		return this;
	}

	public ReleaseTrackerPage releasesRightPanelComponent(Consumer<ReleasesRightPanelComponent> formConsumer) {
		formConsumer.accept(releasesRightPanelComponent);
		return this;
	}

	public ReleaseTrackerPage assertActionMenuBar(Consumer<ActionMenuBarAssertions> formConsumer) {
		formConsumer.accept(actionMenuBarComponent.assertThat());
		return this;
	}

	public ReleaseTrackerPage switchToBaselineMode(Baseline baseline) {
		return new ReleaseTrackerPage(getCodebeamerPage(), tracker, baseline).visit();
	}

	public ReleasePage navigateToReleaseItem(TrackerItemId trackerItemId) {
		return new ReleasePage(getCodebeamerPage(), trackerItemId).visit();
	}

	private String getUrl() {
		return Objects.isNull(baseline)
				? PATH.formatted(tracker.id().id())
				: PATH_WITH_REVISION.formatted(tracker.id().id(), baseline.id().id());
	}
}
