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

package com.intland.codebeamer.integration.classic.page.tracker.main.component;

import com.intland.codebeamer.integration.classic.page.wiki.component.tree.AbstractTreeAssertions;

public class TrackerTreeAssertions extends AbstractTreeAssertions<TrackerTreeComponent, TrackerTreeAssertions> {

	protected TrackerTreeAssertions(TrackerTreeComponent component) {
		super(component);
	}

	public TrackerTreeAssertions trackerExistsByName(String trackerName) {
		return assertAll("Tracker should be found by '%s' name".formatted(trackerName),
				() -> assertThat(getComponent().getTreeItemByName(trackerName)).isVisible(createIsVisibleOptions()));
	}
}
