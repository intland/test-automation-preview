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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.classic.page.projectadmin.ProjectAdminPageNavigation;
import com.intland.codebeamer.integration.classic.page.projectadmin.component.AbstractProjectAdminPageTab;

public class ProjectAdminGeneralTabComponent extends AbstractProjectAdminPageTab<ProjectAdminGeneralTabComponent, ProjectAdminGeneralTabAssertions> {

	private ProjectAdminPageNavigation projectAdminPageNavigation;

	public ProjectAdminGeneralTabComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, "#projectSettingsForm");
		this.projectAdminPageNavigation = new ProjectAdminPageNavigation(codebeamerPage, project);
	}

	public ProjectAdminGeneralTabComponent uploadFile(Path path) {
		getCodebeamerPage().uploadFiles(() -> getFileInputLocator().click(), path);
		return this;
	}

	public ProjectAdminGeneralTabComponent assignNewMemberToRole(Role role) {
		getNewMemberToRoleElement().selectOption(String.valueOf(role.getRoleId().id()));
		return this;
	}

	public ProjectAdminPageNavigation cancel() {
		getCancelButton().click();
		return projectAdminPageNavigation;
	}

	public CodebeamerLocator getProjectNameField() {
		return this.locator("input#name");
	}

	public CodebeamerLocator getNewMemberToRoleElement() {
		return this.locator("#defaultMemberRoleId > td:nth-child(2) > select");
	}

	public CodebeamerLocator getKeyNameField() {
		return this.locator("input#keyName");
	}

	public CodebeamerLocator getRemoveProjectButton() {
		return this.locator("input[type='submit'][name='REMOVE']");
	}

	public CodebeamerLocator getAvailablaAsTemplateCheckbox() {
		return this.locator("input#templateCheckbox");
	}

	public CodebeamerLocator getMenuItemWikiCheckbox() {
		return this.locator("input#menuItems_wikispace");
	}

	public CodebeamerLocator getMenuItemDocumentsCheckbox() {
		return this.locator("input#menuItems_docs");
	}

	public CodebeamerLocator getMenuItemTrackersCheckbox() {
		return this.locator("input#menuItems_tracker");
	}

	public CodebeamerLocator getMenuItemBaselinesCheckbox() {
		return this.locator("input#menuItems_baselines");
	}

	public CodebeamerLocator getMenuItemRepositoriesCheckbox() {
		return this.locator("input#menuItems_sources");
	}

	public CodebeamerLocator getMenuItemTrashCheckbox() {
		return this.locator("input#menuItems_trash");
	}

	public CodebeamerLocator getMenuItemWikiNavigationBarCheckbox() {
		return this.locator("input#wikiNavigationBarVisible");
	}

	public CodebeamerLocator getMembershipConstraints() {
		return this.locator("input#membershipPolicy_with_approval");
	}

	public CodebeamerLocator getFileInputLocator() {
		return this.locator("input[type='file'][name='file']");
	}

	@Override
	public ProjectAdminGeneralTabAssertions assertThat() {
		return new ProjectAdminGeneralTabAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#project-admin-general-tab";
	}
}