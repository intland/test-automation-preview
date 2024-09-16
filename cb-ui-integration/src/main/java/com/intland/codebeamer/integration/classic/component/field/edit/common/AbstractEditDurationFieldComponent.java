package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;

public abstract class AbstractEditDurationFieldComponent<C extends AbstractEditDurationFieldComponent<C, A>, A extends AbstractEditDurationFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	private static final String PATTERN = "\\d{1,2}(:\\d{2})?h|\\d{1,2}m|\\d{1,2}s";

	public AbstractEditDurationFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public abstract CodebeamerLocator getValueField();

	protected void validateInput(String value) {
		Matcher matcher = Pattern.compile(PATTERN).matcher(value);
		if (matcher.matches()) {
			return;
		}

		throw new IllegalArgumentException("Value does not allowed, value: %s, pattern: %s".formatted(value, PATTERN));
	}

}
