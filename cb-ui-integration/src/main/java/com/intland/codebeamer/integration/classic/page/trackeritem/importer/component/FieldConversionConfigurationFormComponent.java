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
import com.intland.codebeamer.integration.classic.component.multiselect.MultiSelectMenuComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FieldConversionConfigurationFormComponent extends
		AbstractCodebeamerComponent<FieldConversionConfigurationFormComponent, FieldConversionConfigurationFormAssertions> {

	@Component(value = "Multiselect", includeInSitemap = false)
	private final MultiSelectMenuComponent multiSelectMenuComponent;

	public FieldConversionConfigurationFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#importForm");
		multiSelectMenuComponent = new MultiSelectMenuComponent(codebeamerPage);
	}

	@Override
	public FieldConversionConfigurationFormAssertions assertThat() {
		return new FieldConversionConfigurationFormAssertions(this);
	}

	public CodebeamerLocator getNextLocator() {
		return this.locator("input[name='_eventId_next']");
	}

	public CodebeamerLocator getBackLocator() {
		return this.locator("input[name='_eventId_back']");
	}

	public FieldConversionConfigurationFormComponent selectAccountMapping(String userId) {
		getSelectAccountMappingLocator().selectOption(userId);
		return this;
	}

	public CodebeamerLocator getSelectAccountMappingLocator() {
		return this.locator("select#defaultUserId");
	}

	public FieldConversionConfigurationFormComponent selectStatusMapping(String status) {
		getStatusMappingLocator().selectOption(status);
		return this;
	}

	private CodebeamerLocator getStatusMappingLocator() {
		return this.locator("select#defaultStatusId");
	}

	public FieldConversionConfigurationFormComponent selectStrictImport() {
		getStrictImportLocator().click();
		return this;
	}

	private CodebeamerLocator getStrictImportLocator() {
		return this.locator("input#strictImportForNewItems");
	}

	/**
	 * @param emptyCellsOfNewItems <ul>
	 *                             <li>true - Leave Field empty</li>
	 *                             <li>false - Set to the initial value configured in Tracker</li>
	 *                             </ul>
	 */
	public FieldConversionConfigurationFormComponent setEmptyCellsOfNewItems(boolean emptyCellsOfNewItems) {
		getEmptyCellsOfNewItemsLocator().selectOption(Boolean.toString(emptyCellsOfNewItems));
		return this;
	}

	public CodebeamerLocator getEmptyCellsOfNewItemsLocator() {
		return this.locator("select#keepEmptyImportData");
	}

	public FieldConversionConfigurationFormComponent selectImportFieldsForNewItems(String... names) {
		getImportFieldsForNewItemsLocator().click();
		multiSelectMenuComponent.select(names);
		getImportFieldsForNewItemsLocator().click();
		return this;
	}

	public CodebeamerLocator getImportFieldsForNewItemsLocator() {
		return this.locator("#importFieldsForNewItems_ms");
	}

	public FieldConversionConfigurationFormComponent selectImportFieldsForExistingItems(String... names) {
		getImportFieldsForExistingItemsLocator().click();
		multiSelectMenuComponent.select(names);
		getImportFieldsForExistingItemsLocator().click();
		return this;
	}

	public CodebeamerLocator getImportFieldsForExistingItemsLocator() {
		return this.locator("#importFieldsForExistingItems_ms");
	}

	public FieldConversionConfigurationFormComponent toggleAdvancedConversions() {
		this.locator("a.collapseToggle").click();
		return this;
	}

	public FieldConversionConfigurationFormComponent selectAdvancedConversionsSplitterWithRegexpPattern(int columnIndex,
			String pattern, boolean omitEmptyStrings, boolean trimEachResult) {
		selectSplitterConversion(columnIndex);
		selectConversionMode(columnIndex, "ByPattern");
		fillPattern(columnIndex, pattern);
		selectOmitEmptyString(columnIndex, omitEmptyStrings);
		selectTrimResult(columnIndex, trimEachResult);
		return this;
	}

	public FieldConversionConfigurationFormComponent selectAdvancedConversionsSplitterWithLineEnd(int columnIndex,
			boolean omitEmptyStrings, boolean trimEachResult) {
		selectSplitterConversion(columnIndex);
		selectConversionMode(columnIndex, "ByLine");
		selectOmitEmptyString(columnIndex, omitEmptyStrings);
		selectTrimResult(columnIndex, trimEachResult);
		return this;
	}

	public FieldConversionConfigurationFormComponent selectAdvancedConversionsSplitterWithSeparatorText(int columnIndex,
			String separator, boolean omitEmptyStrings, boolean trimEachResult) {
		selectSplitterConversion(columnIndex);
		selectConversionMode(columnIndex, "BySeparator");
		fillSeparator(columnIndex, separator);
		selectOmitEmptyString(columnIndex, omitEmptyStrings);
		selectTrimResult(columnIndex, trimEachResult);
		return this;
	}

	private void fillPattern(int columnIndex, String pattern) {
		this.locator((getConversionsInputId(columnIndex, "splitPattern"))).fill(pattern);
	}

	private void fillSeparator(int columnIndex, String separator) {
		this.locator(getConversionsInputId(columnIndex, "separator")).fill(separator);
	}

	private void selectConversionMode(int columnIndex, String mode) {
		this.locator(getConversionsInputId(columnIndex, "mode")).selectOption(mode);
	}

	private void selectTrimResult(int columnIndex, boolean trimEachResult) {
		this.locator(getConversionsInputId(columnIndex, "trimResults1")).check(trimEachResult);
	}

	private void selectOmitEmptyString(int columnIndex, boolean omitEmptyStrings) {
		this.locator(getConversionsInputId(columnIndex, "ommitEmptyStrings1")).check(omitEmptyStrings);
	}

	private String getConversionsInputId(int columnIndex, String idSuffix) {
		return "#conversions%d\\.availableImportValueConverters0\\.%s".formatted(Integer.valueOf(columnIndex), idSuffix);
	}

	private void selectSplitterConversion(int columnIndex) {
		this.locator("#conversions%d\\.selectedConversionId".formatted(Integer.valueOf(columnIndex))).selectOption("Splitter");
	}

}
