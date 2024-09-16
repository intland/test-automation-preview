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

package com.intland.codebeamer.integration.classic.page.tracker.view.manager;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerViewManagerAssertions
		extends AbstractCodebeamerComponentAssert<TrackerViewManagerComponent, TrackerViewManagerAssertions> {

	protected TrackerViewManagerAssertions(TrackerViewManagerComponent component) {
		super(component);
	}

	public TrackerViewManagerAssertions activeView(String viewName) {
		return assertAll("Active tracker view should be %s".formatted(viewName),
				() -> assertThat(getComponent().getActiveViewElement()).hasText(viewName));
	}
}
