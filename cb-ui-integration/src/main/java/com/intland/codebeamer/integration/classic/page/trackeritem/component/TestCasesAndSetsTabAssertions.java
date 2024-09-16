package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.classic.component.tab.AbstractTabAssertions;

public class TestCasesAndSetsTabAssertions
		extends AbstractTabAssertions<TestCasesAndSetsTabComponent, TestCasesAndSetsTabAssertions> {

	public TestCasesAndSetsTabAssertions(TestCasesAndSetsTabComponent component) {
		super(component);
	}

	public TestCasesAndSetsTabAssertions countOfTestCasesMatch(int countOfTestCases) {
		assertAll("Count of test cases should match",
				() -> assertThat(getComponent().getCountOfTestCases()).hasText(String.format("%,d", countOfTestCases)));
		return this;
	}
}
