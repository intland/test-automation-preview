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

package com.intland.codebeamer.integration.classic.component.fileimport;

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FileImportFormComponent extends AbstractCodebeamerComponent<FileImportFormComponent, FileImportFormAssertions> {

	public FileImportFormComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	@Override
	public FileImportFormAssertions assertThat() {
		return new FileImportFormAssertions(this);
	}

	public FileImportFormComponent addFile(Path path) {
		getCodebeamerPage().uploadFiles(() -> getFileInputLocator().click(), path);
		return this;
	}

	private CodebeamerLocator getFileInputLocator() {
		return this.locator("input[type='file'][name='file']");
	}

	public CodebeamerLocator getBreadcrumbIconLocator() {
		return this.locator("img");
	}
}
