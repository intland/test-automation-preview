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

public class ProjectBrowserProjectListAssertion extends
		AbstractProjectBrowserTabAssertions<ProjectBrowserProjectListComponent, ProjectBrowserProjectListAssertion> {

	ProjectBrowserProjectListAssertion(ProjectBrowserProjectListComponent component) {
		super(component);
	}

	public ProjectBrowserProjectListAssertion isProjectPartOfCategory(String projectName, String categoryName) {
		return assertAll("Project(%s) should be part of the '%s' category".formatted(projectName, categoryName), () -> assertThat(this.getComponent().getCategoryElementForProjectByName(projectName)).hasAttribute("data-name", categoryName));
	}

}
