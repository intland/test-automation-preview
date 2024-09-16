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

public class CountrySelectorComponent extends AbstractCodebeamerComponent<CountrySelectorComponent, CountrySelectorAssertions>
		implements InlineEditable<CountrySelectorComponent>{

	public CountrySelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator) {
		super(codebeamerPage, fieldLocator);
	}
	
	public List<Country> getCountries() {
		return Arrays.stream(getValueElement().text().split(","))
				.map(String::trim).map(Country::findByName).toList();
	}

	@Override
	public CodebeamerLocator getValueElement() {
		return this.locator("");
	}

	@Override
	public CountrySelectorAssertions assertThat() {
		return new CountrySelectorAssertions(this);
	}

}
