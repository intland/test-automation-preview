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
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public class ScmRepositoryMainContentComponent extends
		AbstractCodebeamerComponent<ScmRepositoryMainContentComponent, ScmRepositoryMainContentAssertion> {

	public ScmRepositoryMainContentComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-center .contentWithMargins");
	}

	public CodebeamerLocator getWarningContainer() {
		return this.locator("div.warning");
	}

	public CodebeamerLocator getRepositoryCardContainer() {
		return this.locator("div.repositoryCard");
	}
	@Override
	public ScmRepositoryMainContentAssertion assertThat() {
		return new ScmRepositoryMainContentAssertion(this);
	}

}
