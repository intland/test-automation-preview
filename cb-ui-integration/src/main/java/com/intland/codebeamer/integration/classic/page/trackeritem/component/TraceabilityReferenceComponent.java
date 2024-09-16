package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TraceabilityReferenceComponent extends AbstractCodebeamerComponent<TraceabilityReferenceComponent, TraceabilityReferenceAssertions> {

	public TraceabilityReferenceComponent(CodebeamerPage codebeamerPage, String selector) {
		super(codebeamerPage, selector);
	}

	protected CodebeamerLocator level1Name() {
		return locator(" td:nth-child(2)");
	}

	@Override
	public TraceabilityReferenceAssertions assertThat() {
		return new TraceabilityReferenceAssertions(this);
	}
}
