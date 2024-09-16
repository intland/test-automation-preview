package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditDecimalFieldAssertions<C extends AbstractEditDecimalFieldComponent<C, A>, A extends AbstractEditDecimalFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditDecimalFieldAssertions(C component) {
		super(component);
	}

	public A is(double value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueField()).hasValue(String.valueOf(value)));
	}

}
