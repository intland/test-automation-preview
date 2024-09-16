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

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public class AbstractEditChoiceFieldAssertions
		<C extends AbstractEditChoiceFieldComponent<C, A>, A extends AbstractEditChoiceFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditChoiceFieldAssertions(C component) {
		super(component);
	}

	public A is(String value) {
		return assertAll("Choice option(name:%s) should be selected".formatted(value),
				() -> assertThat(getComponent().getValueSelector().concat("option:checked")).hasText(value));
	}

	public A options(String... options) {
		return assertAll("Choice option(name:%s) should be selected".formatted(Arrays.toString(options)),
				() -> {
					assertThat(getComponent().getOptionsSelector()).hasCount(options.length);
					List<String> selectableOptions = getComponent().getOptionsSelector().all().stream().map(a -> a.text()).toList();
					MatcherAssert.assertThat(selectableOptions, Matchers.containsInAnyOrder(options));
				}
			);
	}

}
