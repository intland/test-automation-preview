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

import org.apache.commons.lang3.BooleanUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditBooleanFieldComponent extends AbstractCodebeamerComponent<EditBooleanFieldComponent, EditBooleanFieldAssertions> {

	public EditBooleanFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
	}

	public EditBooleanFieldComponent selectOption(Boolean value) {
		getValueSelector().selectOption(BooleanUtils.toStringTrueFalse(value).toLowerCase());
		return this;
	}
	
	public CodebeamerLocator getValueSelector() {
		return this.locator(" + td.fieldValue.fieldType_bool select");
	}

	@Override
	public EditBooleanFieldAssertions assertThat() {
		return new EditBooleanFieldAssertions(this);
	}

}
