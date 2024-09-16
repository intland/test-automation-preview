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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditChoiceFieldComponent;

public class InlineEditChoiceFieldComponent extends
		AbstractEditChoiceFieldComponent<InlineEditChoiceFieldComponent, InlineEditChoiceFieldAssertions> {

	public InlineEditChoiceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditChoiceFieldComponent selectOption(String value) {
		getValueSelector().selectOption(value);

		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueSelector() {
		return this.locator(" select.pixelResizeEditBox");
	}

	@Override
	public InlineEditChoiceFieldAssertions assertThat() {
		return new InlineEditChoiceFieldAssertions(this);
	}

}
