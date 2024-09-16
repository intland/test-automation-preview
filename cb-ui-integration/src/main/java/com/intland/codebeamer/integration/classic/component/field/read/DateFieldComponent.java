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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DateFieldComponent extends AbstractCodebeamerComponent<DateFieldComponent, DateFieldAssertions>
		implements InlineEditable<DateFieldComponent> {

	private static final String DATE_FIELD_PATTERN = "MMM dd yyyy HH:mm";
	
	public DateFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public DateFieldAssertions assertThat() {
		return new DateFieldAssertions(this);
	}

	protected String getFormattedDate(int year, int month, int day, int hour, int minute) {
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FIELD_PATTERN);
		return dateTime.format(formatter);
	}
	
}
