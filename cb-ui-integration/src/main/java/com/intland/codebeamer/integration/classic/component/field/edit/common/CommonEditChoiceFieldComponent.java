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

package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

public class CommonEditChoiceFieldComponent
		extends AbstractEditChoiceFieldComponent<CommonEditChoiceFieldComponent, CommonEditChoiceFieldAssertions> {

	public CommonEditChoiceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, null, fieldLocator);
	}

	public CommonEditChoiceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public CommonEditChoiceFieldComponent selectOption(String value) {
		getValueSelector().selectOption(value);
		return this;
	}

	@Override
	public CodebeamerLocator getValueSelector() {
		return this.locator("");
	}

	@Override
	public CommonEditChoiceFieldAssertions assertThat() {
		return new CommonEditChoiceFieldAssertions(this);
	}

}
