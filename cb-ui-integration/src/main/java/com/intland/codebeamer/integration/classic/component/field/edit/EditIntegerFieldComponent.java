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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditIntegerFieldComponent extends AbstractCodebeamerComponent<EditIntegerFieldComponent, EditIntegerFieldAssertions> {

	public EditIntegerFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
	}

	public EditIntegerFieldComponent fill(int value) {
		getValueField().click().clear().fill(String.valueOf(value));
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue input[type=number]");
	}

	@Override
	public EditIntegerFieldAssertions assertThat() {
		return new EditIntegerFieldAssertions(this);
	}

}
