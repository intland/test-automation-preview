package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class TestRunTrackerSelectorDialog extends AbstractCodebeamerDialog {

	@Component("Test run tracker selector form")
	private final TestRunTrackerSelectorFormComponent testRunTrackerSelectorFormComponent;

	public TestRunTrackerSelectorDialog(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "iframe[src*='assignTestCasesToTestRunTrackers.spr']");
		this.testRunTrackerSelectorFormComponent = new TestRunTrackerSelectorFormComponent(getCodebeamerPage(),
				getDialogLocator(), tracker);
	}

	public TestRunTrackerSelectorFormComponent getTestRunTrackerSelectorFormComponent() {
		return testRunTrackerSelectorFormComponent;
	}

	public TestRunTrackerSelectorDialog testRunTrackerSelectorFormComponent(
			Consumer<TestRunTrackerSelectorFormComponent> testRunTrackerSelectorFormComponentConsumer) {
		testRunTrackerSelectorFormComponentConsumer.accept(testRunTrackerSelectorFormComponent);
		return this;
	}
}
