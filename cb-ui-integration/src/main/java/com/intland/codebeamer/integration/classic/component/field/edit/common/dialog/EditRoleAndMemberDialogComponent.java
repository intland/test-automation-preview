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

package com.intland.codebeamer.integration.classic.component.field.edit.common.dialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditRoleAndMemberDialogComponent
		extends AbstractCodebeamerComponent<EditRoleAndMemberDialogComponent, EditRoleAndMemberDialogAssertions> {

	public EditRoleAndMemberDialogComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.token-input-dropdown-facebook");
	}

	public EditRoleAndMemberDialogComponent selectUser(String username) {
		this.locator("li table[data-username='%s']".formatted(username)).click();
		return this;
	}

	public EditRoleAndMemberDialogComponent selectRole(String role) {
		// UI-AUTOMATION: add extra selector for role type
		this.locator("li table:not([data-username]) span:text-is('%s')".formatted(role)).click();
		return this;
	}

	public EditRoleAndMemberDialogComponent selectTrackerField(String trackerFieldName) {
		// UI-AUTOMATION: add extra selector for tracker field type
		this.locator("li table:not([data-username]) span:text-is('%s')".formatted(trackerFieldName)).click();
		return this;
	}

	@Override
	public EditRoleAndMemberDialogAssertions assertThat() {
		return new EditRoleAndMemberDialogAssertions(this);
	}

}
