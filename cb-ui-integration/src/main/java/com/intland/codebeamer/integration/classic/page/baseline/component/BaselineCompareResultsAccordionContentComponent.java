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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class BaselineCompareResultsAccordionContentComponent extends
		AbstractCodebeamerComponent<BaselineCompareResultsAccordionContentComponent, BaselineCompareResultsAccordionContentAssertion> {

	private static final String LOCATOR = "compareResultsAccordionContent";

	public BaselineCompareResultsAccordionContentComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, String.format("div[data-testid='%s']", LOCATOR));
	}

	public CodebeamerLocator getComponentElement() {
		return this.locatorByTestId(LOCATOR);
	}

	public CodebeamerLocator getIdenticalBaselinesWarning() {
		return this.locatorByTestId("identicalBaselinesWarning");
	}

	@Override
	public BaselineCompareResultsAccordionContentAssertion assertThat() {
		return new BaselineCompareResultsAccordionContentAssertion(this);
	}
}