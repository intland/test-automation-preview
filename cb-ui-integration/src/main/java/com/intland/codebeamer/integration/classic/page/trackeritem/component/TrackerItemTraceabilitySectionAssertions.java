package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.List;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemTraceabilitySectionAssertions extends AbstractCodebeamerComponentAssert<TrackerItemTraceabilitySectionComponent, TrackerItemTraceabilitySectionAssertions> {

	private static final Pattern EXPANDED_CLASS_PATTERN = Pattern.compile("collapsingBorder_expanded");

	protected TrackerItemTraceabilitySectionAssertions(TrackerItemTraceabilitySectionComponent component) {
		super(component);
	}

	public TrackerItemTraceabilitySectionAssertions hasUpstreamReference(int numberOfUpstreamReferences) {
		return assertAll("Traceability section should contain at least one upstream reference",
				() -> {
					List<TraceabilityReferenceComponent> upstreamReferences = getComponent().upstreamReferences();
					assertThat(getComponent().getUpstreamReferenceElements()).hasCount(numberOfUpstreamReferences);
					upstreamReferences.getFirst().assertThat().isNotPlaceholder();
				});
	}

	public TrackerItemTraceabilitySectionAssertions hasNoUpstreamReference() {
		return assertAll("Traceability section should not contain upstream reference",
				() -> {
					List<TraceabilityReferenceComponent> upstreamReferences = getComponent().upstreamReferences();
					assertThat(getComponent().getUpstreamReferenceElements()).hasCount(1);
					upstreamReferences.getFirst().assertThat().isPlaceholder();
				});
	}

	public TrackerItemTraceabilitySectionAssertions hasDownstreamReference(int numberOfDownstreamReferences) {
		return assertAll("Traceability section should contain at least one downstream reference",
				() -> {
					List<TraceabilityReferenceComponent> downstreamReferences = getComponent().downstreamReferences();
					assertThat(getComponent().getDownstreamReferenceElements()).hasCount(numberOfDownstreamReferences);
					downstreamReferences.getFirst().assertThat().isNotPlaceholder();
				});
	}

	public TrackerItemTraceabilitySectionAssertions hasNoDownstreamReference() {
		return assertAll("Traceability section should not contain downstream reference",
				() -> {
					List<TraceabilityReferenceComponent> downstreamReferences = getComponent().downstreamReferences();
					assertThat(getComponent().getDownstreamReferenceElements()).hasCount(1);
					downstreamReferences.getFirst().assertThat().isPlaceholder();
				});
	}

	public TrackerItemTraceabilitySectionAssertions verifyTrackerItemTraceabilityDescription(TrackerItemId TrackerItemId, String expectedDescription) {
		return assertAll("Description should be: %s for the following TrackerItemId: %s".formatted(expectedDescription, TrackerItemId),
				() ->
						assertThat(getComponent().descriptionByTrackerItemId(TrackerItemId.id())).hasText(expectedDescription)
		);
	}

	public TrackerItemTraceabilitySectionAssertions verifyTrackerItemTraceabilityDescriptionIsHidden(TrackerItemId TrackerItemId) {
		return assertAll("Description should be hidden for the following TrackerItemId: %s".formatted(TrackerItemId),
				() ->
						assertThat(getComponent().descriptionByTrackerItemId(TrackerItemId.id())).isHidden()
		);
	}

	public TrackerItemTraceabilitySectionAssertions isExpanded() {
		return assertAll("Traceability section should be expanded",
				() -> assertThat(getComponent().getLocator()).hasClass(EXPANDED_CLASS_PATTERN)
		);
	}
}
