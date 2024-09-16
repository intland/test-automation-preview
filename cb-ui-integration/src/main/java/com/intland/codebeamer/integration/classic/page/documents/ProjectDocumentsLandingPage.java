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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("ProjectDocumentsLandingPage")
public class ProjectDocumentsLandingPage extends AbstractProjectDocumentsPage<ProjectDocumentsLandingPage> {

	public ProjectDocumentsLandingPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, project);
	}

	protected String getPath() {
		return "project/%s/documents".formatted(project.id().id());
	}
}
