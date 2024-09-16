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

package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.Optional;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter.UrlFieldValue;

public abstract class AbstractEditUrlFieldComponent<C extends AbstractEditUrlFieldComponent<C, A>, A extends AbstractEditUrlFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	public AbstractEditUrlFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public AbstractEditUrlFieldComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	public Optional<UrlFieldValue> getUrl() {
		getValueContainerElement().waitForAttached();
		return Optional.ofNullable(getValueContainerElement().concat("li:has(p.name)"))
				.map(l -> l.getAttribute("data-value"))
				.map(url -> new UrlFieldValueConverter().createFromRawValue(url));
	}

	public CodebeamerLocator getElement(String referenceName) {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat(
				"li:has(p:has-text('%s')) span.token-input-delete-token-facebook".formatted(referenceName));
	}

	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" ul.token-input-list-facebook");
	}

	public CodebeamerLocator getSelectedValues() {
		return getValueContainerElement().concat("li:has(p.name)");
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" ul li input[type=text]");
	}

	public CodebeamerLocator getPopupButton() {
		return this.locator(" a.popupButton");
	}

}