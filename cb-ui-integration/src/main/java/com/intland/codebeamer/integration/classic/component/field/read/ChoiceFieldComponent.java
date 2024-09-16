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

public class ChoiceFieldComponent extends AbstractCodebeamerComponent<ChoiceFieldComponent, ChoiceFieldAssertions>
		implements InlineEditable<ChoiceFieldComponent> {

	public ChoiceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public ChoiceFieldAssertions assertThat() {
		return new ChoiceFieldAssertions(this);
	}
	
}
