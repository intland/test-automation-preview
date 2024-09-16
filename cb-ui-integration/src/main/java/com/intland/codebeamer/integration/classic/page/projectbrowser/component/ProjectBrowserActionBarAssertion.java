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

package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class ProjectBrowserActionBarAssertion extends
		AbstractCodebeamerComponentAssert<ProjectBrowserActionBarComponent, ProjectBrowserActionBarAssertion> {
	protected ProjectBrowserActionBarAssertion(ProjectBrowserActionBarComponent component) {
		super(component);
	}

	public ProjectBrowserActionBarAssertion isNewProjectButtonVisible() {
		return assertAll("New Project button should be present and visible",
				() -> assertThat(getComponent().getNewProjectButton()).isVisible(createIsVisibleOptions()));
	}

	public ProjectBrowserActionBarAssertion isNewProjectButtonHidden() {
		return assertAll("New Project button should be hidden",
				() -> assertThat(getComponent().getNewProjectButton()).isHidden());
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
