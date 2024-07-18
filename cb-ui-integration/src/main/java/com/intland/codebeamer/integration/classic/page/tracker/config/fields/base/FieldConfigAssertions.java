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

package com.intland.codebeamer.integration.classic.page.tracker.config.fields.base;

import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class FieldConfigAssertions extends AbstractCodebeamerComponentAssert<FieldConfigDialog, FieldConfigAssertions> {

	protected FieldConfigAssertions(FieldConfigDialog component) {
		super(component);
	}

	public FieldConfigAssertions assertDescription(String input) {
		return assertAll("Input should have '%s' value".formatted(input),
				() -> assertThat(getComponent().getDescriptionInput()).hasValue(input));
	}

	public FieldConfigAssertions assertType(FieldType fieldType) {
		return assertAll("Field type should be '%s'".formatted(fieldType.name()),
				() -> assertThat(getComponent().getTypeSelector()).hasValue(fieldType.getTypeIdAsString()));
	}

	public FieldConfigAssertions assertListable(boolean listable) {
		return assertAll("Listable should be: %s".formatted(listable),
				() -> assertThat(getComponent().getListableCheckbox()).isChecked(
						new LocatorAssertions.IsCheckedOptions().setChecked(listable)));
	}

	public void close() {
		getComponent().clickCancel();
	}
}
