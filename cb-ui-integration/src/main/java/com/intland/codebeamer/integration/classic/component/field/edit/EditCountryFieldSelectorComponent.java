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

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditCountryFieldSelectorComponent extends AbstractCodebeamerComponent<EditCountryFieldSelectorComponent, EditCountryFieldSelectorAssertions> {

	private EditCountryDialogComponent countryDialogComponent;

	public EditCountryFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.countryDialogComponent = new EditCountryDialogComponent(codebeamerPage);
	}

	public EditCountryFieldSelectorComponent selectOption(Country county) {
		getValueField().doubleClick().type(county.getName());
		this.countryDialogComponent.select(county);
		return this;
	}
	
	public List<Country> getCountries() {
		return this.locator(" + td.fieldValue ul.token-input-list-facebook li p.name").all().stream()
				.map(CodebeamerLocator::text)
				.map(Country::findByName)
				.toList();
	}

	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" + td.fieldValue ul.token-input-list-facebook");
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}

	@Override
	public EditCountryFieldSelectorAssertions assertThat() {
		return new EditCountryFieldSelectorAssertions(this);
	}

}
