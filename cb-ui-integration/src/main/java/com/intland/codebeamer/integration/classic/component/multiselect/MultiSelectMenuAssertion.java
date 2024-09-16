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

package com.intland.codebeamer.integration.classic.component.multiselect;

import java.util.Arrays;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class MultiSelectMenuAssertion
		extends AbstractCodebeamerComponentAssert<MultiSelectMenuComponent, MultiSelectMenuAssertion> {

	MultiSelectMenuAssertion(MultiSelectMenuComponent component) {
		super(component);
	}

	public MultiSelectMenuAssertion isSelected(int... indices) {
		return assertAll("%s indices should be selected".formatted(Arrays.toString(indices)),
				() -> {
					assertThat(getComponent().getSelectedLocator()).hasCount(indices.length);
					for (int index : indices) {
						assertThat(getComponent().getByIndex(index)).isChecked();
					}
				});
	}

	public MultiSelectMenuAssertion isSelected(String... names) {
		return assertAll("%s should be selected".formatted(Arrays.toString(names)),
				() -> {
					assertThat(getComponent().getSelectedLocator()).hasCount(names.length);
					for (String name : names) {
						assertThat(getComponent().getByName(name)).isChecked();
					}
				});

	}

}
