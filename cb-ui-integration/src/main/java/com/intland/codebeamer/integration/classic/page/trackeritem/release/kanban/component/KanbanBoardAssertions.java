package com.intland.codebeamer.integration.classic.page.trackeritem.release.kanban.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class KanbanBoardAssertions extends AbstractCodebeamerComponentAssert<KanbanBoardComponent, KanbanBoardAssertions> {
	protected KanbanBoardAssertions(KanbanBoardComponent component) {
		super(component);
	}

	public KanbanBoardAssertions openInTableViewIsNotVisible() {
		return assertAll("Open in Table view link should not be visible",
				() -> assertThat(getComponent().getOpenInTableViewLink()).isHidden());
	}

}
