package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;

public abstract class AbstractEditDateFieldComponent<C extends AbstractEditDateFieldComponent<C, A>, A extends AbstractEditDateFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	private static final String DATE_FIELD_PATTERN = "MMM dd yyyy HH:mm";

	public AbstractEditDateFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}
	
	public abstract CodebeamerLocator getValueField();

	protected String getFormattedDate(int year, int month, int day, int hour, int minute) {
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FIELD_PATTERN);
		return dateTime.format(formatter);
	}
	
}
