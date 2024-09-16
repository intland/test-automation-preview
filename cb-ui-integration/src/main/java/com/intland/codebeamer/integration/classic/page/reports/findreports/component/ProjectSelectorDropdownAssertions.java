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

package com.intland.codebeamer.integration.classic.page.reports.findreports.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class ProjectSelectorDropdownAssertions
		extends AbstractCodebeamerComponentAssert<ProjectSelectorDropdownComponent, ProjectSelectorDropdownAssertions> {

	protected ProjectSelectorDropdownAssertions(ProjectSelectorDropdownComponent component) {
		super(component);
	}

	public ProjectSelectorDropdownAssertions assertProjectCheckbox(String projectName, boolean checked) {
		return assertAll("Selected project label value text should contain ",
				() -> assertThat(getComponent().getProjectCheckBoxElement(projectName)).isChecked(
						new LocatorAssertions.IsCheckedOptions().setChecked(checked)));
	}

	// UI-AUTOMATION TODO: Refactor this method to use a more stable HTML locator instead of relying on the label text.
	// Currently, this method depends on the label text to locate, which is not a reliable practice.
	// Due to technical constraints related to the JSP UI, we're using this approach temporarily.
	// Resolving this issue will require expertise in JSP UI handling to implement a more robust solution.
	public ProjectSelectorDropdownAssertions isProjectSelectorDropdownReady() {
		assertAll("Project selector dropdown is ready to use", () -> {
			assertThat(getComponent().getFilterElement()).isVisible();
			assertThat(getComponent().getClearAllButton("Clear All")).isVisible();
			assertThat(getComponent().getSharedReportsFromAnyProjectsCheckBoxElement()).isVisible();
			assertThat(getComponent().getSharedReportsFromAnyProjectsCheckBoxElement()).isEditable();
			assertThat(getComponent().getMyReportsCheckBoxElement()).isVisible();
			assertThat(getComponent().getMyReportsCheckBoxElement()).isEditable();
			assertThat(getComponent().getHeaderElementByLabel("Shared Reports from Recent Projects")).isVisible();
			assertThat(getComponent().getHeaderElementByLabel("Shared Reports from other Projects")).isVisible();
			assertThat(getComponent().getCloseButtonElement()).isVisible();
			assertThat(getComponent().getCloseButtonElement()).isEditable();
		});
		return this;
	}
}
