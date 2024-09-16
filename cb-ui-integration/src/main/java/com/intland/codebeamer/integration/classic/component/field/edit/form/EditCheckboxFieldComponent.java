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
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;

public class EditCheckboxFieldComponent extends
		AbstractEditFieldComponent<EditCheckboxFieldComponent, EditCheckboxFieldAssertions> {

	private String valueSelector = "input[type='checkbox']";

	public EditCheckboxFieldComponent(CodebeamerPage codebeamerPage, String fieldSelector) {
		super(codebeamerPage, fieldSelector);
	}

	public EditCheckboxFieldComponent(CodebeamerPage codebeamerPage, String fieldSelector, String valueSelector) {
		super(codebeamerPage, fieldSelector);
		this.valueSelector = valueSelector;
	}

	public EditCheckboxFieldComponent select(boolean value) {
		getValueSelector().check(value);
		return this;
	}

	protected CodebeamerLocator getValueSelector() {
		return this.locator(valueSelector);
	}

	@Override
	public EditCheckboxFieldAssertions assertThat() {
		return new EditCheckboxFieldAssertions(this);
	}

}
