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

package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TextFieldComponent extends AbstractCodebeamerComponent<TextFieldComponent, TextFieldAssertions>
		implements InlineEditable<TextFieldComponent> {

	public TextFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public TextFieldAssertions assertThat() {
		return new TextFieldAssertions(this);
	}

}
