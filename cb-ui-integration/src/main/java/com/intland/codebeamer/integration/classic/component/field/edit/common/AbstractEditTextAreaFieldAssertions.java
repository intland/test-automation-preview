package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditTextAreaFieldAssertions<C extends AbstractEditTextAreaFieldComponent<C, A>, A extends AbstractEditTextAreaFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditTextAreaFieldAssertions(C component) {
		super(component);
	}

	public A is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueField()).hasText(value));
	}

	public A contains(String value) {
		return assertAll("Field should contain '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueField()).containsText(value));
	}

}
