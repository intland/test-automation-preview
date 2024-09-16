package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractAccordionSectionAssertions<C extends AbstractAccordionSection<C, A>, A extends AbstractAccordionSectionAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractAccordionSectionAssertions(C component) {
		super(component);
	}

}
