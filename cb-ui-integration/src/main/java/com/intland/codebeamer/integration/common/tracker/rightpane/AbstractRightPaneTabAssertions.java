package com.intland.codebeamer.integration.common.tracker.rightpane;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractRightPaneTabAssertions<C extends AbstractRightPaneTab<C, A>, A extends AbstractRightPaneTabAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractRightPaneTabAssertions(C component) {
		super(component);
	}

}
