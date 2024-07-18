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

package com.intland.codebeamer.integration.classic.component.field.edit;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditRoleAndMemberSelectorComponent extends AbstractCodebeamerComponent<EditRoleAndMemberSelectorComponent, EditRoleAndMemberSelectorAssertions> {

	private EditRoleAndMemberDialogComponent roleAndMemberDialogComponent;

	public EditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.roleAndMemberDialogComponent = new EditRoleAndMemberDialogComponent(codebeamerPage);
	}

	public EditRoleAndMemberSelectorComponent selectUser(String username) {
		getValueField().doubleClick().type(username);
		this.roleAndMemberDialogComponent.selectUser(username);
		return this;
	}
	
	public EditRoleAndMemberSelectorComponent selectRole(String role) {
		getValueField().doubleClick().type(role);
		this.roleAndMemberDialogComponent.selectRole(role);
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}

	@Override
	public EditRoleAndMemberSelectorAssertions assertThat() {
		return new EditRoleAndMemberSelectorAssertions(this);
	}

}
