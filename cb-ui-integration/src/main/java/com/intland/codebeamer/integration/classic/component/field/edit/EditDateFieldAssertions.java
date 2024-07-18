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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditDateFieldAssertions extends AbstractCodebeamerComponentAssert<EditDateFieldComponent, EditDateFieldAssertions> {

	protected EditDateFieldAssertions(EditDateFieldComponent component) {
		super(component);
	}

	public EditDateFieldAssertions is(int year, int month, int day, int hour, int minute) {
		String formattedDate = getComponent().getFormattedDate(year, month, day, hour, minute);
		return assertAll("Field should have '%s' value".formatted(formattedDate),
				() -> assertThat(getComponent().getValueField()).hasValue(formattedDate));
	}

}
