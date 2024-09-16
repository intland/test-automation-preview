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

package com.intland.codebeamer.integration.classic.page.documents.navigation;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.documents.UploadDocumentPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectDocumentsPageNavigation {

	private CodebeamerPage codebeamerPage;

	private Project project;

	public ProjectDocumentsPageNavigation(CodebeamerPage codebeamerPage, Project project) {
		this.codebeamerPage = codebeamerPage;
		this.project = project;
	}

	@Action("redirectedToUploadDocumentPage")
	public UploadDocumentPage redirectedToUploadDocumentPage() {
		return new UploadDocumentPage(codebeamerPage, project).isActive();
	}
}
