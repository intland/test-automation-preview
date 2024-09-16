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

import java.util.function.Function;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter.UrlFieldValue;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class UrlFieldAssertions extends AbstractCodebeamerComponentAssert<UrlFieldComponent, UrlFieldAssertions> {

	protected UrlFieldAssertions(UrlFieldComponent component) {
		super(component);
	}

	public UrlFieldAssertions isUrl(String url) {
		return assertAll("Field should contain: %s".formatted(url), () -> assertUrl(url, UrlFieldValue::url));
	}

	public UrlFieldAssertions isWikiLink(String url) {
		return assertAll("Field should contain: %s".formatted(url), () -> assertWikiLinkUrl(url, UrlFieldValue::url));
	}

	public UrlFieldAssertions isAlias(String alias) {
		return assertAll("Field should contain: %s".formatted(alias), () -> assertUrl(alias, UrlFieldValue::alias));
	}

	public void assertUrlIsEmpty() {
		assertThat(getComponent().getValueElement()).hasText("--");
	}

	private void assertUrl(String url, Function<UrlFieldValue, String> mapper) {
		assertThat(getComponent().getValueElement()).isAttached();
		MatcherAssert.assertThat(
				getComponent().getUrl().map(mapper).orElseThrow(() -> new AssertionError("Url should exist")),
				Matchers.equalTo(url));
	}

	private void assertWikiLinkUrl(String url, Function<UrlFieldValue, String> mapper) {
		assertThat(getComponent().getValueElement()).isAttached();
		MatcherAssert.assertThat(
				getComponent().getUrl().map(mapper).orElseThrow(() -> new AssertionError("WikiLink url should exist")),
				Matchers.endsWith(url));
	}

}
