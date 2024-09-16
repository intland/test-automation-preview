package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditIntegerFieldAssertions<C extends AbstractEditIntegerFieldComponent<C, A>, A extends AbstractEditIntegerFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditIntegerFieldAssertions(C component) {
		super(component);
	}

	public A is(int value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueField()).hasValue(String.valueOf(value)));
	}

}
