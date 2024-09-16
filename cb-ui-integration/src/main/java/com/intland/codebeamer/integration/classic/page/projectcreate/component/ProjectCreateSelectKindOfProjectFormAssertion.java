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

public class ProjectCreateSelectKindOfProjectFormAssertion extends
		AbstractCodebeamerComponentAssert<ProjectCreateSelectKindOfProjectFormComponent, ProjectCreateSelectKindOfProjectFormAssertion> {

	protected ProjectCreateSelectKindOfProjectFormAssertion(ProjectCreateSelectKindOfProjectFormComponent component) {
		super(component);
	}

	public ProjectCreateSelectKindOfProjectFormAssertion isNewProjectSelectorVisible() {
		return assertAll("Project kind selector input should be present and visible",
				() -> assertThat(getComponent().getNewProjectSelector()).isVisible(createIsVisibleOptions()));
	}

	public ProjectCreateSelectKindOfProjectFormAssertion isNewProjectSelectorSelected() {
		return assertAll("Project kind selector input should be present and visible",
				() -> assertThat(getComponent().getNewProjectSelector()).isChecked());
	}

	public ProjectCreateSelectKindOfProjectFormAssertion isNewProjectTemplateSelectorVisible() {
		return assertAll("Project kind selector input should be present and visible",
				() -> assertThat(getComponent().getNewProjectTemplateSelector()).isVisible(createIsVisibleOptions()));
	}

	public ProjectCreateSelectKindOfProjectFormAssertion isNewProjectTemplateDefaultSelected() {
		return assertAll("Project kind selector input should be present and visible",
				() -> assertThat(getComponent().getNewProjectTemplateSelector()).hasValue("-1"));
	}

	public ProjectCreateSelectKindOfProjectFormAssertion isStepContentVisible() {
		return assertAll("Project kind selection content should be present and visible",
				() -> assertThat(getComponent().getComponentElement()).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}

}
