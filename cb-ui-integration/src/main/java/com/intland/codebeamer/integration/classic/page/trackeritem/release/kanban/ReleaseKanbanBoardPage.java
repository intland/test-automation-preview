package com.intland.codebeamer.integration.classic.page.trackeritem.release.kanban;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.kanban.component.KanbanBoardComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("Release kanban board page")
public class ReleaseKanbanBoardPage extends AbstractCodebeamerPage<ReleaseKanbanBoardPage> {

	private static final String PATH = "cardboard/%s";

	private TrackerItemId trackerItemId;

	@Component("Action bar")
	private TrackerItemActionBarComponent actionBarComponent;

	@Component("Kanban board")
	private KanbanBoardComponent kanbanBoardComponent;

	public ReleaseKanbanBoardPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.actionBarComponent = new TrackerItemActionBarComponent(codebeamerPage, trackerId, trackerItemId);
		this.kanbanBoardComponent = new KanbanBoardComponent(codebeamerPage);
	}

	@Action("Visit")
	public ReleaseKanbanBoardPage visit() {
		navigate(getUrl());
		return isActive();
	}

	@Override
	public ReleaseKanbanBoardPage isActive() {
		assertUrl(getUrl(), "Kanban board should be the active page");
		return this;
	}

	public TrackerItemActionBarComponent actionBarComponent() {
		return actionBarComponent;
	}

	public ReleaseKanbanBoardPage actionBarComponent(Consumer<TrackerItemActionBarComponent> actionBarComponentConsumer) {
		actionBarComponentConsumer.accept(actionBarComponent);
		return this;
	}

	public KanbanBoardComponent kanbanBoardComponent() {
		return kanbanBoardComponent;
	}

	private String getUrl() {
		return PATH.formatted(trackerItemId.id());
	}
}
