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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditColorFieldComponent;

public class InlineEditColorFieldComponent extends
		AbstractEditColorFieldComponent<InlineEditColorFieldComponent, InlineEditColorFieldAssertions> {
	
	public InlineEditColorFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditColorFieldComponent fill(HtmlColor htmlColor) {
		getValueField().readonlyWrapper(f -> f.clear().fill(htmlColor.getHexCode()));
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input#inlineEditColorFieldEditor");
	}

	@Override
	public InlineEditColorFieldAssertions assertThat() {
		return new InlineEditColorFieldAssertions(this);
	}

}
