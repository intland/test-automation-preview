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

package com.intland.codebeamer.integration.classic.page.project.projectadmin;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectMembersNavigation {

	private CodebeamerPage codebeamerPage;

	private Project project;

	public ProjectMembersNavigation(CodebeamerPage codebeamerPage, Project project) {
		this.codebeamerPage = codebeamerPage;
		this.project = project;
	}

	@Action("redirectedToEditRolePage")
	public AddRolePage redirectedToEditRolePage() {
		return new AddRolePage(codebeamerPage, project).isActive();
	}
}
