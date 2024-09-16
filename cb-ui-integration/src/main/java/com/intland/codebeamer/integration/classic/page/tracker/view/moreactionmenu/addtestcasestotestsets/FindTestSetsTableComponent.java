package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FindTestSetsTableComponent
		extends AbstractCodebeamerComponent<FindTestSetsTableComponent, FindTestSetsTableAssertions> {

	public FindTestSetsTableComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#searchList");
	}

	public FindTestSetsTableComponent selectTestSet(TrackerItemId trackerItemId) {
		getTestSetCheckbox(trackerItemId).click();
		return this;
	}

	public FindTestSetsTableComponent selectTestSet(TrackerItem trackerItem) {
		return selectTestSet(trackerItem.id());
	}

	public CodebeamerLocator getTestSetCheckbox(TrackerItemId trackerItemId) {
		return locator("input.historyItemSelector[value$='%s']".formatted(trackerItemId.id()));
	}

	@Override
	public FindTestSetsTableAssertions assertThat() {
		return new FindTestSetsTableAssertions(this);
	}
}
