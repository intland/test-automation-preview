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

package com.intland.codebeamer.integration.classic.page.wiki.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class WikiPageContentAssertions
		extends AbstractCodebeamerComponentAssert<WikiContentComponent, WikiPageContentAssertions> {

	private static final String EDITED_OR_NOT_CONTENT_PATTERN = "\\b(\\[edit\\])?%s\\b";

	protected WikiPageContentAssertions(WikiContentComponent component) {
		super(component);
	}

	public WikiPageContentAssertions contentOfSectionIs(String sectionTitle, String content) {
		return assertAll("Content of section '%s' should be: '%s'".formatted(sectionTitle, content), () ->
				assertThat(getComponent().getSection(sectionTitle)).hasText(
						Pattern.compile(EDITED_OR_NOT_CONTENT_PATTERN.formatted(content))));
	}

	public WikiPageContentAssertions hasSectionWithTitle(String sectionTitle) {
		return assertAll("Component has section with title: '%s'".formatted(sectionTitle), () ->
				assertThat(getComponent().getSection(sectionTitle)).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
}
