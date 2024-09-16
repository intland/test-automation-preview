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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditIntegerFieldComponent;

public class InlineEditIntegerFieldComponent extends
		AbstractEditIntegerFieldComponent<InlineEditIntegerFieldComponent, InlineEditIntegerFieldAssertions> {

	public InlineEditIntegerFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditIntegerFieldComponent fill(int value) {
		getValueField().clear().fill(String.valueOf(value));
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=number]");
	}

	@Override
	public InlineEditIntegerFieldAssertions assertThat() {
		return new InlineEditIntegerFieldAssertions(this);
	}

}
