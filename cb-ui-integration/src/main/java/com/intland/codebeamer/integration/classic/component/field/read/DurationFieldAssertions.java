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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class DurationFieldAssertions extends AbstractCodebeamerComponentAssert<DurationFieldComponent, DurationFieldAssertions> {

	private static String PATTERN = "[1-9][0-9]+:[0-9][0-9]+[smh]?";
	
	protected DurationFieldAssertions(DurationFieldComponent component) {
		super(component);
	}

	public DurationFieldAssertions is(String value) {
		validateInput(value);
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getValueElement()).hasText(value));
	}

	private void validateInput(String value) {
		Matcher matcher = Pattern.compile(PATTERN).matcher(value);
		if (matcher.matches()) {
			return;
		}
		
		throw new IllegalArgumentException("Value does not allowed, value: %s, pattern: %s".formatted(value, PATTERN));
	}
	
}
