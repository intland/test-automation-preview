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
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditCountryDialogComponent extends AbstractCodebeamerComponent<EditCountryDialogComponent, EditCountryDialogAssertions> {

	public EditCountryDialogComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.token-input-dropdown-facebook");
	}

	public EditCountryDialogComponent select(Country country) {
		this.locator("li:text-matches('%s .*')".formatted(country.getName())).click();
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}
	
	@Override
	public EditCountryDialogAssertions assertThat() {
		return new EditCountryDialogAssertions(this);
	}

}
