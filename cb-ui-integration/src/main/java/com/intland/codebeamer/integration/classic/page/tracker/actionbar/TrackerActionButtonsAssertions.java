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

package com.intland.codebeamer.integration.classic.page.tracker.actionbar;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerActionButtonsAssertions
		extends AbstractCodebeamerComponentAssert<TrackerActionButtonsComponent, TrackerActionButtonsAssertions> {

	protected TrackerActionButtonsAssertions(TrackerActionButtonsComponent component) {
		super(component);
	}

	public TrackerActionButtonsAssertions hasBaselinesAndBranchesButton() {
		return assertAll("Baselines and Branches button should be present on action bar",
				() -> assertThat(getComponent().getBaselinesandBranchesButton()).isVisible());
	}

}
