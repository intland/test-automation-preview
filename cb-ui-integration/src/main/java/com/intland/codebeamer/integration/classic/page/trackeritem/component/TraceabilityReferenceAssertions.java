package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TraceabilityReferenceAssertions extends AbstractCodebeamerComponentAssert<TraceabilityReferenceComponent, TraceabilityReferenceAssertions> {

	protected TraceabilityReferenceAssertions(TraceabilityReferenceComponent component) {
		super(component);
	}

	public TraceabilityReferenceAssertions isPlaceholder() {
		return assertAll("Traceability reference row should be a placeholder",
				() -> assertThat(getComponent().level1Name()).hasText("--"));

	}

	public TraceabilityReferenceAssertions isNotPlaceholder() {
		return assertAll("Traceability reference row should not be a placeholder",
				() -> assertThat(getComponent().level1Name()).not().hasText("--"));

	}
}
