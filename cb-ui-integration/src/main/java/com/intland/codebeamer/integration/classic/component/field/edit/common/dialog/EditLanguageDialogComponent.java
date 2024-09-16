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
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditLanguageDialogComponent
		extends AbstractCodebeamerComponent<EditLanguageDialogComponent, EditLanguageDialogAssertions> {

	public EditLanguageDialogComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.token-input-dropdown-facebook");
	}

	public EditLanguageDialogComponent select(Language language) {
		this.locator("li:text-matches('%s .*')".formatted(language.getName())).click();
		return this;
	}

	@Override
	public EditLanguageDialogAssertions assertThat() {
		return new EditLanguageDialogAssertions(this);
	}

}
