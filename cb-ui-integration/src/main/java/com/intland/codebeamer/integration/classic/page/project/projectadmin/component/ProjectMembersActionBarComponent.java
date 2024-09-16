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

package com.intland.codebeamer.integration.classic.page.project.projectadmin.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.ProjectMembersNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectMembersActionBarComponent extends
		AbstractCodebeamerComponent<ProjectMembersActionBarComponent, ProjectMembersActionBarAssertion> {

	private Project project;

	public ProjectMembersActionBarComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, "#headerDiv");
		this.project = project;
	}

	public CodebeamerLocator getAddRoleButton() {
		return this.locator("a.addRole");
	}

	public ProjectMembersNavigation addRole() {
		getAddRoleButton().click();
		return new ProjectMembersNavigation(getCodebeamerPage(), project);
	}

	@Override
	public ProjectMembersActionBarAssertion assertThat() {
		return new ProjectMembersActionBarAssertion(this);
	}
}
