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

package com.intland.codebeamer.integration.classic.page.projectadmin.component.generaltab;

import com.intland.codebeamer.integration.classic.page.projectadmin.component.AbstractProjectAdminPageAssertions;

public class ProjectAdminGeneralTabAssertions extends AbstractProjectAdminPageAssertions<ProjectAdminGeneralTabComponent, ProjectAdminGeneralTabAssertions> {

	protected ProjectAdminGeneralTabAssertions(ProjectAdminGeneralTabComponent component) {
		super(component);
	}

	public ProjectAdminGeneralTabAssertions isFormFieldsReady() {
		return assertAll("Project admin form is ready", () -> {
			assertThat(getComponent().getProjectNameField()).isEditable();
			assertThat(getComponent().getKeyNameField()).isEditable();
			assertThat(getComponent().getRemoveProjectButton()).isVisible();
			assertThat(getComponent().getFileInputLocator()).isEditable();
		});
	}

	public ProjectAdminGeneralTabAssertions isCheckboxFieldsReady() {
		return assertAll("Project admin form is ready", () -> {
			assertThat(getComponent().getAvailablaAsTemplateCheckbox()).isVisible();
			assertThat(getComponent().getMenuItemWikiCheckbox()).isChecked();
			assertThat(getComponent().getMenuItemDocumentsCheckbox()).isChecked();
			assertThat(getComponent().getMenuItemTrackersCheckbox()).isChecked();
			assertThat(getComponent().getMenuItemBaselinesCheckbox()).isChecked();
			assertThat(getComponent().getMenuItemRepositoriesCheckbox()).isVisible();
			assertThat(getComponent().getMenuItemTrashCheckbox()).isVisible();
			assertThat(getComponent().getMenuItemWikiNavigationBarCheckbox()).isVisible();
			assertThat(getComponent().getMembershipConstraints()).isVisible();
		});
	}
}