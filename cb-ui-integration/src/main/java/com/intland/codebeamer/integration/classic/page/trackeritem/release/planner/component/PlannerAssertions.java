package com.intland.codebeamer.integration.classic.page.trackeritem.release.planner.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class PlannerAssertions extends AbstractCodebeamerComponentAssert<PlannerComponent, PlannerAssertions> {
	protected PlannerAssertions(PlannerComponent component) {
		super(component);
	}

	public PlannerAssertions openInTableViewIsNotVisible() {
		return assertAll("Open in Table view link should not be visible",
				() -> assertThat(getComponent().getOpenInTableViewLink()).isHidden());
	}

}
