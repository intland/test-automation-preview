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

package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.projectcreate.ProjectCreateNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectBrowserActionBarComponent extends
		AbstractCodebeamerComponent<ProjectBrowserActionBarComponent, ProjectBrowserActionBarAssertion> {

	private ProjectCreateNavigation projectCreateNavigation;

	public ProjectBrowserActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#left-content");
		this.projectCreateNavigation = new ProjectCreateNavigation(getCodebeamerPage());
	}

	public ProjectCreateNavigation createNewProject() {
		getNewProjectButton().click();
		return projectCreateNavigation;
	}

	public CodebeamerLocator getNewProjectButton() {
		return this.locator("a.actionLink[id='create-project-link']");
	}

	@Override
	public ProjectBrowserActionBarAssertion assertThat() {
		return new ProjectBrowserActionBarAssertion(this);
	}
}
