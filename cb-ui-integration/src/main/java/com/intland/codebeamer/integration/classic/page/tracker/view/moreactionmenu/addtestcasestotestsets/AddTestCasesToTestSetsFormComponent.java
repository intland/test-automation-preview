package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class AddTestCasesToTestSetsFormComponent
		extends AbstractCodebeamerComponent<AddTestCasesToTestSetsFormComponent, AddTestCasesToTestSetsAssertions> {

	@Component("Test set history tab")
	private final TestSetHistoryTabComponent testSetHistoryTabComponent;

	@Component("Find test sets tab")
	private final FindTestSetsTabComponent findTestSetsTabComponent;

	private final Tracker tracker;

	public AddTestCasesToTestSetsFormComponent(CodebeamerPage codebeamerPage, String frameLocator, Tracker tracker) {
		super(codebeamerPage, frameLocator, "#command");
		this.testSetHistoryTabComponent = new TestSetHistoryTabComponent(getCodebeamerPage(), frameLocator);
		this.findTestSetsTabComponent = new FindTestSetsTabComponent(getCodebeamerPage(), frameLocator);
		this.tracker = tracker;
	}

	@Override
	public AddTestCasesToTestSetsAssertions assertThat() {
		return new AddTestCasesToTestSetsAssertions(this);
	}

	public TestSetHistoryTabComponent getTestSetHistoryTabComponent() {
		return testSetHistoryTabComponent;
	}

	public AddTestCasesToTestSetsFormComponent testSetHistoryTabComponent(
			Consumer<TestSetHistoryTabComponent> testSetHistoryTabComponentConsumer) {
		testSetHistoryTabComponentConsumer.accept(testSetHistoryTabComponent);
		return this;
	}

	public AddTestCasesToTestSetsFormComponent findTestSetsTabComponent(
			Consumer<FindTestSetsTabComponent> findTestSetsTabComponentConsumer) {
		findTestSetsTabComponentConsumer.accept(findTestSetsTabComponent);
		return this;
	}

	public TestSetHistoryTabComponent openTestSetHistoryTab() {
		this.testSetHistoryTabComponent.activateTab();
		return this.testSetHistoryTabComponent;
	}

	public AddTestCasesToTestSetsFormComponent openTestSetHistoryTab(
			Consumer<TestSetHistoryTabComponent> testSetHistoryTabComponentConsumer) {
		this.testSetHistoryTabComponent.activateTab();
		testSetHistoryTabComponentConsumer.accept(testSetHistoryTabComponent);
		return this;
	}

	public AddTestCasesToTestSetsFormComponent openTestSetHistoryTab(
			Consumer<TestSetHistoryTabComponent> testSetHistoryTabComponentConsumer, double timeout) {
		this.testSetHistoryTabComponent.activateTab(timeout);
		testSetHistoryTabComponentConsumer.accept(testSetHistoryTabComponent);
		return this;
	}

	public FindTestSetsTabComponent getFindTestSetsTabComponent() {
		return findTestSetsTabComponent;
	}

	public CodebeamerLocator getSubmitButton() {
		return locator("input[type='submit'][name='addToExistingSet']");
	}

	public AddTestCasesToTestSetsDialog submit() {
		getSubmitButton().click();
		this.waitForResponse(urlEndsWith("assignTestCasesToSets.spr"), 200, 30000);
		return new AddTestCasesToTestSetsDialog(getCodebeamerPage(), tracker);
	}
}
