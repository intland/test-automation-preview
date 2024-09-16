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

package com.intland.codebeamer.integration.classic.page.scmrepository.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public class ScmRepositoryMainActionBarAssertion extends
		AbstractCodebeamerComponentAssert<ScmRepositoryMainActionBarComponent, ScmRepositoryMainActionBarAssertion> {

	protected ScmRepositoryMainActionBarAssertion(ScmRepositoryMainActionBarComponent component) {
		super(component);
	}

	public ScmRepositoryMainActionBarAssertion isNewRepositoryButtonVisible() {
		return assertAll("New Repository button should be present and visible",
				() -> assertThat(getComponent().getNewRepositoryButton()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
