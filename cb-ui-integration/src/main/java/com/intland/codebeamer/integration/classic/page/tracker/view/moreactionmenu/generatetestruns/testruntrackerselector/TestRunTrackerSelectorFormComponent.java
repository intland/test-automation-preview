package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.GenerateTestRunsDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TestRunTrackerSelectorFormComponent extends
		AbstractCodebeamerComponent<TestRunTrackerSelectorFormComponent, TestRunTrackerSelectorFormAssertions> {

	private static final String FORM_SELECTOR = "#command";

	@Component("Test run trackers history tab")
	private final TestRunTrackersHistoryTab testRunTrackersHistoryTab;

	@Component("Find test run trackers tab")
	private final FindTestRunTrackersTab findTestRunTrackersTab;

	private final Tracker tracker;

	public TestRunTrackerSelectorFormComponent(CodebeamerPage codebeamerPage, String frameLocator, Tracker tracker) {
		super(codebeamerPage, frameLocator, FORM_SELECTOR);
		this.testRunTrackersHistoryTab = new TestRunTrackersHistoryTab(codebeamerPage, frameLocator);
		this.findTestRunTrackersTab = new FindTestRunTrackersTab(codebeamerPage, frameLocator);
		this.tracker = tracker;
	}

	@Override
	public TestRunTrackerSelectorFormAssertions assertThat() {
		return new TestRunTrackerSelectorFormAssertions(this);
	}

	public TestRunTrackerSelectorFormComponent testRunTrackersHistoryTab(
			Consumer<TestRunTrackersHistoryTab> testRunTrackersTabConsumer) {
		testRunTrackersTabConsumer.accept(testRunTrackersHistoryTab);
		return this;
	}

	public TestRunTrackersHistoryTab getTestRunTrackersHistoryTab() {
		return testRunTrackersHistoryTab;
	}

	public CodebeamerLocator getSubmitButton() {
		return locator("input[type='submit'][name='generateTestRun']");
	}

	public GenerateTestRunsDialog submit() {
		getSubmitButton().click();
		return new GenerateTestRunsDialog(getCodebeamerPage(), tracker, "iframe[src*='assignTestCasesToTestRunTrackers.spr']");
	}
}
