package com.intland.codebeamer.integration.classic.page.trackeritem.release.planner;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.planner.component.PlannerComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("Release planner page")
public class ReleasePlannerPage extends AbstractCodebeamerPage<ReleasePlannerPage> {

	private static final String PATH = "item/%s/planner?openProductBacklog=true";

	private final TrackerItemId trackerItemId;

	@Component("Action bar")
	private TrackerItemActionBarComponent actionBarComponent;

	@Component("Planner")
	private PlannerComponent plannerComponent;

	public ReleasePlannerPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.actionBarComponent = new TrackerItemActionBarComponent(codebeamerPage, trackerId, trackerItemId);
		this.plannerComponent = new PlannerComponent(codebeamerPage);
	}

	@Action("Visit")
	public ReleasePlannerPage visit() {
		navigate(getUrl());
		return isActive();
	}

	@Override
	public ReleasePlannerPage isActive() {
		assertUrl(getUrl(), "Release page should be the active page");
		return this;
	}

	public TrackerItemActionBarComponent actionBarComponent() {
		return actionBarComponent;
	}

	public ReleasePlannerPage actionBarComponent(Consumer<TrackerItemActionBarComponent> actionBarComponentConsumer) {
		actionBarComponentConsumer.accept(actionBarComponent);
		return this;
	}

	public PlannerComponent plannerComponent() {
		return plannerComponent;
	}

	private String getUrl() {
		return PATH.formatted(trackerItemId.id());
	}
}
