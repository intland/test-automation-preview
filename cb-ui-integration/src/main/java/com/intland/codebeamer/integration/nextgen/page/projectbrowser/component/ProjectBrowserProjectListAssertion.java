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

package com.intland.codebeamer.integration.nextgen.page.projectbrowser.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ProjectBrowserProjectListAssertion extends
		AbstractCodebeamerComponentAssert<ProjectBrowserProjectListComponent, ProjectBrowserProjectListAssertion> {

	ProjectBrowserProjectListAssertion(ProjectBrowserProjectListComponent component) {
		super(component);
	}

	public ProjectBrowserProjectListAssertion isProjectPartOfCategory(String projectName, String categoryName) {
		 return assertAll("Project(%s) should be part of the '%s' category".formatted(projectName, categoryName), () -> assertThat(this.getComponent().getCategoryForProjectByName(projectName)).hasText(categoryName));
	}

}
