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

package com.intland.codebeamer.integration.classic.page.projectcreate.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectCreateSelectKindOfProjectFormComponent extends
		AbstractCodebeamerComponent<ProjectCreateSelectKindOfProjectFormComponent, ProjectCreateSelectKindOfProjectFormAssertion> {

	private static final String LOCATOR = "selectKindOfNewProjectContent";

	public ProjectCreateSelectKindOfProjectFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, String.format("div[data-testid='%s']", LOCATOR));
	}

	public CodebeamerLocator getNewProjectSelector() {
		return this.locator("input#newProject[name='kindOfNewProject']");
	}

	public CodebeamerLocator getNewProjectTemplateSelector() {
		return this.locator("select#templateProjectSelector[name='templateProjId']");
	}

	public CodebeamerLocator getComponentElement() {
		return this.locatorByTestId(LOCATOR);
	}

	@Override
	public ProjectCreateSelectKindOfProjectFormAssertion assertThat() {
		return new ProjectCreateSelectKindOfProjectFormAssertion(this);
	}
}
