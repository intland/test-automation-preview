package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleaseDashboardBacklogAssertions extends AbstractCodebeamerComponentAssert<ReleaseDashboardBacklogComponent, ReleaseDashboardBacklogAssertions> {

	protected ReleaseDashboardBacklogAssertions(ReleaseDashboardBacklogComponent component) {
		super(component);
	}

	public ReleaseDashboardBacklogAssertions hasBacklogItems(List<TrackerItemId> backlogItems) {
		return assertAll("Backlog should have following items: %s".formatted(StringUtils.join(backlogItems, ", ")), () -> {
			assertThat(getComponent().getCompositeBacklogItems()).hasCount(backlogItems.size());
			List<CodebeamerLocator> trackerItemLocators = getComponent().getBacklogItems();
			for (int i = 0; i < backlogItems.size(); i++) {
				assertThat(trackerItemLocators.get(i)).hasId(String.valueOf(backlogItems.get(i).id()));
			}
		});
	}
}
