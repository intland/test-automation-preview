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

public class BaselineTableAssertion extends
		AbstractCodebeamerComponentAssert<BaselineTableComponent, BaselineTableAssertion> {

	protected BaselineTableAssertion(BaselineTableComponent component) {
		super(component);
	}

	public BaselineTableAssertion isBaselineVisibleByName(String baselineName) {
		return assertAll("Expected %s to be present and visible".formatted(baselineName),
				() -> assertThat(getComponent().getBaselineElement(baselineName)).isVisible());
	}

}
