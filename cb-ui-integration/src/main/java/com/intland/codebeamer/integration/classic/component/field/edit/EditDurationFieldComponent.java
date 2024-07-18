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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditDurationFieldComponent extends AbstractCodebeamerComponent<EditDurationFieldComponent, EditDurationFieldAssertions> {
	
	private static String PATTERN = "[1-9][0-9]+[smh]?";

	public EditDurationFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
	}

	public EditDurationFieldComponent fill(String value) {
		validateInput(value);
		getValueField().click().clear().fill(value);
		return this;
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue input[type=text]");
	}

	@Override
	public EditDurationFieldAssertions assertThat() {
		return new EditDurationFieldAssertions(this);
	}

	private void validateInput(String value) {
		Matcher matcher = Pattern.compile(PATTERN).matcher(value);
		if (matcher.matches()) {
			return;
		}
		
		throw new IllegalArgumentException("Value does not allowed, value: %s, pattern: %s".formatted(value, PATTERN));
	}
	
}
