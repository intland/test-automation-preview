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
import com.intland.codebeamer.integration.classic.page.projectcreate.ProjectCreateNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectCreateActionBarComponent extends
		AbstractCodebeamerComponent<ProjectCreateActionBarComponent, ProjectCreateActionBarAssertion> {

	private ProjectCreateNavigation projectCreateNavigation;

	public ProjectCreateActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".actionBar");
		this.projectCreateNavigation = new ProjectCreateNavigation(getCodebeamerPage());
	}

	public CodebeamerLocator getNextStepButton() {
		return this.locator("input#nextStepButton");
	}

	public CodebeamerLocator getFinishButton() {
		return this.locator("input#finishButton");
	}

	public CodebeamerLocator getBackButton() {
		return this.locator("input#backButton");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input.cancelButton");
	}

	public ProjectCreateNavigation next() {
		getNextStepButton().click();
		return projectCreateNavigation;
	}

	public ProjectCreateNavigation finish() {
		getCodebeamerPage().setDefaultTimeout(15);
		getFinishButton().click();
		return projectCreateNavigation;
	}

	@Override
	public ProjectCreateActionBarAssertion assertThat() {
		return new ProjectCreateActionBarAssertion(this);
	}
}
