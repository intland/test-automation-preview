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

public class DescriptionWikiTextFieldAssertions extends AbstractCodebeamerComponentAssert<DescriptionWikiTextFieldComponent, DescriptionWikiTextFieldAssertions> {

	protected DescriptionWikiTextFieldAssertions(DescriptionWikiTextFieldComponent component) {
		super(component);
	}

	public DescriptionWikiTextFieldAssertions is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueElement()).hasText(String.valueOf(value)));
	}

}
