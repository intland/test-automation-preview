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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.statetransition;

import static org.testng.Assert.assertEquals;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class StateTransitionAssertions
		extends AbstractCodebeamerComponentAssert<StateTransitionComponent, StateTransitionAssertions> {

	public StateTransitionAssertions(StateTransitionComponent component) {
		super(component);
	}

	public StateTransitionAssertions is(String name, String permitted) {
		return assertAll("The state transition's name from: '%s' to: '%s' should be: '%s' with permitted: '%s'".formatted(getComponent().getFrom(),
						getComponent().getTo(), name, permitted),
				() -> {
					assertEquals(getComponent().getNameValue(), name);
					assertEquals(getComponent().getPermittedValue(), permitted);
				});
	}

}
