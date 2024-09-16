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

package com.intland.codebeamer.integration.classic.page.documents;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.documents.component.DocumentListComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public abstract class AbstractProjectDocumentsPage<P extends AbstractProjectDocumentsPage> extends AbstractCodebeamerPage<P> {

	protected Project project;

	@Component("Documents table")
	private DocumentListComponent documentListComponent;

	public AbstractProjectDocumentsPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.documentListComponent = new DocumentListComponent(codebeamerPage, project);
		this.project = project;
	}

	@Action("Visit project documents page")
	public P visit() {
		navigate(getPath());
		return isActive();
	}

	@Override
	public P isActive() {
		assertUrl(getPath(), "Project documents page should be the active page");
		return (P) this;
	}

	public DocumentListComponent getDocumentListComponent() {
		return documentListComponent;
	}

	public P documentListComponent(Consumer<DocumentListComponent> docListConsumer) {
		docListConsumer.accept(documentListComponent);
		return (P) this;
	}

	protected abstract String getPath();
}
