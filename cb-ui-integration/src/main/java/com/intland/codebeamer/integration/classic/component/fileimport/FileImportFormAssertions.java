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

import static org.testng.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;

import com.intland.codebeamer.integration.api.service.utility.DocumentFileInfo;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class FileImportFormAssertions
		extends AbstractCodebeamerComponentAssert<FileImportFormComponent, FileImportFormAssertions> {

	protected FileImportFormAssertions(FileImportFormComponent component) {
		super(component);
	}

	public FileImportFormAssertions isUploadedIconEquals(DocumentFileInfo sourceIcon) {
		String imageSrc = getComponent().getBreadcrumbIconLocator().getAttribute("src");
		DocumentFileInfo downloadedFile = getComponent().getCodebeamerPage()
				.getDataManagerService().getUtilityApiService()
				.downloadDocumentFile(Integer.valueOf(StringUtils.substringAfter(imageSrc, "doc_id=")));

		return assertAll("Uploaded file should be equal to source file",
				() -> {
					assertThat(getComponent().getBreadcrumbIconLocator()).isVisible();
					assertEquals(downloadedFile, sourceIcon);
				});
	}
}
