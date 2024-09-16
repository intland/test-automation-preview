package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemBreadcrumbAssertions extends AbstractCodebeamerComponentAssert<TrackerItemBreadcrumbComponent, TrackerItemBreadcrumbAssertions> {

	protected TrackerItemBreadcrumbAssertions(TrackerItemBreadcrumbComponent component) {
		super(component);
	}

	public TrackerItemBreadcrumbAssertions hasNoItemReference() {
		return assertAll("Should have no item reference",
				() -> {
					assertThat(getComponent().itemReference()).hasCount(0);
					assertThat(getComponent().placeholderElement()).isVisible();
				});
	}

}
