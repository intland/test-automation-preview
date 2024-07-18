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

import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditLanguageFieldSelectorAssertions extends AbstractCodebeamerComponentAssert<EditLanguageFieldSelectorComponent, EditLanguageFieldSelectorAssertions> {

	protected EditLanguageFieldSelectorAssertions(EditLanguageFieldSelectorComponent component) {
		super(component);
	}

	public EditLanguageFieldSelectorAssertions is(Language language) {
		Language[] languages = new Language[]  { language };
		return assertThat(Matchers.contains(languages), languages);
	}
	public EditLanguageFieldSelectorAssertions contains(Language... languages) {
		return assertThat(Matchers.containsInAnyOrder(languages), languages);
	}

	public EditLanguageFieldSelectorAssertions sameAs(Language... countries) {
		return assertThat(Matchers.contains(countries), countries);
	}
	
	private EditLanguageFieldSelectorAssertions assertThat(Matcher<Iterable<? extends Language>> matcher, Language... languages) {
		String countriesAsString = Arrays.stream(languages).map(Language::toString).collect(Collectors.joining());
		return assertAll("Languages should be found: %s".formatted(countriesAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getLanguage(), matcher);
				});
	}

}
