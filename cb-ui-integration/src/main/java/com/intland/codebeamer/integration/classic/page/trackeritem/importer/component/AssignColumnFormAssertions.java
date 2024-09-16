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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;
import com.intland.codebeamer.integration.classic.component.multiselect.MultiSelectMenuAssertion;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.NumberFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AssignColumnFormAssertions<C extends AssignColumnFormComponent<C, A>, A extends AssignColumnFormAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AssignColumnFormAssertions(C component) {
		super(component);
	}

	public A isDateFormat(DateFormat dateFormat) {
		return assertAll("Date format should be '%s'".formatted(dateFormat),
				() -> assertThat(getComponent().getDateFormatLocator()).hasValue(dateFormat.getValue()));
	}

	public A isTimeZone(TimeZone timeZone) {
		return assertAll("Time zone should be '%s'".formatted(timeZone),
				() -> assertThat(getComponent().getTimeZoneLocator()).hasValue(timeZone.getValue()));
	}

	public A isNumberFormat(NumberFormat format) {
		return assertAll("Number format should be '%s'".formatted(format),
				() -> assertThat(getComponent().getNumberFormatLocator()).hasValue(format.getValue()));
	}

	public A isDescriptionFormat(WikiTextType format) {
		return assertAll("Description format should be '%s'".formatted(format),
				() -> assertThat(getComponent().getDescriptionFormatLocator()).hasValue(getComponent().getValueForDescFormat(format)));
	}

	public A isSkipDataOrCell(String value) {
		return assertAll("Skip data or cell should be '%s'".formatted(value),
				() -> assertThat(getComponent().getSkipDataOrCellLocator()).hasValue(value));
	}

	public A findAndUpdateExistingItemsBy(Consumer<MultiSelectMenuAssertion> assertionConsumer) {
		this.getComponent().getFindAndUpdateExistingItemsByLocator().click();
		assertionConsumer.accept(getComponent().getMultiSelectMenuComponent().assertThat());
		this.getComponent().getFindAndUpdateExistingItemsByLocator().click();
		return (A) this;
	}

	public A isDataOrCellTypeMappingSelected() {
		return assertAll("Data or cell type mapping should be selected",
				() -> assertThat(getComponent().getDataOrCellTypeMappingLocator()).isChecked());
	}

	public A isDataOrCellTypeMappingNotSelected() {
		return assertAll("Data or cell type mapping shouldn't be selected",
				() -> assertThat(getComponent().getDataOrCellTypeMappingLocator()).not().isChecked());
	}

	public A isMultipleFieldMappingsSelected() {
		return assertAll("Multiple field mappings should be selected",
				() -> assertThat(getComponent().getMultipleFieldMappingsLocator()).isChecked());
	}

	public A isMultipleFieldMappingsNotSelected() {
		return assertAll("Multiple field mappings shouldn't be selected",
				() -> assertThat(getComponent().getMultipleFieldMappingsLocator()).not().isChecked());
	}
}
