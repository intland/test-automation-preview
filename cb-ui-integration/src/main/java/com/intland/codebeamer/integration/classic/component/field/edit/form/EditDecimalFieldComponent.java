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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDecimalFieldComponent;

public class EditDecimalFieldComponent extends
		AbstractEditDecimalFieldComponent<EditDecimalFieldComponent, EditDecimalFieldAssertions> {

	public EditDecimalFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditDecimalFieldComponent fill(double value) {
		getValueField().click().clear().fill(String.valueOf(value));
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public EditDecimalFieldAssertions assertThat() {
		return new EditDecimalFieldAssertions(this);
	}

}
