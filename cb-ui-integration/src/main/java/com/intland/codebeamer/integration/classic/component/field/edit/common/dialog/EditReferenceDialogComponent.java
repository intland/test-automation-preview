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

package com.intland.codebeamer.integration.classic.component.field.edit.common.dialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditReferenceDialogComponent extends
		AbstractCodebeamerComponent<EditReferenceDialogComponent, EditReferenceDialogAssertions> {

	public EditReferenceDialogComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.token-input-dropdown-facebook");
	}

	public EditReferenceDialogComponent select(String streamName) {
		this.locator("li table span.autocomplete-match-highlight:text-is('%s')".formatted(streamName)).click();
		return this;
	}

	@Override
	public EditReferenceDialogAssertions assertThat() {
		return new EditReferenceDialogAssertions(this);
	}
}
