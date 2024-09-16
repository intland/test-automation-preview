package com.intland.codebeamer.integration.classic.page.trackeritem.release;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarAssertions;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarComponent;
import com.intland.codebeamer.integration.classic.component.globalmessage.GlobalMessagesAssertions;
import com.intland.codebeamer.integration.classic.component.globalmessage.GlobalMessagesComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseDashboardComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseGanttChartComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseItemActionBarAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.component.ReleaseItemActionBarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ReleasePage")
public class ReleasePage extends AbstractCodebeamerPage<ReleasePage> {

	private static final String PATH = "item/%d/stats";
	private static final String PATH_WITH_BASELINE = "item/%d/stats?revision=%d";

	private final TrackerItemId trackerItemId;

	private Baseline baseline;

	@Component("Overlay messages")
	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	@Component("Action menu bar")
	private final ActionMenuBarComponent actionMenuBarComponent;

	@Component("Action bar")
	private final ReleaseItemActionBarComponent releasesActionBarComponent;

	@Component("Release gantt chart")
	private ReleaseGanttChartComponent releaseGanttChartComponent;

	@Component("Release dashboard listing releases")
	private ReleaseDashboardComponent releaseDashboardComponent;

	@Component("Global messages")
	private final GlobalMessagesComponent globalMessagesComponent;

	public ReleasePage(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		this(codebeamerPage, trackerItemId, null);
	}

	public ReleasePage(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.releaseGanttChartComponent = new ReleaseGanttChartComponent(getCodebeamerPage());
		this.releaseDashboardComponent = new ReleaseDashboardComponent(getCodebeamerPage());
		this.releasesActionBarComponent = new ReleaseItemActionBarComponent(getCodebeamerPage());
		this.actionMenuBarComponent = new ActionMenuBarComponent(getCodebeamerPage());
		this.globalMessagesComponent = new GlobalMessagesComponent(getCodebeamerPage());
		this.baseline = baseline;
	}

	@Action("Visit")
	public ReleasePage visit() {
		navigate(getUrl());
		return isActive();
	}

	@Override
	public ReleasePage isActive() {
		assertUrl(getUrl(), "Release page should be the active page");
		return this;
	}

	public ReleasePage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public ReleasePage releaseGanttChartComponent(Consumer<ReleaseGanttChartComponent> formConsumer) {
		formConsumer.accept(releaseGanttChartComponent);
		return this;
	}

	public ReleasePage releaseDashboardComponent(Consumer<ReleaseDashboardComponent> formConsumer) {
		formConsumer.accept(releaseDashboardComponent);
		return this;
	}

	public ReleasePage assertReleaseDashboardComponent(Consumer<ReleaseDashboardAssertions> formConsumer) {
		formConsumer.accept(releaseDashboardComponent.assertThat());
		return this;
	}

	public ReleasePage releaseActionBarComponent(Consumer<ReleaseItemActionBarComponent> formConsumer) {
		formConsumer.accept(releasesActionBarComponent);
		return this;
	}

	public ReleasePage actionMenuBarComponent(Consumer<ActionMenuBarComponent> formConsumer) {
		formConsumer.accept(actionMenuBarComponent);
		return this;
	}

	public ActionMenuBarComponent getActionMenuBarComponent() {
		return actionMenuBarComponent;
	}

	public ReleasePage assertReleaseActionBarComponent(Consumer<ReleaseItemActionBarAssertions> formConsumer) {
		formConsumer.accept(releasesActionBarComponent.assertThat());
		return this;
	}

	public ReleasePage assertGlobalMessagesComponent(Consumer<GlobalMessagesAssertions> formConsumer) {
		formConsumer.accept(globalMessagesComponent.assertThat());
		return this;
	}

	public ReleasePage assertActionMenuBarComponent(Consumer<ActionMenuBarAssertions> formConsumer) {
		formConsumer.accept(actionMenuBarComponent.assertThat());
		return this;
	}

	public ReleasePage switchToBaselineMode(Baseline baseline) {
		return new ReleasePage(getCodebeamerPage(), trackerItemId, baseline).visit();
	}

	private String getUrl() {
		return baseline == null ?
				PATH.formatted(trackerItemId.id()) :
				PATH_WITH_BASELINE.formatted(trackerItemId.id(), baseline.id().id());
	}

}
