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

public class ProjectCreateGeneralSettingsFormAssertion extends
		AbstractCodebeamerComponentAssert<ProjectCreateGeneralSettingsFormComponent, ProjectCreateGeneralSettingsFormAssertion> {

	protected ProjectCreateGeneralSettingsFormAssertion(ProjectCreateGeneralSettingsFormComponent component) {
		super(component);
	}

	public ProjectCreateGeneralSettingsFormAssertion isStepContentVisible() {
		return assertAll("Project general settings content should be present and visible",
				() -> assertThat(getComponent().getComponentElement()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
