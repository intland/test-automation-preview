package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class AbstractTrackerItemComponentTabAssertion<C extends AbstractTrackerItemComponentTab<C, A>, A extends AbstractTrackerItemComponentTabAssertion<C, A>> extends
		AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractTrackerItemComponentTabAssertion(C component) {
		super(component);
	}

	public A isTabActive() {
		return assertAll("Active tab should be in focus",
				() -> assertThat(getComponent().getTab()).hasClass(Pattern.compile(".*ditch-focused.*")));
	}

	public A isTabHidden() {
		return assertAll(getComponent().getTabName() + " should not be visible",
				() -> assertThat(getComponent().getTab()).not().isVisible());
	}
}
