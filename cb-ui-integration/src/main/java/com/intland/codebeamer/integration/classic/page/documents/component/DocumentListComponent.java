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

package com.intland.codebeamer.integration.classic.page.documents.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.documents.UploadDocumentPage;
import com.intland.codebeamer.integration.classic.page.documents.navigation.ProjectDocumentsPageNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DocumentListComponent extends AbstractCodebeamerComponent<DocumentListComponent, DocumentListAssertions> {

	private ProjectDocumentsPageNavigation projectDocumentPageNavigation;

	public DocumentListComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, "#centerDiv");
		this.projectDocumentPageNavigation = new ProjectDocumentsPageNavigation(codebeamerPage, project);
	}

	public UploadDocumentPage addNewFile() {
		getNewButton().click();
		getNewFileElement().click();
		return projectDocumentPageNavigation.redirectedToUploadDocumentPage();
	}

	public CodebeamerLocator getNewFileElement() {
		// UI-AUTOMATION: on project documents page, new file button needs a proper identifier
		return getToolbarElement().concat("a:text-is('File')");
	}

	public CodebeamerLocator getNewButton() {
		return getToolbarElement().concat(".new-documents-button");
	}

	public CodebeamerLocator getToolbarElement() {
		return this.locator("#middleHeaderDiv");
	}

	@Override
	public DocumentListAssertions assertThat() {
		return new DocumentListAssertions(this);
	}
}
