package com.intland.codebeamer.integration.common.tracker.rightpane;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractRightPaneTab<C extends AbstractRightPaneTab<C, A>, A extends AbstractRightPaneTabAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private final String parentSelector;

	public AbstractRightPaneTab(CodebeamerPage codebeamerPage, String parentSelector, String componentLocator) {
		super(codebeamerPage, componentLocator);
		this.parentSelector = parentSelector;
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	protected abstract String getTabClass();

	private CodebeamerLocator getTab() {
		return getCodebeamerPage().locator("%s li.%s".formatted(parentSelector, getTabClass()));
	}

}
