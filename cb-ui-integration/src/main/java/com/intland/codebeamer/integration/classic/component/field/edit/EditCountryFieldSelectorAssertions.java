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

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditCountryFieldSelectorAssertions extends AbstractCodebeamerComponentAssert<EditCountryFieldSelectorComponent, EditCountryFieldSelectorAssertions> {

	protected EditCountryFieldSelectorAssertions(EditCountryFieldSelectorComponent component) {
		super(component);
	}

	public EditCountryFieldSelectorAssertions is(Country country) {
		Country[] countries = new Country[]  { country };
		return assertThat(Matchers.contains(countries), countries);
	}
	public EditCountryFieldSelectorAssertions contains(Country... countries) {
		return assertThat(Matchers.containsInAnyOrder(countries), countries);
	}

	public EditCountryFieldSelectorAssertions sameAs(Country... countries) {
		return assertThat(Matchers.contains(countries), countries);
	}
	
	private EditCountryFieldSelectorAssertions assertThat(Matcher<Iterable<? extends Country>> matcher, Country... country) {
		String countriesAsString = Arrays.stream(country).map(Country::toString).collect(Collectors.joining());
		return assertAll("Countries should be found: %s".formatted(countriesAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getCountries(), matcher);
				});
	}
	
}
