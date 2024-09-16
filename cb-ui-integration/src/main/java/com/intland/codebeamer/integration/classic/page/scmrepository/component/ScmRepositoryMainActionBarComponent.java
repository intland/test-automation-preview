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

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public class ScmRepositoryMainActionBarComponent extends
		AbstractCodebeamerComponent<ScmRepositoryMainActionBarComponent, ScmRepositoryMainActionBarAssertion> {

	private ScmRepositoryNavigation scmRepositoryNavigation;

	public ScmRepositoryMainActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-center .actionBar");
		this.scmRepositoryNavigation = new ScmRepositoryNavigation(getCodebeamerPage());
	}

	public ScmRepositoryNavigation newRepository() {
		getNewRepositoryButton().click();
		return scmRepositoryNavigation;
	}

	public CodebeamerLocator getNewRepositoryButton() {
		return this.locator("a.actionLink#create-repository-link");
	}

	public CodebeamerLocator getPageContent() {
		return this.locator("div.contentWithMargins");
	}

	@Override
	public ScmRepositoryMainActionBarAssertion assertThat() {
		return new ScmRepositoryMainActionBarAssertion(this);
	}

}
