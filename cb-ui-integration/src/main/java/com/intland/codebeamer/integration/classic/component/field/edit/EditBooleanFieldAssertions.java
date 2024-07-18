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

package com.intland.codebeamer.integration.classic.component.field.edit;

import org.apache.commons.lang3.BooleanUtils;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditBooleanFieldAssertions
		extends AbstractCodebeamerComponentAssert<EditBooleanFieldComponent, EditBooleanFieldAssertions> {

	protected EditBooleanFieldAssertions(EditBooleanFieldComponent component) {
		super(component);
	}

	public EditBooleanFieldAssertions isTrue() {
		return is(Boolean.TRUE);
	}
	
	public EditBooleanFieldAssertions isFalse() {
		return is(Boolean.FALSE);
	}
	
	public EditBooleanFieldAssertions is(Boolean value) {
		String valueAsString = BooleanUtils.toStringTrueFalse(value).toLowerCase();
		return assertAll("Field should have '%s' value".formatted(valueAsString),
				() -> assertThat(getComponent().getValueSelector()).hasValue(valueAsString));
	}

}
