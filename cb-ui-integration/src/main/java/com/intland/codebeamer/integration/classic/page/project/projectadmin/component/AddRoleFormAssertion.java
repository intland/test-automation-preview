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

import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class AddRoleFormAssertion extends
		AbstractCodebeamerComponentAssert<AddRoleFormComponent, AddRoleFormAssertion> {

	public AddRoleFormAssertion(AddRoleFormComponent component) {
		super(component);
	}

	public AddRoleFormAssertion isReady() {
		return assertAll("Add role form is ready", () -> {
			assertThat(getComponent().getSaveButton()).isVisible();
			assertThat(getComponent().getCancelButton()).isVisible();
			assertThat(getComponent().getRoleInputField()).isEditable();
			assertThat(getComponent().getBasedOnDropdownButton()).isEditable();
			assertThat(getComponent().getDescriptionInputField()).isEditable();
			assertThat(getComponent().getPermissionLabel(UserGroupPermission.REVIEW_ADMINISTRATION)).not().isVisible();
		});
	}
}
