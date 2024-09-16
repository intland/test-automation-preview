package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleaseItemActionBarAssertions
		extends AbstractCodebeamerComponentAssert<ReleaseItemActionBarComponent, ReleaseItemActionBarAssertions> {

	protected ReleaseItemActionBarAssertions(ReleaseItemActionBarComponent component) {
		super(component);
	}

	public ReleaseItemActionBarAssertions hasBaselinesAndBranchesButton() {
		return assertAll("Baselines and Branches button should be present on action bar",
				() -> assertThat(getComponent().getBaselinesandBranchesButton()).isVisible());
	}
}
