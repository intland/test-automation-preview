package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class FindTestRunTrackersTab extends AbstractTabComponent<FindTestRunTrackersTab, FindTestRunTrackersTabAssertions> {
	public FindTestRunTrackersTab(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, new TabId("#searchTab"));
	}

	@Override
	public FindTestRunTrackersTabAssertions assertThat() {
		return new FindTestRunTrackersTabAssertions(this);
	}
}
