package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.tab.AbstractTabComponent;
import com.intland.codebeamer.integration.classic.component.tab.TabId;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class FindTestSetsTabComponent extends AbstractTabComponent<FindTestSetsTabComponent, FindTestSetsTabAssertions> {

	@Component("Find test sets table component")
	private final FindTestSetsTableComponent findTestSetsTableComponent;

	public FindTestSetsTabComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, new TabId("#searchTab"));
		this.findTestSetsTableComponent = new FindTestSetsTableComponent(getCodebeamerPage(), frameLocator);
	}

	public FindTestSetsTableComponent selectTestSet(TrackerItemId trackerItemId) {
		return this.findTestSetsTableComponent.selectTestSet(trackerItemId);
	}

	@Override
	public FindTestSetsTabAssertions assertThat() {
		return new FindTestSetsTabAssertions(this);
	}
}
