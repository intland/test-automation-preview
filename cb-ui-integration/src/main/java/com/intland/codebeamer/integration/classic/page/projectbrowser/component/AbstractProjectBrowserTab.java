package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public abstract class AbstractProjectBrowserTab<C extends AbstractProjectBrowserTab<C, A>, A extends AbstractProjectBrowserTabAssertions<C, A>>
		extends AbstractTabComponent<C, A> {

	public AbstractProjectBrowserTab(CodebeamerPage codebeamerPage, TabId tabId) {
		super(codebeamerPage, tabId);
	}
}
