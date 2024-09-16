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

package com.intland.codebeamer.integration.classic.page.projectadmin.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractProjectAdminPageTab<C extends AbstractProjectAdminPageTab<C, A>, A extends AbstractProjectAdminPageAssertions<C, A>> extends AbstractCodebeamerComponent<C, A> {

	protected AbstractProjectAdminPageTab(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	public C save() {
		getSaveButton().click();
		return (C) this;
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("div > input.cancelButton");
	}

	protected abstract String getTabId();

	private CodebeamerLocator getSaveButton() {
		return this.getCodebeamerPage().locator("input#saveButton");
	}
}