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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditDateFieldComponent extends AbstractCodebeamerComponent<EditDateFieldComponent, EditDateFieldAssertions> {

	private static final String DATE_FIELD_PATTERN = "MMM dd yyyy HH:mm";

	public EditDateFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
	}

	public EditDateFieldComponent fill(int year, int month, int day, int hour, int minute) {
		getValueField().click().clear().fill(String.valueOf(getFormattedDate(year, month, day, hour, minute)));
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue:has(img.calendarAnchorLink) input[type=text]");
	}

	@Override
	public EditDateFieldAssertions assertThat() {
		return new EditDateFieldAssertions(this);
	}

	protected String getFormattedDate(int year, int month, int day, int hour, int minute) {
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FIELD_PATTERN);
		return dateTime.format(formatter);
	}
	
}
