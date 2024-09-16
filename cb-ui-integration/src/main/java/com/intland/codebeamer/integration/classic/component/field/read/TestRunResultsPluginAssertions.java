package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TestRunResultsPluginAssertions
		extends AbstractCodebeamerComponentAssert<TestRunResultsPluginComponent, TestRunResultsPluginAssertions> {

	public TestRunResultsPluginAssertions(TestRunResultsPluginComponent component) {
		super(component);
	}

	public TestRunResultsPluginAssertions countOfTestCasesMatch(int countOfTestCases) {
		assertAll("Count of test cases should match",
				() -> assertThat(getComponent().getTestCaseLinks()).hasCount(countOfTestCases));
		return this;
	}
}
