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

package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import java.util.Arrays;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;
import com.microsoft.playwright.Locator;

public class InlineEditRoleAndMemberSelectorComponent extends
		AbstractEditRoleAndMemberSelectorComponent<InlineEditRoleAndMemberSelectorComponent, InlineEditRoleAndMemberSelectorAssertions> {

	public InlineEditRoleAndMemberSelectorComponent(CodebeamerPage codebeamerPage, String fieldSelector, String frameLocator) {
		super(codebeamerPage, fieldSelector, frameLocator);
	}

	public InlineEditRoleAndMemberSelectorComponent selectMember(MemberReference... member) {
		waitForReferenceInlineEditStart();

		Arrays.stream(member).forEach(this::select);

		stopInlineEdit();
		return this;
	}

	public InlineEditRoleAndMemberSelectorComponent removeMember(MemberReference... member) {
		waitForReferenceInlineEditStart();

		Arrays.stream(member).forEach(this::remove);

		stopInlineEdit();
		return this;
	}

	public InlineEditRoleAndMemberSelectorComponent removeAll() {
		waitForReferenceInlineEditStart();

		getMemberReferences().forEach(c -> getRemoveElement(c).click());

		stopInlineEdit();
		return this;
	}

	@Override
	public InlineEditRoleAndMemberSelectorAssertions assertThat() {
		return new InlineEditRoleAndMemberSelectorAssertions(this);
	}

	@Override
	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2).setDelay(500));
		waitForReferenceInlineEditEnd();
	}

	private void select(MemberReference member) {
		getValueField().type(member.name());

		doSelectMember(member);

		getElement(member).waitForAttached();
	}

	private void remove(MemberReference member) {
		CodebeamerLocator element = getElement(member);
		element.waitForAttached();

		getRemoveElement(member).click();

		element.waitForDetached();
	}

}
