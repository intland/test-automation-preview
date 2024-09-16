package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.List;
import java.util.stream.IntStream;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemTraceabilitySectionComponent extends AbstractCodebeamerComponent<TrackerItemTraceabilitySectionComponent, TrackerItemTraceabilitySectionAssertions> {

	public TrackerItemTraceabilitySectionComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".relations-box");
	}

	public List<TraceabilityReferenceComponent> upstreamReferences() {
		return references(1);
	}

	public CodebeamerLocator upstreamReference() {
		return this.locator(".collapsingBorder_content div:nth-child(1) tbody tr");
	}

	public CodebeamerLocator downstreamReference() {
		return this.locator(".collapsingBorder_content div:nth-child(3) tbody tr");
	}

	public CodebeamerLocator descriptionByTrackerItemId(int trackerItemId) {
		return this.locator("td[data-wikilink='[ITEM:%s]'] .traceabilityDescriptionBox .traceabilityTabWikiContent".formatted(trackerItemId));
	}

	public List<TraceabilityReferenceComponent> downstreamReferences() {
		return references(3);
	}

	private List<TraceabilityReferenceComponent> references(int childIndex) {
		String referencesRowSelector = ".collapsingBorder_content div:nth-child(" + childIndex + ") tbody tr";
		int rowCount = locator(referencesRowSelector).all().size();

		return IntStream.rangeClosed(1, rowCount)
				.mapToObj(i -> new TraceabilityReferenceComponent(getCodebeamerPage(),
						"#relations-box " + referencesRowSelector + ":nth-child(" + i + ")"))
				.toList();
	}

	public TrackerItemTraceabilitySectionComponent open() {
		getToggleElement().click();
		waitForResponse(urlEndsWith("/proj/tracker/loadItemTraceability.spr"), 200);
		return this;
	}

	public TrackerItemTraceabilitySectionComponent checkShowDescriptionCheckbox() {
		showDescriptionCheckbox().click();
		return this;
	}

	private CodebeamerLocator showDescriptionCheckbox() {
		return locator("#itemTraceabilityShowDescription");
	}

	private CodebeamerLocator getToggleElement() {
		return locator("a.collapseToggle");
	}
  
  public CodebeamerLocator getUpstreamReferenceElements() {
		return this.locator(".collapsingBorder_content div:nth-child(1) tbody tr");
	}

	public CodebeamerLocator getDownstreamReferenceElements() {
		return this.locator(".collapsingBorder_content div:nth-child(3) tbody tr");
	}

	public CodebeamerLocator getLocator() {
		return selfLocator();
	}

	public TrackerItemTraceabilitySectionNavigation clickOnCurrentItem() {
		traceabilityTabItemTitleLink().click();
		return new TrackerItemTraceabilitySectionNavigation(getCodebeamerPage());
	}

	private CodebeamerLocator traceabilityTabItemTitleLink() {
		return locator(".traceabilityTabItemTitle a");
	}

	@Override
	public TrackerItemTraceabilitySectionAssertions assertThat() {
		return new TrackerItemTraceabilitySectionAssertions(this);
	}
}
