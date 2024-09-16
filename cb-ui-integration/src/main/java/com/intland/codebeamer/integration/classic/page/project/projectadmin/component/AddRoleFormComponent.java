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

package com.intland.codebeamer.integration.classic.page.project.projectadmin.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class AddRoleFormComponent extends
		AbstractCodebeamerComponent<AddRoleFormComponent, AddRoleFormAssertion> {

	public AddRoleFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "form#command");
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input.button");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input.cancelButton");
	}

	public CodebeamerLocator getRoleInputField() {
		return this.locator("input#roleName");
	}

	public CodebeamerLocator getBasedOnDropdownButton() {
		return this.locator("select[name='templateId']");
	}

	public CodebeamerLocator getDescriptionInputField() {
		return this.locator("input#roleDesc");
	}

	public CodebeamerLocator getPermissionLabel(UserGroupPermission userGroupPermission) {
		return this.locator("label[data-testid='%s']".formatted(userGroupPermission.getPermission()));
	}

	@Override
	public AddRoleFormAssertion assertThat() {
		return new AddRoleFormAssertion(this);
	}
}

