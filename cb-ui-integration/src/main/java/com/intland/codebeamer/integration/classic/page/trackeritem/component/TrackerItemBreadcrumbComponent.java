package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemBreadcrumbComponent extends AbstractCodebeamerComponent<TrackerItemBreadcrumbComponent, TrackerItemBreadcrumbAssertions> {

	public TrackerItemBreadcrumbComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".breadCrumbNavigation");
	}

	protected CodebeamerLocator itemReference() {
		return locator(".item[data-item-id]");
	}

	protected CodebeamerLocator placeholderElement() {
		return locator(".item:not([data-item-id])");
	}
	
	@Override
	public TrackerItemBreadcrumbAssertions assertThat() {
		return new TrackerItemBreadcrumbAssertions(this);
	}

}
