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

public class DateFieldAssertions extends AbstractCodebeamerComponentAssert<DateFieldComponent, DateFieldAssertions> {

	protected DateFieldAssertions(DateFieldComponent component) {
		super(component);
	}

	public DateFieldAssertions is(int year, int month, int day, int hour, int minute) {
		String formattedDate = getComponent().getFormattedDate(year, month, day, hour, minute);
		return assertAll("Field should have '%s' value".formatted(formattedDate),
				() -> assertThat(getComponent().getValueElement()).hasText(formattedDate));
	}

}
