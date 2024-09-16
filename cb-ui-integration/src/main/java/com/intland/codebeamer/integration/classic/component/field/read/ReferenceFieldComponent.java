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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.helper.ReferenceFieldValueConverter;
import com.intland.codebeamer.integration.classic.component.field.helper.ReferenceFieldValueConverter.ReferenceFieldValue;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public class ReferenceFieldComponent extends AbstractCodebeamerComponent<ReferenceFieldComponent, ReferenceFieldAssertions>
	implements InlineEditable<ReferenceFieldComponent> {

	public ReferenceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}
	
	public List<ReferenceFieldValue> getReferences() {
		return getValueElement().concat("div[data-reference-ids]").all().stream()
				.flatMap(l -> Arrays.stream(StringUtils.split(l.getAttribute("data-reference-ids"), ",")))
				.map(referenceId -> new ReferenceFieldValueConverter().createFromRawValue(referenceId))
				.toList();
	}

	/**
	 * Because playwright clicks at the locators position before initiating a double click, it needs to be ensured that
	 * we click at the start of the input and not in the middle.
	 */
	@Override
	public ReferenceFieldComponent edit() {
		getValueElement().doubleClick(new Locator.DblclickOptions().setPosition(5, 0));
		return this;
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public ReferenceFieldAssertions assertThat() {
		return new ReferenceFieldAssertions(this);
	}

}
