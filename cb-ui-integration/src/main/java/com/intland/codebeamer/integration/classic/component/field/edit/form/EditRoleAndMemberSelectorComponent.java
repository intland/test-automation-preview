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

package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;

public class EditRoleAndMemberSelectorComponent extends
		AbstractEditRoleAndMemberSelectorComponent<EditRoleAndMemberSelectorComponent, EditRoleAndMemberSelectorAssertions> {

	public EditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String selector) {
		super(codebeamerPage, selector);
	}

	public EditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String selector, String frameLocator) {
		super(codebeamerPage, selector, frameLocator);
	}

	public EditRoleAndMemberSelectorComponent removeAll() {
		getValueField().click();
		getMemberReferences().forEach(c -> getRemoveElement(c).click());
		return this;
	}

	public EditRoleAndMemberSelectorComponent removeMember(MemberReference member) {
		getValueField().click();
		getRemoveElement(member).click();
		return this;
	}

	public EditRoleAndMemberSelectorComponent selectMember(MemberReference member) {
		getValueField().click().type(member.name());

		doSelectMember(member);

		return this;
	}

	@Override
	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}

	@Override
	public EditRoleAndMemberSelectorAssertions assertThat() {
		return new EditRoleAndMemberSelectorAssertions(this);
	}

}
