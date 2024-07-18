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
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class CountrySelectorAssertions extends AbstractCodebeamerComponentAssert<CountrySelectorComponent, CountrySelectorAssertions> {

	protected CountrySelectorAssertions(CountrySelectorComponent component) {
		super(component);
	}
	
	public CountrySelectorAssertions contains(Country... countries) {
		return assertThat(Matchers.containsInAnyOrder(countries), countries);
	}

	public CountrySelectorAssertions sameAs(Country... countries) {
		return assertThat(Matchers.contains(countries), countries);
	}
	
	private CountrySelectorAssertions assertThat(Matcher<Iterable<? extends Country>> matcher, Country... countries) {
		String countriesAsString = Arrays.stream(countries).map(Country::toString).collect(Collectors.joining(", "));
		return assertAll("Countries should be found: %s".formatted(countriesAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getCountries(), matcher);
				});
	}
	
}
