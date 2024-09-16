package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditDateFieldAssertions<C extends AbstractEditDateFieldComponent<C, A>, A extends AbstractEditDateFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditDateFieldAssertions(C component) {
		super(component);
	}

	public A is(int year, int month, int day, int hour, int minute) {
		String formattedDate = getComponent().getFormattedDate(year, month, day, hour, minute);
		return assertAll("Field should have '%s' value".formatted(formattedDate),
				() -> assertThat(getComponent().getValueField()).hasValue(formattedDate));
	}

}
