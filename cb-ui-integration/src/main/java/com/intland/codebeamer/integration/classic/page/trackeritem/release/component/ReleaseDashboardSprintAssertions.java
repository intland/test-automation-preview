package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class ReleaseDashboardSprintAssertions extends
		AbstractReleaseDashboardReleaseAssertions<ReleaseDashboardSprintComponent, ReleaseDashboardSprintAssertions> {

	protected ReleaseDashboardSprintAssertions(ReleaseDashboardSprintComponent component) {
		super(component);
	}

	public ReleaseDashboardSprintAssertions hasTrackerItems(List<TrackerItemId> trackerItems) {
		return assertAll("Sprint should have following tracker items: %s".formatted(StringUtils.join(trackerItems, ", ")), () -> {
			assertThat(getComponent().getCompositeTrackerItems()).hasCount(trackerItems.size());
			List<CodebeamerLocator> trackerItemLocators = getComponent().getTrackerItems();
			for (int i = 0; i < trackerItems.size(); i++) {
				assertThat(trackerItemLocators.get(i)).hasId(String.valueOf(trackerItems.get(i).id()));
			}
		});
	}
}
