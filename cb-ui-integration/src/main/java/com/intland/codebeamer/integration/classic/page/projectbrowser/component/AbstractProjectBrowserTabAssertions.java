package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.classic.component.tab.AbstractTabAssertions;

public class AbstractProjectBrowserTabAssertions<C extends AbstractProjectBrowserTab<C, A>, A extends AbstractProjectBrowserTabAssertions<C, A>>
		extends AbstractTabAssertions<C, A> {

	protected AbstractProjectBrowserTabAssertions(C component) {
		super(component);
	}
}
