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

import org.apache.commons.lang3.BooleanUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditBooleanFieldComponent;

public class EditBooleanFieldComponent extends
		AbstractEditBooleanFieldComponent<EditBooleanFieldComponent, EditBooleanFieldAssertions> {

	public EditBooleanFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditBooleanFieldComponent selectOption(Boolean value) {
		getValueSelector().selectOption(BooleanUtils.toStringTrueFalse(value).toLowerCase());
		return this;
	}

	@Override
	public CodebeamerLocator getValueSelector() {
		return this.locator(" select");
	}

	@Override
	public EditBooleanFieldAssertions assertThat() {
		return new EditBooleanFieldAssertions(this);
	}

}
