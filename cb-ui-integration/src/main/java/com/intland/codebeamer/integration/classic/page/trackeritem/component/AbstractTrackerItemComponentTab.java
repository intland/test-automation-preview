package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractTrackerItemComponentTab<C extends AbstractTrackerItemComponentTab<C, A>, A extends AbstractTrackerItemComponentTabAssertion<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private String frameLocator;

	protected AbstractTrackerItemComponentTab(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	protected AbstractTrackerItemComponentTab(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.frameLocator = frameLocator;
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	public CodebeamerLocator getTab() {
		if (frameLocator != null) {
			return getCodebeamerPage().frameLocator(frameLocator, getTabId());
		}
		return getCodebeamerPage().locator(getTabId());
	}

	protected abstract String getTabId();

	protected abstract String getTabName();
}
