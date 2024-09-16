package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class TestRunTrackersHistoryTab
		extends AbstractTabComponent<TestRunTrackersHistoryTab, TestRunTrackersHistoryTabAssertions> {

	@Component("Test run trackers history table")
	private final TestRunTrackersHistoryTableComponent testRunTrackersHistoryTableComponent;

	public TestRunTrackersHistoryTab(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, new TabId("#historyTab"));
		this.testRunTrackersHistoryTableComponent = new TestRunTrackersHistoryTableComponent(codebeamerPage, frameLocator);
	}

	@Override
	public TestRunTrackersHistoryTabAssertions assertThat() {
		return new TestRunTrackersHistoryTabAssertions(this);
	}

	public TestRunTrackersHistoryTableComponent getTestRunTrackersHistoryTableComponent() {
		return testRunTrackersHistoryTableComponent;
	}

	public TestRunTrackersHistoryTab testRunTrackersHistoryTableComponent(
			Consumer<TestRunTrackersHistoryTableComponent> testRunTrackersHistoryTableComponentConsumer) {
		testRunTrackersHistoryTableComponentConsumer.accept(testRunTrackersHistoryTableComponent);
		return this;
	}
}
