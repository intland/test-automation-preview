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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditTextAreaFieldComponent;

public class InlineEditTextAreaFieldComponent extends
		AbstractEditTextAreaFieldComponent<InlineEditTextAreaFieldComponent, InlineEditTextAreaFieldAssertions> {

	public InlineEditTextAreaFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditTextAreaFieldComponent fill(String value) {
		getValueField().clear().fill(value);
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" textarea");
	}

	@Override
	public InlineEditTextAreaFieldAssertions assertThat() {
		return new InlineEditTextAreaFieldAssertions(this);
	}

}
