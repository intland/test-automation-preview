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

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class BaselineCompareResultsAccordionContentAssertion extends
		AbstractCodebeamerComponentAssert<BaselineCompareResultsAccordionContentComponent, BaselineCompareResultsAccordionContentAssertion> {

	protected BaselineCompareResultsAccordionContentAssertion(BaselineCompareResultsAccordionContentComponent component) {
		super(component);
	}

	public BaselineCompareResultsAccordionContentAssertion isAccordionContentOpened() {
		return assertAll("Compare Results accordion content should be opened and visible",
				() -> {
					assertThat(getComponent().getComponentElement()).isVisible(createIsVisibleOptions());
					assertThat(getComponent().getComponentElement()).hasClass(Pattern.compile(".*opened.*"));
				});
	}

	public BaselineCompareResultsAccordionContentAssertion isIdenticalBaselinesWarningVisible() {
		return assertAll("Identical Baselines warning message should be visible",
				() -> assertThat(getComponent().getIdenticalBaselinesWarning()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
