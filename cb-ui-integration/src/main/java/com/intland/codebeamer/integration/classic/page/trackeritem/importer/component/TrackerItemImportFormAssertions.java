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
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.CharsetName;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.FieldSeparator;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemImportFormAssertions
		extends AbstractCodebeamerComponentAssert<TrackerItemImportFormComponent, TrackerItemImportFormAssertions> {

	protected TrackerItemImportFormAssertions(TrackerItemImportFormComponent component) {
		super(component);
	}

	public TrackerItemImportFormAssertions isMsWordSelected() {
		return isSelected("Ms Word", getComponent().getMsWordLocator());
	}

	public TrackerItemImportFormAssertions isMsWordNotSelected() {
		return isNotSelected("Ms Word", getComponent().getMsWordLocator());
	}

	public TrackerItemImportFormAssertions isMsWordTablesSelected() {
		return isSelected("Ms Word Tables", getComponent().getMsWordTablesLocator());
	}

	public TrackerItemImportFormAssertions isMsWordTablesNotSelected() {
		return isNotSelected("Ms Word Tables", getComponent().getMsWordTablesLocator());
	}

	public TrackerItemImportFormAssertions isMsExcelSelected() {
		return isSelected("Ms Excel", getComponent().getMsExcelLocator());
	}

	public TrackerItemImportFormAssertions isMsExcelNotSelected() {
		return isNotSelected("Ms Excel", getComponent().getMsExcelLocator());
	}

	public TrackerItemImportFormAssertions isMsProjectSelected() {
		return isSelected("Ms Project", getComponent().getMsProjectLocator());
	}

	public TrackerItemImportFormAssertions isMsProjectNotSelected() {
		return isNotSelected("Ms Project", getComponent().getMsProjectLocator());
	}

	public TrackerItemImportFormAssertions isMsExcelCsvSelected() {
		return isSelected("Ms Excel CSV", getComponent().getMsExcelCsvLocator());
	}

	public TrackerItemImportFormAssertions isMsExcelCsvNotSelected() {
		return isNotSelected("Ms Excel CSV", getComponent().getMsExcelCsvLocator());
	}

	public TrackerItemImportFormAssertions isFieldSeparator(FieldSeparator fieldSeparator) {
		return assertAll("Field separator should be '%s'".formatted(fieldSeparator),
				() -> assertThat(getComponent().getFieldSeparatorLocator()).hasValue(fieldSeparator.getValue()));
	}

	public TrackerItemImportFormAssertions isCharset(CharsetName charset) {
		return assertAll("Charset should be '%s'".formatted(charset),
				() -> assertThat(getComponent().getCharsetLocator()).hasValue(charset.getValue()));
	}

	private TrackerItemImportFormAssertions isSelected(String fileFormat, CodebeamerLocator locator) {
		return assertAll("%s should be selected".formatted(fileFormat), () -> assertThat(locator).isChecked());
	}

	private TrackerItemImportFormAssertions isNotSelected(String fileFormat, CodebeamerLocator locator) {
		return assertAll("%s shouldn't be selected".formatted(fileFormat), () -> assertThat(locator).not().isChecked());
	}

	public TrackerItemImportFormAssertions isOnlyOneFileFormatSelected() {
		return assertAll("Only one file format should be selected",
				() -> assertThat(getComponent().getSelectedFileFormatLocator()).hasCount(1));
	}

}
