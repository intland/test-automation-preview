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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditTextAreaFieldComponent;

public class EditTextAreaFieldComponent extends
		AbstractEditTextAreaFieldComponent<EditTextAreaFieldComponent, EditTextAreaFieldAssertions> {

	public EditTextAreaFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditTextAreaFieldComponent fill(String value) {
		getValueField().click().clear().fill(value);
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" textarea");
	}

	@Override
	public EditTextAreaFieldAssertions assertThat() {
		return new EditTextAreaFieldAssertions(this);
	}

}
