package com.intland.codebeamer.integration.classic.component.tab;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractTabAssertions<C extends AbstractTabComponent<C, A>, A extends AbstractTabAssertions<C, A>> extends
		AbstractCodebeamerComponentAssert<C, A> {

	private static final Pattern VISIBLE_TAB_PATTERN = Pattern.compile(".*ditch-focused.*");

	public AbstractTabAssertions(C component) {
		super(component);
	}

	public A isTabActive() {
		return assertAll("%s should be in focus".formatted(getComponent().getTabId().getTabId()),
				() -> assertThat(getComponent().getTab()).hasClass(VISIBLE_TAB_PATTERN));
	}

	public A isTabContentActive() {
		return assertAll("%s should be visible".formatted(getComponent().getTabId().getTabContentId()),
				() -> assertThat(getComponent().getTabContent()).isVisible());
	}
}
