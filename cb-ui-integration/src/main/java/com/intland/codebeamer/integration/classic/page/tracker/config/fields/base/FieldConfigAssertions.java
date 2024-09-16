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

import java.util.Arrays;

import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class FieldConfigAssertions extends AbstractCodebeamerComponentAssert<FieldConfigDialog, FieldConfigAssertions> {

	protected FieldConfigAssertions(FieldConfigDialog component) {
		super(component);
	}

	public FieldConfigAssertions assertLabel(String label) {
		return assertAll("Label should have '%s' value".formatted(label),
				() -> assertThat(getComponent().getLabelInput()).hasValue(label));
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
	public FieldConfigAssertions assertDescription(String input) {
		return assertAll("Input should have '%s' value".formatted(input),
				() -> assertThat(getComponent().getDescriptionInput()).hasValue(input));
	}

	public FieldConfigAssertions assertDependsOnIsNotDefault() {
		return assertAll("Input should not have default value",
				() -> assertThat(getComponent().getDependsOnSelector()).not().hasValue("-1"));
	}

	public FieldConfigAssertions assertUnion(boolean isSelected) {
		return assertAll("Union checkbox should be %s".formatted(isSelected),
				() -> assertThat(getComponent().getUnionCheckbox()).isChecked(
						new LocatorAssertions.IsCheckedOptions().setChecked(isSelected)));
	}

	public FieldConfigAssertions assertMandatoryIf(String input) {
		return assertAll("Input should have '%s' value".formatted(input),
				() -> assertThat(getComponent().getMandatoryIfInput()).hasValue(input));
	}

	public FieldConfigAssertions hasDialogTitle(String title) {
		return assertAll("Dialog title should have '%s' value".formatted(title),
				() -> assertThat(getComponent().getDialogTitleElement()).containsText(title));
	}

	public FieldConfigAssertions assertSharedField(String... sharedFields) {
		return assertAll(
				"Shared field(s) should be '%s'".formatted(Arrays.toString(sharedFields)),
				() -> {
					getComponent().getSharedFieldElement().click();
					getComponent().getSharedFieldMultiselectMenuComponent().assertThat().isSelected(sharedFields);
					getComponent().getSharedFieldElement().click();
				});
	}
	
	public void close() {
		getComponent().cancel();
	}
}
