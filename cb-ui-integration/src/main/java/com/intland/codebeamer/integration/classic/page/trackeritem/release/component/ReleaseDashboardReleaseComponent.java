package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class ReleaseDashboardReleaseComponent
		extends AbstractReleaseDashboardReleaseComponent<ReleaseDashboardReleaseComponent, ReleaseDashboardReleaseAssertions> {

	protected ReleaseDashboardReleaseComponent(CodebeamerPage codebeamerPage, Tracker tracker,
			TrackerItemId trackerItemId) {
		this(codebeamerPage, tracker, trackerItemId, null);
	}

	protected ReleaseDashboardReleaseComponent(CodebeamerPage codebeamerPage, Tracker tracker,
			TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage, tracker, trackerItemId, "release", baseline);
	}

	@Override
	public ReleaseDashboardReleaseAssertions assertThat() {
		return new ReleaseDashboardReleaseAssertions(this);
	}

	public void clickTransitionSectionIcon() {
		getTransitionSectionIcon().click();
	}

	private CodebeamerLocator getTransitionSectionIcon() {
		return this.locator("div.large-icon");
	}

	public CodebeamerLocator getTransition(String transition) {
		return this.locator("div.transition-action-menu li.yuimenuitem a[data-testid='%s']".formatted(transition));
	}

	public CodebeamerLocator getReleaseDescription() {
		return this.locator("div.description");
	}
}
