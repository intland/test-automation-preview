package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public class AbstractEditCountryFieldSelectorAssertions<C extends AbstractEditCountryFieldSelectorComponent<C, A>, A extends AbstractEditCountryFieldSelectorAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditCountryFieldSelectorAssertions(C component) {
		super(component);
	}

	public A is(Country country) {
		Country[] countries = new Country[]  { country };
		return assertThat(Matchers.contains(countries), countries);
	}
	public A contains(Country... countries) {
		return assertThat(Matchers.containsInAnyOrder(countries), countries);
	}

	public A sameAs(Country... countries) {
		return assertThat(Matchers.contains(countries), countries);
	}
	
	private A assertThat(Matcher<Iterable<? extends Country>> matcher, Country... country) {
		String countriesAsString = Arrays.stream(country).map(Country::toString).collect(Collectors.joining(","));
		return assertAll("Countries should be found: %s".formatted(countriesAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getSelectedCountries(), matcher);
				});
	}
	
}
