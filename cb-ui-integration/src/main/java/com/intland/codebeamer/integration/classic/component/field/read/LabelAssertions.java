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

public class LabelAssertions extends AbstractCodebeamerComponentAssert<LabelComponent, LabelAssertions> {

	protected LabelAssertions(LabelComponent component) {
		super(component);
	}
	
	public LabelAssertions hasTitle(String title) {
		return assertAll("Field should have title %s".formatted(title),
				() -> assertThat(getComponent().getTitleElement()).hasAttribute("title", title));
	}

}
