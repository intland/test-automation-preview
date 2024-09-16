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

package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.function.Function;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter.UrlFieldValue;

public abstract class AbstractEditUrlFieldAssertions<C extends AbstractEditUrlFieldComponent<C, A>, A extends AbstractEditUrlFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditUrlFieldAssertions(C component) {
		super(component);
	}

	public A isAlias(String alias) {
		return assertAll("Alias of the url field should be %s".formatted(alias), () -> assertUrl(UrlFieldValue::alias, alias));
	}

	public A isWikiLink(String wikiLink) {
		return assertAll("Url of the url field should be %s".formatted(wikiLink),
				() -> assertWikiLink(UrlFieldValue::url, wikiLink));
	}

	public A isUrl(String url) {
		return assertAll("Url of the url field should be %s".formatted(url), () -> assertUrl(UrlFieldValue::url, url));
	}

	public A isWikiLinkBroken(String url) {
		return assertAll("Wiki Link (%s) must be broken".formatted(url),
				() -> assertThat(getComponent().getSelectedValues()).hasCSS("color", "rgb(255, 0, 0)"));
	}

	private void assertWikiLink(Function<UrlFieldValue, String> mapper, String value) {
		assertUrl(mapper, "ISSUE:%s".formatted(value));
	}

	private void assertUrl(Function<UrlFieldValue, String> mapper, String value) {
		assertThat(getComponent().getSelectedValues()).not().isEmpty();
		MatcherAssert.assertThat(extractValue(mapper), Matchers.equalTo(value));
	}

	private String extractValue(Function<UrlFieldValue, String> mapper) {
		return getComponent().getUrl().map(mapper).orElse(null);
	}
}
