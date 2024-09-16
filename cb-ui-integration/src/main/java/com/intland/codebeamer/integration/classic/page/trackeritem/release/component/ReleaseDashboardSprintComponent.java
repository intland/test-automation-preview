package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class ReleaseDashboardSprintComponent
		extends AbstractReleaseDashboardReleaseComponent<ReleaseDashboardSprintComponent, ReleaseDashboardSprintAssertions> {

	protected ReleaseDashboardSprintComponent(CodebeamerPage codebeamerPage, Tracker tracker,
			TrackerItemId trackerItemId) {
		this(codebeamerPage, tracker, trackerItemId, null);
	}

	protected ReleaseDashboardSprintComponent(CodebeamerPage codebeamerPage, Tracker tracker,
			TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage, tracker, trackerItemId, "sprint", baseline);
	}

	public CodebeamerLocator getCompositeTrackerItems() {
		return this.locator("tr.trackerItem");
	}

	public List<CodebeamerLocator> getTrackerItems() {
		return getCompositeTrackerItems().all();
	}

	@Override
	public ReleaseDashboardSprintAssertions assertThat() {
		return new ReleaseDashboardSprintAssertions(this);
	}
}
