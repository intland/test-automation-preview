package com.intland.codebeamer.integration.classic.component.tab;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public abstract class AbstractTabComponent<C extends AbstractTabComponent<C, A>, A extends AbstractTabAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private final TabId tabId;

	public AbstractTabComponent(CodebeamerPage codebeamerPage, String frameLocator, TabId tabId) {
		super(codebeamerPage, frameLocator, tabId.getTabContentId());
		this.tabId = tabId;
	}

	public AbstractTabComponent(CodebeamerPage codebeamerPage, TabId tabId) {
		this(codebeamerPage, null, tabId);
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	public C activateTab(double timeout) {
		getTab().click(new Locator.ClickOptions().setTimeout(timeout));
		return (C) this;
	}

	public CodebeamerLocator getTab() {
		return locator(tabId.getTabId());
	}

	public CodebeamerLocator getTabContent() {
		return locator("div%s".formatted(tabId.getTabContentId()));
	}

	protected CodebeamerLocator locator(String selector) {
		if (StringUtils.isNotBlank(getFrameLocator())) {
			return getCodebeamerPage().frameLocator(getFrameLocator(), selector);
		}

		return getCodebeamerPage().locator(selector);
	}

	public TabId getTabId() {
		return tabId;
	}
}
