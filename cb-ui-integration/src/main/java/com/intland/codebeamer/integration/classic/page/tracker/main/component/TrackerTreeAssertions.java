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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class TrackerTreeAssertions extends AbstractCodebeamerComponentAssert<TrackerTreeComponent, TrackerTreeAssertions> {

	protected TrackerTreeAssertions(TrackerTreeComponent component) {
		super(component);
	}

	public TrackerTreeAssertions trackerExistsByName(String trackerName) {
		return assertAll("Tracker should be found by '%s' name".formatted(trackerName),
				() -> assertThat(getComponent().getWorkTrackerByName(trackerName)).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
}
