package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class TestSetHistoryTabComponent extends AbstractTabComponent<TestSetHistoryTabComponent, TestSetHistoryTabAssertions> {

	@Component("Test run trackers history table")
	private final TestSetHistoryTableComponent testSetHistoryTableComponent;

	public TestSetHistoryTabComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, new TabId("#historyTab"));
		this.testSetHistoryTableComponent = new TestSetHistoryTableComponent(getCodebeamerPage(), frameLocator);
	}

	public TestSetHistoryTableComponent getTestSetHistoryTableComponent() {
		return testSetHistoryTableComponent;
	}

	public TestSetHistoryTableComponent selectTestSet(TrackerItemId trackerItemId) {
		return this.testSetHistoryTableComponent.selectTestSet(trackerItemId);
	}

	@Override
	public TestSetHistoryTabAssertions assertThat() {
		return new TestSetHistoryTabAssertions(this);
	}
}
