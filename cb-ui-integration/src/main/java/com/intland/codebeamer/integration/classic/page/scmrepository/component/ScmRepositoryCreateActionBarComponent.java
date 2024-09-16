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

package com.intland.codebeamer.integration.classic.page.scmrepository.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.scmrepository.ScmRepositoryNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ScmRepositoryCreateActionBarComponent extends
		AbstractCodebeamerComponent<ScmRepositoryCreateActionBarComponent, ScmRepositoryCreateActionBarAssertion> {

	private ScmRepositoryNavigation scmRepositoryNavigation;

	public ScmRepositoryCreateActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-center .actionBar");
		this.scmRepositoryNavigation = new ScmRepositoryNavigation(getCodebeamerPage());
	}

	public ScmRepositoryNavigation configure() {
		getScmTypeButton().click();
		return scmRepositoryNavigation;
	}

	public ScmRepositoryNavigation next() {
		getNewOrExistingButton().click();
		return scmRepositoryNavigation;
	}

	public ScmRepositoryNavigation finish() {
		getFinishButton().click();
		return scmRepositoryNavigation;
	}

	public ScmRepositoryNavigation back() {
		getBackButton().click();
		return scmRepositoryNavigation;
	}

	public ScmRepositoryNavigation cancel() {
		getCancelButton().click();
		return scmRepositoryNavigation;
	}

	public CodebeamerLocator getScmTypeButton() {
		return this.locator("input#submit-scm-type-button");
	}

	public CodebeamerLocator getNewOrExistingButton() {
		return this.locator("input#submit-new-or-existing");
	}

	public CodebeamerLocator getFinishButton() {
		return this.locator("input#finishButton");
	}

	public CodebeamerLocator getBackButton() {
		return this.locator("input.linkButton");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input.cancelButton");
	}

	@Override
	public ScmRepositoryCreateActionBarAssertion assertThat() {
		return new ScmRepositoryCreateActionBarAssertion(this);
	}

}
