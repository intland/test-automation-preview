package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractReleaseDashboardReleaseComponent<C extends AbstractReleaseDashboardReleaseComponent<C, A>, A extends AbstractReleaseDashboardReleaseAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	protected final Tracker tracker;

	protected final TrackerItemId trackerItemId;

	protected final Baseline baseline;

	protected AbstractReleaseDashboardReleaseComponent(CodebeamerPage codebeamerPage, Tracker tracker,
			TrackerItemId trackerItemId, String type, Baseline baseline) {
		super(codebeamerPage, "div[id='%d'].%s".formatted(trackerItemId.id(), type));
		this.tracker = tracker;
		this.trackerItemId = trackerItemId;
		this.baseline = baseline;
	}

	public C expand() {
		try {
			assertThat().isExpanded();
		} catch (AssertionError e) {
			getExpander().click();
		}
		return (C) this;
	}

	public C close() {
		try {
			assertThat().isClosed();
		} catch (AssertionError e) {
			getExpander().click();
		}
		return (C) this;
	}

	public ReleaseOverlayMenuComponent openMoreMenu() {
		locator("#more-menu-%d".formatted(trackerItemId.id())).click();
		return new ReleaseOverlayMenuComponent(getCodebeamerPage(), tracker.id(), trackerItemId, baseline);
	}

	public CodebeamerLocator getExpander() {
		return this.locator("a.expander");
	}

	public TrackerItemPage openTrackerDependenciesInTraceability() {
		getViewTrackerDependenciesInTraceabilityButton().click();
		return new TrackerItemPage(getCodebeamerPage(), tracker.id(), trackerItemId, baseline);
	}

	public CodebeamerLocator getViewTrackerDependenciesInTraceabilityButton() {
		this.selfLocator().hover();
		return this.locatorByTestId("traceabilityActionIcon-%d".formatted(trackerItemId.id()));
	}

	public CodebeamerLocator getVersionBadge() {
		return this.locator("span[data-testid='versionBadge']");
	}

	public CodebeamerLocator getReleaseItemLink() {
		return this.locator("a[data-testid='itemLink']");
	}

	public CodebeamerLocator getNumberOfSprints() {
		return this.locator("span[data-testid='totalSprintStats']");
	}

	public CodebeamerLocator getSprintsProgressBar() {
		return this.locator("[data-testid='sprintStats'] div.miniprogressbar label");
	}

	public CodebeamerLocator getNumberOfClosedSprints() {
		return this.locator("span[data-testid='closedSprintStats']");
	}

	public CodebeamerLocator getNumberOfOpenSprints() {
		return this.locator("span[data-testid='openSprintStats']");
	}
}
