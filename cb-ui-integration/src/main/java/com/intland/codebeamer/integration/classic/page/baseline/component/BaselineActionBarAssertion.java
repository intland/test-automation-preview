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

package com.intland.codebeamer.integration.classic.page.baseline.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class BaselineActionBarAssertion extends
		AbstractCodebeamerComponentAssert<BaselineActionBarComponent, BaselineActionBarAssertion> {

	protected BaselineActionBarAssertion(BaselineActionBarComponent component) {
		super(component);
	}

	public BaselineActionBarAssertion isNewBaselineButtonVisible() {
		return assertAll("New Baseline button should be present and visible",
				() -> assertThat(getComponent().getNewBaselineButton()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
