package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class TestCasesAndSetsTabComponent extends AbstractTabComponent<TestCasesAndSetsTabComponent, TestCasesAndSetsTabAssertions> {
	public TestCasesAndSetsTabComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, new TabId("#testSetTestCases"));
	}

	public CodebeamerLocator getCountOfTestCases() {
		return this.locator(".showAll:first-child");
	}

	@Override
	public TestCasesAndSetsTabAssertions assertThat() {
		return new TestCasesAndSetsTabAssertions(this);
	}
}
