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
public class ScmRepositoryCreateActionBarAssertion extends
		AbstractCodebeamerComponentAssert<ScmRepositoryCreateActionBarComponent, ScmRepositoryCreateActionBarAssertion> {

	protected ScmRepositoryCreateActionBarAssertion(ScmRepositoryCreateActionBarComponent component) {
		super(component);
	}

	public ScmRepositoryCreateActionBarAssertion isSubmitScmTypeButtonVisible() {
		return assertAll("Configure button for the ScmType selection should be present and visible",
				() -> assertThat(getComponent().getScmTypeButton()).isVisible());
	}

	public ScmRepositoryCreateActionBarAssertion isNewOrExistingButtonVisible() {
		return assertAll("Next button for the NewOrExisting repository selection should be present and visible",
				() -> assertThat(getComponent().getNewOrExistingButton()).isVisible());
	}

	public ScmRepositoryCreateActionBarAssertion isFinishButtonVisible() {
		return assertAll("Finish button should be present and visible",
				() -> assertThat(getComponent().getFinishButton()).isVisible());
	}

	public ScmRepositoryCreateActionBarAssertion isBackButtonVisible() {
		return assertAll("Back button should be present and visible",
				() -> assertThat(getComponent().getBackButton()).isVisible());
	}

	public ScmRepositoryCreateActionBarAssertion isCancelButtonVisible() {
		return assertAll("Cancel button should be present and visible",
				() -> assertThat(getComponent().getCancelButton()).isVisible());
	}

}
