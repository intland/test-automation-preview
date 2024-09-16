package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractAccordionSection<C extends AbstractAccordionSection<C, A>, A extends AbstractAccordionSectionAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private final String headerSelector;

	public AbstractAccordionSection(CodebeamerPage codebeamerPage, String headerSelector) {
		super(codebeamerPage, headerSelector + " + div");
		this.headerSelector = headerSelector;
	}

	public C open() {
		getSectionHeader().click();
		return (C) this;
	}

	private CodebeamerLocator getSectionHeader() {
		return getCodebeamerPage().locator(headerSelector);
	}

}
