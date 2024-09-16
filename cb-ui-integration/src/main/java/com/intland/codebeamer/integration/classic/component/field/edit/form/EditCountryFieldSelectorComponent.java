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

package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditCountryFieldSelectorComponent;

public class EditCountryFieldSelectorComponent extends
		AbstractEditCountryFieldSelectorComponent<EditCountryFieldSelectorComponent, EditCountryFieldSelectorAssertions> {

	public EditCountryFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditCountryFieldSelectorComponent removeAllOptions() {
		getValueField().click();
		getSelectedCountries().forEach(c -> getRemoveElement(c).click());
		return this;
	}

	public EditCountryFieldSelectorComponent removeOption(Country county) {
		getValueField().click();
		getRemoveElement(county).click();
		return this;
	}

	public EditCountryFieldSelectorComponent selectOption(Country county) {
		if (getSelectedCountries().contains(county)) {
			throw new AssertionError("Country is already added to the field");
		}

		getValueField().click().type(county.getName());
		this.countryDialogComponent.select(county);
		return this;
	}

	@Override
	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" ul.token-input-list-facebook");
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" ul li input[type=text]");
	}

	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}

	@Override
	public EditCountryFieldSelectorAssertions assertThat() {
		return new EditCountryFieldSelectorAssertions(this);
	}

}
