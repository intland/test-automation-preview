package com.intland.codebeamer.integration.classic.page.trackeritem.release.kanban.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class KanbanBoardComponent extends AbstractCodebeamerComponent<KanbanBoardComponent, KanbanBoardAssertions> {

	public KanbanBoardComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#userStories");
	}

	public CodebeamerLocator getOpenInTableViewLink() {
		return locator(".drillDownLink");
	}

	@Override
	public KanbanBoardAssertions assertThat() {
		return new KanbanBoardAssertions(this);
	}
}
