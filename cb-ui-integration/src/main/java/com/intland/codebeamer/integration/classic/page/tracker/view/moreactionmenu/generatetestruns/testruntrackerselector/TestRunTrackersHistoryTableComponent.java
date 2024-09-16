package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TestRunTrackersHistoryTableComponent
		extends AbstractCodebeamerComponent<TestRunTrackersHistoryTableComponent, TestRunTrackersHistoryTableAssertions> {
	public TestRunTrackersHistoryTableComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#historyList");
	}

	@Override
	public TestRunTrackersHistoryTableAssertions assertThat() {
		return new TestRunTrackersHistoryTableAssertions(this);
	}

	public TestRunTrackersHistoryTableComponent selectTracker(TrackerId trackerId) {
		getRadioInputByTrackerId(trackerId).click();
		return this;
	}

	public TestRunTrackersHistoryTableComponent selectTracker(Tracker tracker) {
		return selectTracker(tracker.id());
	}

	public CodebeamerLocator getRadioInputByTrackerId(TrackerId trackerId) {
		return locator("input.historyItemSelector[type='radio'][value$='%s']".formatted(trackerId.id()));
	}
}
