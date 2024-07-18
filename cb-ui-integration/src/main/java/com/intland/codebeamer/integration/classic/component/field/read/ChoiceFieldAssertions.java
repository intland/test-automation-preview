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

package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ChoiceFieldAssertions
		extends AbstractCodebeamerComponentAssert<ChoiceFieldComponent, ChoiceFieldAssertions> {

	protected ChoiceFieldAssertions(ChoiceFieldComponent component) {
		super(component);
	}

	public ChoiceFieldAssertions is(String value) {
		return assertAll("Choice option(%s) should be selected".formatted(value),
				() -> assertThat(getComponent().getValueElement()).hasText(value));
	}

}
