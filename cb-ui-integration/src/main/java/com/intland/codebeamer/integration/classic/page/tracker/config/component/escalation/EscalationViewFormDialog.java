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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EscalationViewFormDialog
		extends AbstractCodebeamerComponent<EscalationViewFormDialog, EscalationViewFormAssertions> {

	public EscalationViewFormDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.trackerViewConfigurationDialog");
	}

	public EscalationViewFormDialog setName(String name) {
		getNameInput().fill(name);
		return this;
	}

	public EscalationViewFormDialog setDescription(String description) {
		getDescriptionInput().fill(description);
		return this;
	}

	public EscalationViewFormDialog save() {
		getSaveButton().click();
		return this;
	}

	public EscalationViewFormDialog cancel() {
		getCancelButton().click();
		return this;
	}

	public CodebeamerLocator getNameInput() {
		return this.locator("input[name=name]");
	}

	public CodebeamerLocator getDescriptionInput() {
		return this.locator("textarea[name=description]");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("button.cancel");
	}

	public CodebeamerLocator getSaveButton() {
		// UI-AUTOMATION: add proper data-testid to it
		return this.locator("button:has-text('OK')");
	}

	@Override
	public EscalationViewFormAssertions assertThat() {
		return new EscalationViewFormAssertions(this);
	}

}
