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

package com.intland.codebeamer.integration.classic.page.tracker.config.fields;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FieldOverlayMenuComponent extends AbstractCodebeamerComponent<FieldOverlayMenuComponent, FieldOverlayMenuAssertions> {

	public FieldOverlayMenuComponent(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: on tracker config page, the context menu of a field row (contains edit, remove etc.) needs
		// a proper identifier, also for the currently selected one
		super(codebeamerPage, "ul.context-menu-list:not([style='display:none'])");
	}

	public CodebeamerLocator getNewColumnButton() {
		// UI-AUTOMATION: on tracker config page, the context menu for adding a new column to
		// a table field needs a proper identifier
		return this.locator("li.context-menu-item span:has-text('New column')");
	}

	@Override
	public FieldOverlayMenuAssertions assertThat() {
		return new FieldOverlayMenuAssertions(this);
	}
}
