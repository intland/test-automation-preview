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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ConfirmationDialogComponent
		extends AbstractCodebeamerComponent<ConfirmationDialogComponent, ConfirmationDialogAssertions> {

	public ConfirmationDialogComponent(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: the active dialog should have a proper selector
		super(codebeamerPage, ".ui-dialog.ui-corner-all.cbModalDialog");
	}

	public ConfirmationDialogComponent yes() {
		getButtonsLocator().concat("button.button").click();
		return this;
	}

	public ConfirmationDialogComponent cancel() {
		getButtonsLocator().concat("button.cancelButton").click();
		return this;
	}

	private CodebeamerLocator getButtonsLocator() {
		return this.locator("div.ui-dialog-buttonset");
	}

	@Override
	public ConfirmationDialogAssertions assertThat() {
		return new ConfirmationDialogAssertions(this);
	}
}
