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
import com.microsoft.playwright.Locator;

public abstract class AbstractEditFieldComponent<C extends AbstractEditFieldComponent<C, A>, A extends AbstractEditFieldComponentAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	protected AbstractEditFieldComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	protected AbstractEditFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public CodebeamerLocator getTitleElement() {
		return this.locator("");
	}

	protected String getReferenceInlineEditSelector() {
		return "table";
	}

	protected void waitForReferenceInlineEditStart() {
		this.locator(getReferenceInlineEditSelector()).waitForAttached();
		waitForNetworkIdle();
		sleep(1);
	}

	protected void waitForReferenceInlineEditEnd() {
		this.locator(getReferenceInlineEditSelector()).waitForDetached();
		waitForNetworkIdle();
		sleep(1);
	}

	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2));
	}
}
