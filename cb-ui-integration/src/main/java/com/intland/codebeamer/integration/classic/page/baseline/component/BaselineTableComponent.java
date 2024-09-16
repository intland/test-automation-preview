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

public class BaselineTableComponent extends
		AbstractCodebeamerComponent<BaselineTableComponent, BaselineTableAssertion> {

	public BaselineTableComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "table#baseline");
	}

	public CodebeamerLocator getBaselineElement(String baselineName) {
		return this.locator("td a:has-text('%s')".formatted(baselineName));
	}

	@Override
	public BaselineTableAssertion assertThat() {
		return new BaselineTableAssertion(this);
	}
}