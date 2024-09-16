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

package com.intland.codebeamer.integration.classic.component.field.edit.form;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public class EditCheckboxFieldAssertions
		extends AbstractEditFieldComponentAssertions<EditCheckboxFieldComponent, EditCheckboxFieldAssertions> {

	public EditCheckboxFieldAssertions(EditCheckboxFieldComponent component) {
		super(component);
	}

	public EditCheckboxFieldAssertions is(Boolean value) {
		return assertAll("Field should have '%s' value".formatted(value.toString()),
				() -> assertEquals(getComponent().getValueSelector().isChecked(), value));
	}

	public EditCheckboxFieldAssertions isEnabled() {
		return assertAll("Field should be enabled",
				() -> assertTrue(getComponent().getValueSelector().isEnabled()));
	}

	public EditCheckboxFieldAssertions isDisabled() {
		return assertAll("Field should be disabled",
				() -> assertTrue(getComponent().getValueSelector().isDisabled()));
	}
}
