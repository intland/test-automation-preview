package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.component.WikiContentComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TestStepComponent
		extends AbstractCodebeamerComponent<TestStepComponent, TestStepAssertions> {

	public TestStepComponent(CodebeamerPage codebeamerPage, String selector) {
		super(codebeamerPage, selector);
	}

	public WikiContentComponent getAction() {
		return new WikiContentComponent(getCodebeamerPage(), getSelector(".actionColumn .wikiContent"));
	}

	public WikiContentComponent getExpectedResult() {
		return new WikiContentComponent(getCodebeamerPage(), getSelector(".expectedResultColumn .wikiContent"));
	}

	@Override
	public TestStepAssertions assertThat() {
		return new TestStepAssertions(this);
	}

}
