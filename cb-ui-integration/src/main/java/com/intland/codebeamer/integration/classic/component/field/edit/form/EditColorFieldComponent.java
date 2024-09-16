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
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditColorFieldComponent;

public class EditColorFieldComponent extends AbstractEditColorFieldComponent<EditColorFieldComponent, EditColorFieldAssertions> {

	public EditColorFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		this(codebeamerPage, fieldLocator, null);
	}
	
	public EditColorFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditColorFieldComponent fill(HtmlColor htmlColor) {
		getValueField().readonlyWrapper(f -> f.clear().fill(htmlColor.getHexCode()));
		getLocator().click();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public EditColorFieldAssertions assertThat() {
		return new EditColorFieldAssertions(this);
	}

}
