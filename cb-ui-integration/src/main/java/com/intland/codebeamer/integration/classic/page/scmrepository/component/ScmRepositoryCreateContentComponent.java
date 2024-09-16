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
public class ScmRepositoryCreateContentComponent extends
		AbstractCodebeamerComponent<ScmRepositoryCreateContentComponent, ScmRepositoryCreateContentAssertion> {

	private ScmRepositoryNavigation scmRepositoryNavigation;

	public ScmRepositoryCreateContentComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-center .contentWithMargins");
		this.scmRepositoryNavigation = new ScmRepositoryNavigation(getCodebeamerPage());
	}

	public ScmRepositoryNavigation repositoryName(String repositoryName) {
		getRepositoryNameTextField().fill(repositoryName);
		return scmRepositoryNavigation;
	}

	public CodebeamerLocator getRepositoryNameTextField() {
		return this.locator("input#repositoryName");
	}

	@Override
	public ScmRepositoryCreateContentAssertion assertThat() {
		return new ScmRepositoryCreateContentAssertion(this);
	}

}
