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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditTextFieldComponent;

public class InlineEditTextFieldComponent extends
		AbstractEditTextFieldComponent<InlineEditTextFieldComponent, InlineEditTextFieldAssertions> {

	public InlineEditTextFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditTextFieldComponent fill(String value) {
		getValueField().clear().fill(value);
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text][class=inlineEditor]");
	}

	@Override
	public InlineEditTextFieldAssertions assertThat() {
		return new InlineEditTextFieldAssertions(this);
	}

}
