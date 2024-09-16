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

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditDurationFieldAssertions<C extends AbstractEditDurationFieldComponent<C, A>, A extends AbstractEditDurationFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditDurationFieldAssertions(C component) {
		super(component);
	}

	public A is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueField()).hasValue(value));
	}

}
