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
import com.intland.codebeamer.integration.classic.InterwikiLinkNavigation;
import com.intland.codebeamer.integration.classic.component.InterwikiLinkComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DescriptionWikiTextFieldComponent
		extends AbstractCodebeamerComponent<DescriptionWikiTextFieldComponent, DescriptionWikiTextFieldAssertions>
		implements InlineEditable<DescriptionWikiTextFieldComponent> {

	public DescriptionWikiTextFieldComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "fieldset.descriptionBox");
	}

	public InterwikiLinkComponent getInterwikiLink(String linkText) {
		return new InterwikiLinkComponent(getCodebeamerPage(), getSelector(), linkText);
	}

	public InterwikiLinkNavigation clickOnLink(String linkText) {
		getLink(linkText).click();
		return new InterwikiLinkNavigation(getCodebeamerPage());
	}

	@Override
	public DescriptionWikiTextFieldComponent edit() {
		getEditElement().click();
		return this;
	}

	public CodebeamerLocator getEditElement() {
		return this.locator("span.descriptionEditIcon");
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("div.fieldColumn.descriptionField ");
	}

	@Override
	public DescriptionWikiTextFieldAssertions assertThat() {
		return new DescriptionWikiTextFieldAssertions(this);
	}

	private CodebeamerLocator getLink(String text) {
		return locator("a").getByText(text, true);
	}
}
