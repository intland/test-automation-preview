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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditChoiceFieldAssertions
		extends AbstractCodebeamerComponentAssert<EditChoiceFieldComponent, EditChoiceFieldAssertions> {

	protected EditChoiceFieldAssertions(EditChoiceFieldComponent component) {
		super(component);
	}

	public EditChoiceFieldAssertions is(String value) {
		int optionId = getComponent().resolveIdByName(value);
		return assertAll("Choice option(id:%s, name:%s) should be selected".formatted(optionId, value),
				() -> assertThat(getComponent().getValueSelector()).hasValue(String.valueOf(optionId)));
	}

}
