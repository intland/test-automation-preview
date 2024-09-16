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
import com.intland.codebeamer.integration.classic.page.baseline.BaselineNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class BaselineActionBarComponent extends
		AbstractCodebeamerComponent<BaselineActionBarComponent, BaselineActionBarAssertion> {

	private BaselineNavigation baselineNavigation;

	public BaselineActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".actionBar");
		baselineNavigation = new BaselineNavigation(codebeamerPage);
	}

	public BaselineNavigation createNewBaseline() {
		getNewBaselineButton().click();
		return baselineNavigation;
	}

	public CodebeamerLocator getNewBaselineButton() {
		return this.locator("a.actionLink.createNewBaselineAction");
	}

	@Override
	public BaselineActionBarAssertion assertThat() {
		return new BaselineActionBarAssertion(this);
	}
}