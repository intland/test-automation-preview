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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditTextFieldAssertions<C extends AbstractEditTextFieldComponent<C, A>, A extends AbstractEditTextFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditTextFieldAssertions(C component) {
		super(component);
	}

	public A is(String value) {
		CodebeamerLocator actualValue = getComponent().getValueField();
		return assertAll("Field should have '%s' value, but it has: '%s'".formatted(value, actualValue.value()),
				() -> assertThat(actualValue).hasValue(value));
	}

	public A hasText(String value) {
		CodebeamerLocator actualValue = getComponent().getValueField();
		return assertAll("Field should have '%s' text, but it has: '%s'".formatted(value, actualValue.value()),
				() -> assertThat(actualValue).hasText(value));
	}
}
