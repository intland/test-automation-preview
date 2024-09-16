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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;
import com.intland.codebeamer.integration.classic.component.multiselect.MultiSelectMenuComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.NumberFormat;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.TimeZone;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AssignColumnFormComponent<C extends AssignColumnFormComponent<C, A>, A extends AssignColumnFormAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private final MultiSelectMenuComponent multiSelectMenuComponent;

	public AssignColumnFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#importForm");
		this.multiSelectMenuComponent = new MultiSelectMenuComponent(codebeamerPage);
	}

	public void selectCancel() {
		this.locator("input[name='_eventId_cancel']").click();
	}

	public void selectNext() {
		this.locator("input[name='_eventId_next']").click();
	}

	public void selectBack() {
		this.locator("input[name='_eventId_back']").click();
	}

	public C selectDateFormat(DateFormat dateFormat) {
		getDateFormatLocator().selectOption(dateFormat.getValue());
		return (C) this;
	}

	public CodebeamerLocator getDateFormatLocator() {
		return this.locator("select#dateFormat");
	}

	public C selectTimeZone(TimeZone timeZone) {
		getTimeZoneLocator().selectOption(timeZone.getValue());
		return (C) this;
	}

	public CodebeamerLocator getTimeZoneLocator() {
		return this.locator("select#timeZone");
	}

	public C selectNumberFormat(NumberFormat format) {
		getNumberFormatLocator().selectOption(format.getValue());
		return (C) this;
	}

	public CodebeamerLocator getNumberFormatLocator() {
		return this.locator("select#numberFormat");
	}

	public C selectDescriptionFormat(WikiTextType format) {
		getDescriptionFormatLocator().selectOption(getValueForDescFormat(format));
		return (C) this;
	}

	public CodebeamerLocator getDescriptionFormatLocator() {
		return this.locator("select#defaultDescriptionFormat");
	}

	public C fillSkipDataOrCell(String value) {
		this.getSkipDataOrCellLocator().fill(value);
		return (C) this;
	}

	public CodebeamerLocator getSkipDataOrCellLocator() {
		return this.locator("input#doNotModifyValue");
	}

	public C selectFindAndUpdateExistingItems(int... indices) {
		getFindAndUpdateExistingItemsByLocator().click();
		this.multiSelectMenuComponent.select(indices);
		getFindAndUpdateExistingItemsByLocator().click();
		return (C) this;
	}

	public C selectFindAndUpdateExistingItems(String... names) {
		getFindAndUpdateExistingItemsByLocator().click();
		this.multiSelectMenuComponent.select(names);
		getFindAndUpdateExistingItemsByLocator().click();
		return (C) this;
	}

	public CodebeamerLocator getFindAndUpdateExistingItemsByLocator() {
		return this.locator("button#idFields_ms");
	}

	public C selectDataOrCellTypeMapping() {
		this.getDataOrCellTypeMappingLocator().click();
		return (C) this;
	}

	public CodebeamerLocator getDataOrCellTypeMappingLocator() {
		return this.locator("input#strictDataTypeMapping");
	}

	public C selectMultipleFieldMappings() {
		this.getMultipleFieldMappingsLocator().click();
		return (C) this;
	}

	public CodebeamerLocator getMultipleFieldMappingsLocator() {
		return this.locator("input#allowMultiMapping");
	}

	public C clearAllMappings() {
		this.getClearAllMappingsLocator().click();
		return (C) this;
	}

	public CodebeamerLocator getClearAllMappingsLocator() {
		return this.locator("input#clearAllMappingsButton");
	}

	public C selectColumnMapping(int columnIndex, String... names) {
		this.getColumnMappingLocator(columnIndex).click();
		this.multiSelectMenuComponent.select(names);
		return (C) this;
	}

	public C selectColumnMapping(int columnIndex, int... indices) {
		this.getColumnMappingLocator(columnIndex).click();
		this.multiSelectMenuComponent.select(indices);
		return (C) this;
	}

	public String getValueForDescFormat(WikiTextType format) {
		return switch (format) {
			case WIKI -> "W";
			case HTML -> "H";
			case PLAINTEXT -> "";
		};
	}

	private CodebeamerLocator getColumnMappingLocator(int columnIndex) {
		return this.locator("#rawDataTable button.ui-multiselect >> nth=%d".formatted(Integer.valueOf(columnIndex)));
	}

	public MultiSelectMenuComponent getMultiSelectMenuComponent() {
		return multiSelectMenuComponent;
	}
}
