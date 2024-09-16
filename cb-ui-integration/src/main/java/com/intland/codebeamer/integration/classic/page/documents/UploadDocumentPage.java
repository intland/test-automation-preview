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
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.documents.component.UploadDocumentFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("UploadDocumentPage")
public class UploadDocumentPage extends AbstractCodebeamerPage<UploadDocumentPage> {

	private static final String PATH = "proj/doc/upload.spr";

	@Component("Upload document form")
	private UploadDocumentFormComponent uploadDocumentComponent;

	@Component("Overlay messages")
	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	public UploadDocumentPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.uploadDocumentComponent = new UploadDocumentFormComponent(codebeamerPage, project);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(codebeamerPage);
	}

	@Action("Visit project upload document page")
	public UploadDocumentPage visit() {
		navigate(PATH);
		return isActive();
	}

	@Override
	public UploadDocumentPage isActive() {
		assertUrl(PATH, "Upload documents page should be the active page");
		return this;
	}

	public UploadDocumentPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public UploadDocumentPage uploadDocumentComponent(Consumer<UploadDocumentFormComponent> uploadFormConsumer) {
		uploadFormConsumer.accept(uploadDocumentComponent);
		return this;
	}
}
