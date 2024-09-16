package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TestSetHistoryTableComponent
		extends AbstractCodebeamerComponent<TestSetHistoryTableComponent, TestSetHistoryTableAssertions> {

	public TestSetHistoryTableComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#historyList");
	}

	@Override
	public TestSetHistoryTableAssertions assertThat() {
		return new TestSetHistoryTableAssertions(this);
	}

	public TestSetHistoryTableComponent selectTestSet(TrackerItemId trackerItemId) {
		getTestSetCheckbox(trackerItemId).click();
		return this;
	}

	public TestSetHistoryTableComponent selectTestSet(TrackerItem trackerItem) {
		return selectTestSet(trackerItem.id());
	}

	public CodebeamerLocator getTestSetCheckbox(TrackerItemId trackerItemId) {
		return locator("input.historyItemSelector[value$='%s']".formatted(trackerItemId.id()));
	}
}
