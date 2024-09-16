package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseOverlayMenuComponent extends AbstractCodebeamerComponent<ReleaseOverlayMenuComponent, ReleaseOverlayMenuAssertions> {

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	private final Baseline baseline;

	public ReleaseOverlayMenuComponent(CodebeamerPage codebeamerPage, TrackerId trackerId,
			TrackerItemId trackerItemId) {
		this(codebeamerPage, trackerId, trackerItemId, null);
	}

	public ReleaseOverlayMenuComponent(CodebeamerPage codebeamerPage, TrackerId trackerId,
			TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage, "ul.context-menu-list:not([style='display:none'])");
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
		this.baseline = baseline;
	}

	public CodebeamerLocator getTraceabilityButton() {
		return this.locator("li.context-menu-item a:has-text('Traceability')");
	}

	public TrackerItemPage openTraceability() {
		getTraceabilityButton().click();
		return new TrackerItemPage(getCodebeamerPage(), trackerId, trackerItemId, baseline).isActive();
	}

	@Override
	public ReleaseOverlayMenuAssertions assertThat() {
		return new ReleaseOverlayMenuAssertions(this);
	}
}
