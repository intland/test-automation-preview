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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public class ScmRepositoryMainContentAssertion extends
		AbstractCodebeamerComponentAssert<ScmRepositoryMainContentComponent, ScmRepositoryMainContentAssertion> {

	protected ScmRepositoryMainContentAssertion(ScmRepositoryMainContentComponent component) {
		super(component);
	}

	public ScmRepositoryMainContentAssertion isWarningMessageVisible() {
		return assertAll("Warning message should be present and visible",
				() -> assertThat(getComponent().getWarningContainer()).isVisible());
	}

	public ScmRepositoryMainContentAssertion isRepositoryCardVisible() {
		return assertAll("RepositoryCard should be present and visible",
				() -> assertThat(getComponent().getRepositoryCardContainer()).isVisible());
	}

}
