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

package com.intland.codebeamer.integration.classic.page.dashboard.child.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractDashboardNewChildDialogFormComponent
		extends
		AbstractCodebeamerComponent<AbstractDashboardNewChildDialogFormComponent, AbstractDashboardNewChildDialogFormAssertions> {

	public AbstractDashboardNewChildDialogFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form#addDashboardCommand");
	}

	public AbstractDashboardNewChildDialogFormComponent pageName(String name) {
		getPageNameField().fill(name);
		return this;
	}

	public AbstractDashboardNewChildDialogFormComponent save() {
		getSaveButton().click();
		return this;
	}

	CodebeamerLocator getSaveButton() {
		return this.locator("button:has-text('Add')");
	}

	public CodebeamerLocator getPageNameField() {
		return this.locator("input#name");
	}

	@Override
	public abstract AbstractDashboardNewChildDialogFormAssertions assertThat();

}
