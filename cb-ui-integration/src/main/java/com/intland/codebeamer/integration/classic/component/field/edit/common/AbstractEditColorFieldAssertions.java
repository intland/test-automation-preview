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

package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public class AbstractEditColorFieldAssertions<C extends AbstractEditColorFieldComponent<C, A>, A extends AbstractEditColorFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditColorFieldAssertions(C component) {
		super(component);
	}

	public A is(HtmlColor color) {
		return assertAll("Field should have '%s' value".formatted(color.getHexCode()),
				() -> assertThat(getComponent().getValueField()).hasValue(color.getHexCode()));
	}

}
