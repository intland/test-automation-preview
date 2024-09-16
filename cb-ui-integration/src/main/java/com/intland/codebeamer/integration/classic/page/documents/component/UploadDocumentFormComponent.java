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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.documents.ProjectDocumentsPage;
import com.intland.codebeamer.integration.classic.page.documents.navigation.UploadDocumentPageNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class UploadDocumentFormComponent
		extends AbstractCodebeamerComponent<UploadDocumentFormComponent, UploadDocumentFormAssertions> {

	private UploadDocumentPageNavigation uploadDocumentPageNavigation;

	private Project project;

	public UploadDocumentFormComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, "#DocumentAttributesForm");
		this.uploadDocumentPageNavigation = new UploadDocumentPageNavigation(codebeamerPage);
		this.project = project;
	}

	public ProjectDocumentsPage save() {
		getUploadButton().click();
		return uploadDocumentPageNavigation.redirectedToProjectDocumentsPage(project);
	}

	public ProjectDocumentsPage cancel() {
		getCancelButton().click();
		return uploadDocumentPageNavigation.redirectedToProjectDocumentsPage(project);
	}

	public UploadDocumentFormComponent uploadFile(Path document) {
		getCodebeamerPage().uploadFiles(() -> getFileInputElement().click(), document);
		return this;
	}

	public UploadDocumentFormComponent uploadFile(Path... document) {
		getCodebeamerPage().uploadFiles(() -> getFileInputElement().click(), document);
		return this;
	}

	public CodebeamerLocator getUploadButton() {
		return getToolbarElement().concat("input[name='SAVE']");
	}

	public CodebeamerLocator getCancelButton() {
		return getToolbarElement().concat("input.cancelButton");
	}

	public CodebeamerLocator getToolbarElement() {
		return this.locator(".actionBar");
	}

	private CodebeamerLocator getFileInputElement() {
		return this.locator("input[type='file'][name='file']");
	}

	@Override
	public UploadDocumentFormAssertions assertThat() {
		return new UploadDocumentFormAssertions(this);
	}
}
