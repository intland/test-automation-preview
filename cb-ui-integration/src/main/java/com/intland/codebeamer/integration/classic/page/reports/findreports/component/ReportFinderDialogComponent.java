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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReportFinderDialogComponent
		extends AbstractCodebeamerComponent<ReportFinderDialogComponent, ReportFinderDialogAssertions> {

	@Component("Project selector dropdown component")
	private ProjectSelectorDropdownComponent projectSelectorDropdownComponent;

	public ReportFinderDialogComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div.pickerTypeSelectorContainer");
		this.projectSelectorDropdownComponent = new ProjectSelectorDropdownComponent(codebeamerPage, frameLocator);
	}

	public ReportFinderDialogComponent selectProjectSelectorDropDown() {
		this.getSelectProjectButton().click();
		return this;
	}

	public ReportFinderDialogComponent projectSelectorDropdownComponent(Consumer<ProjectSelectorDropdownComponent> formConsumer) {
		selectProjectSelectorDropDown();
		formConsumer.accept(projectSelectorDropdownComponent);
		return this;
	}

	public ProjectSelectorDropdownComponent getProjectSelectorDropdownComponent() {
		return projectSelectorDropdownComponent;
	}

	public CodebeamerLocator getSelectProjectButton() {
		return this.locator("button#optionSelector_ms");
	}

	public ReportFinderDialogComponent assertProjectSelectorDropdownComponent(
			Consumer<ProjectSelectorDropdownAssertions> assertion) {
		assertion.accept(getProjectSelectorDropdownComponent().assertThat());
		return this;
	}

	@Override
	public ReportFinderDialogAssertions assertThat() {
		return new ReportFinderDialogAssertions(this);
	}
}
