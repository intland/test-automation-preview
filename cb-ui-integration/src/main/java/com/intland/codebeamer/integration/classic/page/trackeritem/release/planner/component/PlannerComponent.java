package com.intland.codebeamer.integration.classic.page.trackeritem.release.planner.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class PlannerComponent extends AbstractCodebeamerComponent<PlannerComponent, PlannerAssertions> {

	public PlannerComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#plannerCenterPane");
	}

	public CodebeamerLocator getOpenInTableViewLink() {
		return locator(".drillDownLink");
	}

	@Override
	public PlannerAssertions assertThat() {
		return new PlannerAssertions(this);
	}
}
