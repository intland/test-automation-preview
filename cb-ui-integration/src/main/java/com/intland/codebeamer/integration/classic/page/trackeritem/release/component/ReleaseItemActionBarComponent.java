package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseItemActionBarComponent extends AbstractCodebeamerComponent<ReleaseItemActionBarComponent, ReleaseItemActionBarAssertions> {

	private static final String BASELINES_AND_BRANCHES_BUTTON_TITLE = "Baselines and Branches";

	public ReleaseItemActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.actionBar");
	}

	public CodebeamerLocator getBaselinesandBranchesButton() {
		return this.locator("a.actionLink[title='%s']".formatted(BASELINES_AND_BRANCHES_BUTTON_TITLE));
	}

	@Override
	public ReleaseItemActionBarAssertions assertThat() {
		return new ReleaseItemActionBarAssertions(this);
	}
}
