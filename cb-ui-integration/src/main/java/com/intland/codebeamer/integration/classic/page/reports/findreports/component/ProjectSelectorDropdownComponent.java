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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectSelectorDropdownComponent
		extends AbstractCodebeamerComponent<ProjectSelectorDropdownComponent, ProjectSelectorDropdownAssertions> {

	public ProjectSelectorDropdownComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div.ui-multiselect-menu.ui-widget.ui-widget-content.ui-corner-all.optionSelector");
	}

	public ProjectSelectorDropdownComponent setFilterProjectByName(String projectName) {
		getFilterElement().fill(projectName);
		return this;
	}

	public ProjectSelectorDropdownComponent selectProject(String projectName) {
		getProjectCheckBoxElement(projectName).click();
		return this;
	}

	public ProjectSelectorDropdownComponent clearAll() {
		getClearAllButton("Clear All").click();
		return this;
	}

	public ProjectSelectorDropdownComponent selectHeaderLabel(String label) {
		getHeaderElementByLabel(label).click();
		return this;
	}

	public CodebeamerLocator getFilterElement() {
		return this.locator("div.ui-multiselect-filter input[type='search']");
	}

	public CodebeamerLocator getProjectCheckBoxElement(String projectName) {
		return this.locator("label:has(span:text('%s')) input[type='checkbox']".formatted(projectName));
	}

	public CodebeamerLocator getMyReportsCheckBoxElement() {
		return this.locator("label[for='ui-multiselect-0-optionSelector-option-0']");
	}

	public CodebeamerLocator getSharedReportsFromAnyProjectsCheckBoxElement() {
		return this.locator("label[for='ui-multiselect-0-optionSelector-option-1']");
	}

	// UI-AUTOMATION TODO: Refactor this method to use a more stable HTML locator instead of relying on the label text.
	// Currently, this method depends on the label text to locate, which is not a reliable practice.
	// Due to technical constraints related to the JSP UI, we're using this approach temporarily.
	// Resolving this issue will require expertise in JSP UI handling to implement a more robust solution.
	public CodebeamerLocator getClearAllButton(String label) {
		return this.locator("ul.ui-helper-reset li:has-text('%s')".formatted(label));
	}

	// UI-AUTOMATION TODO: Refactor this method to use a more stable HTML locator instead of relying on the label text.
	// Currently, this method depends on the label text to locate, which is not a reliable practice.
	// Due to technical constraints related to the JSP UI, we're using this approach temporarily.
	// Resolving this issue will require expertise in JSP UI handling to implement a more robust solution.
	public CodebeamerLocator getHeaderElementByLabel(String label) {
		return this.locator("ul.ui-multiselect-optgroup:has(a:text('%s'))".formatted(label));
	}

	public CodebeamerLocator getCloseButtonElement() {
		return this.locator("li.ui-multiselect-close:has(a.ui-multiselect-close)");
	}

	@Override
	public ProjectSelectorDropdownAssertions assertThat() {
		return new ProjectSelectorDropdownAssertions(this);
	}
}
