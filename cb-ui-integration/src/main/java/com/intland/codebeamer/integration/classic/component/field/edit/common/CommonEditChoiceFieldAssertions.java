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

package com.intland.codebeamer.integration.classic.component.field.edit.common;

public class CommonEditChoiceFieldAssertions
		extends AbstractEditChoiceFieldAssertions<CommonEditChoiceFieldComponent, CommonEditChoiceFieldAssertions> {

	protected CommonEditChoiceFieldAssertions(CommonEditChoiceFieldComponent component) {
		super(component);
	}

	@Override
	public CommonEditChoiceFieldAssertions is(String value) {
		return assertAll("Choice option('%s') should be selected".formatted(value),
				() -> assertThat(getComponent().getValueSelector()).hasValue(value));
	}

}
