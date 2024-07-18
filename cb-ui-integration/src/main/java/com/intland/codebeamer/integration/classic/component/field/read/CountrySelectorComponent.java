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

package com.intland.codebeamer.integration.classic.component.field.read;

import java.util.Arrays;
import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CountrySelectorComponent extends AbstractCodebeamerComponent<CountrySelectorComponent, CountrySelectorAssertions> {

	public CountrySelectorComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td:text-is('%s:')".formatted(fieldName));
	}
	
	public List<Country> getCountries() {
		return Arrays.stream(getValueContainerElement().text().split(","))
				.map(String::trim).map(Country::findByName).toList();
	}
	
	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" + td.tableItem.fieldColumn");
	}

	@Override
	public CountrySelectorAssertions assertThat() {
		return new CountrySelectorAssertions(this);
	}

}
