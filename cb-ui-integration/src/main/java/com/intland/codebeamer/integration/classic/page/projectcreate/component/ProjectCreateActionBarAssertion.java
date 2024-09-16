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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class ProjectCreateActionBarAssertion extends
		AbstractCodebeamerComponentAssert<ProjectCreateActionBarComponent, ProjectCreateActionBarAssertion> {

	protected ProjectCreateActionBarAssertion(ProjectCreateActionBarComponent component) {
		super(component);
	}

	public ProjectCreateActionBarAssertion isNextStepButtonVisible() {
		return assertAll("Next step button should be present and visible",
				() -> assertThat(getComponent().getNextStepButton()).isVisible(createIsVisibleOptions()));
	}

	public ProjectCreateActionBarAssertion isFinishButtonVisible() {
		return assertAll("Finish button should be present and visible",
				() -> assertThat(getComponent().getFinishButton()).isVisible(createIsVisibleOptions()));
	}

	public ProjectCreateActionBarAssertion isBackButtonVisible() {
		return assertAll("Back button should be present and visible",
				() -> assertThat(getComponent().getBackButton()).isVisible(createIsVisibleOptions()));
	}

	public ProjectCreateActionBarAssertion isCancelButtonVisible() {
		return assertAll("Cancel button should be present and visible",
				() -> assertThat(getComponent().getCancelButton()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
