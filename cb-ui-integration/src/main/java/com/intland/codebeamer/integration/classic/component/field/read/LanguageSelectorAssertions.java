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

import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class LanguageSelectorAssertions extends AbstractCodebeamerComponentAssert<LanguageSelectorComponent, LanguageSelectorAssertions> {

	protected LanguageSelectorAssertions(LanguageSelectorComponent component) {
		super(component);
	}
	
	public LanguageSelectorAssertions contains(Language... languages) {
		return assertThat(Matchers.containsInAnyOrder(languages), languages);
	}

	public LanguageSelectorAssertions sameAs(Language... languages) {
		return assertThat(Matchers.contains(languages), languages);
	}
	
	private LanguageSelectorAssertions assertThat(Matcher<Iterable<? extends Language>> matcher, Language... languages) {
		String languageAsString = Arrays.stream(languages).map(Language::toString).collect(Collectors.joining());
		return assertAll("Languages should be found: %s".formatted(languageAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getLanguages(), matcher);
				});
	}
	
}
