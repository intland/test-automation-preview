/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleaseDashboardAssertions extends
		AbstractCodebeamerComponentAssert<ReleaseDashboardComponent, ReleaseDashboardAssertions> {

	protected ReleaseDashboardAssertions(ReleaseDashboardComponent component) {
		super(component);
	}

	public ReleaseDashboardAssertions hasBacklogSectionForReleaseItem(TrackerItemId trackerItemId) {
		return assertAll("Release item with id '%d' should have backlog section".formatted(trackerItemId.id()),
				() -> assertThat(getComponent().getBacklogSectionForReleaseItem(trackerItemId).getSelfLocator()).isVisible()
		);
	}

	public ReleaseDashboardAssertions hasSprintSectionsForReleaseItem(TrackerItemId trackerItemId,
			List<TrackerItemId> sprintIds) {
		return assertAll("Release item with id '%d' should contain sprints with ids: '%s'".formatted(trackerItemId.id(),
				StringUtils.join(sprintIds, ", ")), () -> {
			assertSectionsMatch(getComponent().getCompositeSprintSectionsLocatorForReleaseItem(trackerItemId), getComponent().getSprintSectionsForReleaseItem(trackerItemId), sprintIds);
		});
	}

	public ReleaseDashboardAssertions hasSprintSections(List<TrackerItemId> sprintIds) {
		return assertAll("Sprints with ids '%s' should be present".formatted(StringUtils.join(sprintIds, ", ")), () -> {
			assertSectionsMatch(getComponent().getCompositeSprintSections(), getComponent().getSprintSections(), sprintIds);
		});
	}

	private void assertSectionsMatch(CodebeamerLocator compositeSections, List<CodebeamerLocator> sprintSections, List<TrackerItemId> sprintIds) {
		assertThat(compositeSections).hasCount(sprintIds.size());
		for (int i = 0; i < sprintSections.size(); i++) {
			assertThat(sprintSections.get(i)).hasAttribute("id", String.valueOf(sprintIds.get(i).id()));
		}
	}
}
