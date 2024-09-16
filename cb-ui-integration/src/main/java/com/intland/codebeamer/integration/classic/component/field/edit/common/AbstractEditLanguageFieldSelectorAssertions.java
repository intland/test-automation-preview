package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditLanguageFieldSelectorAssertions<C extends AbstractEditLanguageFieldSelectorComponent<C, A>, A extends AbstractEditLanguageFieldSelectorAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditLanguageFieldSelectorAssertions(C component) {
		super(component);
	}

	public A is(Language language) {
		Language[] languages = new Language[]  { language };
		return assertThat(Matchers.contains(languages), languages);
	}
	public A contains(Language... languages) {
		return assertThat(Matchers.containsInAnyOrder(languages), languages);
	}

	public A sameAs(Language... countries) {
		return assertThat(Matchers.contains(countries), countries);
	}
	
	private A assertThat(Matcher<Iterable<? extends Language>> matcher, Language... languages) {
		String countriesAsString = Arrays.stream(languages).map(Language::toString).collect(Collectors.joining());
		return assertAll("Languages should be found: %s".formatted(countriesAsString),
				() -> {
					assertThat(getComponent().getValueContainerElement()).isAttached();
					MatcherAssert.assertThat(getComponent().getSelectedLanguages(), matcher);
				});
	}

}
