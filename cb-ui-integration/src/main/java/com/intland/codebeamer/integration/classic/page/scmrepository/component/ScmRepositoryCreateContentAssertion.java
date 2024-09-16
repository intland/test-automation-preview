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
public class ScmRepositoryCreateContentAssertion extends
		AbstractCodebeamerComponentAssert<ScmRepositoryCreateContentComponent, ScmRepositoryCreateContentAssertion> {

	protected ScmRepositoryCreateContentAssertion(ScmRepositoryCreateContentComponent component) {
		super(component);
	}

	public ScmRepositoryCreateContentAssertion isRepositoryNameTextFieldVisible() {
		return assertAll("Repository Name text field should be present and visible",
				() -> assertThat(getComponent().getRepositoryNameTextField()).isVisible());
	}

}
