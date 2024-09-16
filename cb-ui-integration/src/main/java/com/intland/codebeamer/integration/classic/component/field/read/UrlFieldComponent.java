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

import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter.UrlFieldValue;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public class UrlFieldComponent extends AbstractCodebeamerComponent<UrlFieldComponent, UrlFieldAssertions>
		implements InlineEditable<UrlFieldComponent> {

	public UrlFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}

	/**
	 * Because playwright clicks at the locators position before initiating a double click, it needs to be ensured that
	 * we click at the start of the input and not in the middle.
	 */
	@Override
	public UrlFieldComponent edit() {
		getValueElement().doubleClick(new Locator.DblclickOptions().setPosition(5, 0).setDelay(200));
		return this;
	}

	public Optional<UrlFieldValue> getUrl() {
		return getValueElement().concat("a").all().stream()
				.filter(l -> StringUtils.isNotEmpty(l.getAttribute("href")))
				.map(l -> new UrlFieldValueConverter().create(l.text(), l.getAttribute("href")))
				.findFirst();
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public UrlFieldAssertions assertThat() {
		return new UrlFieldAssertions(this);
	}

}
