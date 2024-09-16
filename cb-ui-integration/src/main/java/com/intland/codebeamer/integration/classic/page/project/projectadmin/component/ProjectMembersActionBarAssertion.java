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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ProjectMembersActionBarAssertion extends
		AbstractCodebeamerComponentAssert<ProjectMembersActionBarComponent, ProjectMembersActionBarAssertion> {

	protected ProjectMembersActionBarAssertion(ProjectMembersActionBarComponent component) {
		super(component);
	}

	public ProjectMembersActionBarAssertion isReady() {
		return assertAll("Add role button should be present and visible", () -> {
			assertThat(getComponent().getAddRoleButton()).isVisible();
		});
	}
}
