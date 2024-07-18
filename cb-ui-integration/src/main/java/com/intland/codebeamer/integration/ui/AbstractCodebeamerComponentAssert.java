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

package com.intland.codebeamer.integration.ui;

import java.util.concurrent.TimeUnit;

import com.intland.codebeamer.integration.CodebeamerAssertionUtil;
import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerAssertionUtil.Assertion;
import com.microsoft.playwright.assertions.LocatorAssertions;

public abstract class AbstractCodebeamerComponentAssert<C extends AbstractCodebeamerComponent<C, A>, A extends AbstractCodebeamerComponentAssert<C, A>> {

	protected static long ONE_SECOND_AS_MILLIS = TimeUnit.SECONDS.toMillis(1);
	
	private final C component;

	protected AbstractCodebeamerComponentAssert(C component) {
		this.component = component;
	}

	protected A assertAll(String message, Assertion assertion) {
		getAssertions().assertThat(message, assertion);
		return (A) this;
	}

	protected LocatorAssertions assertThat(CodebeamerLocator codebeamerLocator) {
		return com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(codebeamerLocator.getLocator());
	}

	protected C getComponent() {
		return component;
	}

	private CodebeamerAssertionUtil getAssertions() {
		return getComponent().getAssertions();
	}
	
}
