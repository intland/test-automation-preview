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
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDateFieldComponent;

public class EditDateFieldComponent extends AbstractEditDateFieldComponent<EditDateFieldComponent, EditDateFieldAssertions> {

	public EditDateFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditDateFieldComponent fill(int year, int month, int day, int hour, int minute) {
		getValueField().click().clear().fill(String.valueOf(getFormattedDate(year, month, day, hour, minute)));
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public EditDateFieldAssertions assertThat() {
		return new EditDateFieldAssertions(this);
	}

}
