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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.CharsetName;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.FieldSeparator;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemImportFormComponent
		extends AbstractCodebeamerComponent<TrackerItemImportFormComponent, TrackerItemImportFormAssertions> {

	public TrackerItemImportFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#importForm");
	}

	@Override
	public TrackerItemImportFormAssertions assertThat() {
		return new TrackerItemImportFormAssertions(this);
	}

	public TrackerItemImportFormComponent addFile(Path path) {
		getCodebeamerPage().uploadFiles(() -> getFileInputLocator().click(), path);
		return this;
	}

	public TrackerItemImportFormComponent cancelUpload() {
		getRemoveUploadLinkElement().click();
		return this;
	}

	public TrackerItemImportFormComponent selectMsWord() {
		getMsWordLocator().click();
		return this;
	}

	public CodebeamerLocator getMsWordLocator() {
		return this.locator("input#fileFormat_word");
	}

	public TrackerItemImportFormComponent selectMsWordTables() {
		getMsWordTablesLocator().click();
		return this;
	}

	public CodebeamerLocator getMsWordTablesLocator() {
		return this.locator("input#fileFormat_wordTable");
	}

	public TrackerItemImportFormComponent selectMsExcel() {
		getMsExcelLocator().click();
		return this;
	}

	public CodebeamerLocator getMsExcelLocator() {
		return this.locator("input#fileFormat_excel");
	}

	public TrackerItemImportFormComponent selectMsProject() {
		getMsProjectLocator().click();
		return this;
	}

	public CodebeamerLocator getMsProjectLocator() {
		return this.locator("input#fileFormat_msProject");
	}

	public TrackerItemImportFormComponent selectMsExcelCsv() {
		getMsExcelCsvLocator().click();
		return this;
	}

	public CodebeamerLocator getMsExcelCsvLocator() {
		return this.locator("input#fileFormat_excelCSV");
	}

	public TrackerItemImportFormComponent selectFieldSeparator(FieldSeparator fieldSeparator) {
		getFieldSeparatorLocator().selectOption(fieldSeparator.getValue());
		return this;
	}

	public CodebeamerLocator getFieldSeparatorLocator() {
		return this.locator("select#fieldSeparator");
	}

	public TrackerItemImportFormComponent selectCharset(CharsetName charsetName) {
		getCharsetLocator().selectOption(charsetName.getValue());
		return this;
	}

	public CodebeamerLocator getCharsetLocator() {
		return this.locator("select#charsetName");
	}

	public CodebeamerLocator getSelectedFileFormatLocator() {
		return this.locator("input[name=fileFormat]:checked");
	}

	private CodebeamerLocator getFileInputLocator() {
		return this.locator("input[type='file'][name='file']");
	}

	public CodebeamerLocator getRemoveUploadLinkElement() {
		return this.locator("a.qq-upload-remove");
	}

	public CodebeamerLocator getNextLocator() {
		return this.locator("input#_eventId_next");
	}
}