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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditChoiceFieldComponent;

public class EditChoiceFieldComponent extends
		AbstractEditChoiceFieldComponent<EditChoiceFieldComponent, EditChoiceFieldAssertions> {

	public EditChoiceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditChoiceFieldComponent selectOption(String value) {
		getValueSelector().selectOption(value);
		return this;
	}

	@Override
	public CodebeamerLocator getValueSelector() {
		return this.locator("select[name$='.choiceFieldValues']");
	}

	@Override
	public EditChoiceFieldAssertions assertThat() {
		return new EditChoiceFieldAssertions(this);
	}

}
