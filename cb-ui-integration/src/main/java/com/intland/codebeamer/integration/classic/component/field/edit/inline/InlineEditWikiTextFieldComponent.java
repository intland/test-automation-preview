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

package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditWikiTextFieldComponent;

public class InlineEditWikiTextFieldComponent
		extends AbstractEditWikiTextFieldComponent<InlineEditWikiTextFieldComponent, InlineEditWikiTextFieldAssertions> {

	public InlineEditWikiTextFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
		this.froala = new FroalaComponent(codebeamerPage, frameLocator, fieldLocator);
	}

	@Override
	public InlineEditWikiTextFieldAssertions assertThat() {
		return new InlineEditWikiTextFieldAssertions(this);
	}

}
