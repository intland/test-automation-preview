package com.intland.codebeamer.integration.classic.component.field.read;

import java.util.Optional;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TestRunResultsPluginComponent
		extends AbstractCodebeamerComponent<TestRunResultsPluginComponent, TestRunResultsPluginAssertions> {

	private static final Integer ALL_PAGES_ID = -2;

	public TestRunResultsPluginComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".testRunResultsPlugin");
	}

	public CodebeamerLocator getTestCaseLinks() {
		return this.locator("table.testCaseRunsExecutedSummary tbody tr a[href*='/item/']");
	}

	public CodebeamerLocator getFilterButton() {
		return this.locator("input.actionButton[type='submit'][onclick*='reloadFiltered()']");
	}

	public TestRunResultsPluginComponent filter() {
		getFilterButton().click();
		return this;
	}

	public Optional<CodebeamerLocator> getPagingSelect() {
		return this.locatorIfPresent("select#paging", 5);
	}

	public TestRunResultsPluginComponent setPagingValue(Integer page) {
		Optional<CodebeamerLocator> pagingSelectLocator = getPagingSelect();
		if (pagingSelectLocator.isPresent()) {
			pagingSelectLocator.get().selectOption("page=%d".formatted(page));
			this.waitForResponse(urlEndsWith("testRunResults.spr"), 200, 30000);
		}

		return this;
	}

	public TestRunResultsPluginComponent setPagingToAll() {
		return this.setPagingValue(ALL_PAGES_ID);
	}

	@Override
	public TestRunResultsPluginAssertions assertThat() {
		return new TestRunResultsPluginAssertions(this);
	}
}
