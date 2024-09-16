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

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class BaselinesAccordionContentAssertion extends
		AbstractCodebeamerComponentAssert<BaselinesAccordionContentComponent, BaselinesAccordionContentAssertion> {

	protected BaselinesAccordionContentAssertion(BaselinesAccordionContentComponent component) {
		super(component);
	}

	public BaselinesAccordionContentAssertion isAccordionContentOpened() {
		return assertAll("Baselines accordion content should be opened and visible",
				() -> {
					assertThat(getComponent().getComponentElement()).isVisible(createIsVisibleOptions());
					assertThat(getComponent().getComponentElement()).hasClass(Pattern.compile(".*opened.*"));
				});
	}

	public BaselinesAccordionContentAssertion isLeftBaselineSelectorButtonVisible() {
		return assertAll("Left Baseline selector button should be present and visible",
				() -> assertThat(getComponent().getLeftBaselineSelectorButton()).isVisible(createIsVisibleOptions()));
	}

	public BaselinesAccordionContentAssertion isCorrectLeftBaselineSelected(Baseline baseline) {
		return assertAll(String.format("Left Baseline id should be %s", baseline.id().id()),
				() -> assertThat(getComponent().getSelectedLeftBaselineId()).hasText(
						String.valueOf(baseline.id().id())));
	}

	public BaselinesAccordionContentAssertion isRightBaselineSelectorButtonVisible() {
		return assertAll("Right Baseline selector button should be present and visible",
				() -> assertThat(getComponent().getRightBaselineSelectorButton()).isVisible(createIsVisibleOptions()));
	}

	public BaselinesAccordionContentAssertion isCorrectRightBaselineSelected(Baseline baseline) {
		return assertAll(String.format("Right Baseline id should be %s", baseline.id().id()),
				() -> assertThat(getComponent().getSelectedRightBaselineId()).hasText(
						String.valueOf(baseline.id().id())));
	}

	public BaselinesAccordionContentAssertion isCompareBaselinesButtonVisible() {
		return assertAll("Compare Baselines button should be present and visible",
				() -> assertThat(getComponent().getCompareBaselinesButton()).isVisible(createIsVisibleOptions()));
	}

	public BaselinesAccordionContentAssertion isCompareBaselinesButtonEnabled() {
		return assertAll("Compare Baselines button should be enabled",
				() -> assertThat(getComponent().getCompareBaselinesButton()).isEnabled());
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
