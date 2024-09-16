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

package com.intland.codebeamer.integration.classic.component;

import java.time.Duration;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions;

public class FroalaComponentAssertions extends AbstractCodebeamerComponentAssert<FroalaComponent, FroalaComponentAssertions> {

	protected FroalaComponentAssertions(FroalaComponent component) {
		super(component);
	}

	public FroalaComponentAssertions isActive() {
		return isActive(null);
	}
	
	public FroalaComponentAssertions isActive(Duration timeout) {
		return assertAll("Editor should be active",
				() -> assertThat(getComponent().getRichTextValueElement()).isVisible(createVisibleOptions(timeout)));
	}
	
	public FroalaComponentAssertions is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getRichTextValueElement()).hasText(value));
	}

	public FroalaComponentAssertions containsText(String value) {
		return assertAll("Editor should contain '%s'".formatted(value),
				() -> assertThat(getComponent().getRichTextValueElement()).containsText(value));
	}

	public FroalaComponentAssertions isRichTextModeActive() {
		return isRichTextModeActive(null);
	}
	
	public FroalaComponentAssertions isRichTextModeActive(Duration timeout) {
		return assertAll("RichText mode should be selected", () -> {
			assertThat(getComponent().getRichTextElement()).isVisible(createVisibleOptions(timeout));
			assertThat(getComponent().getRichTextElement()).hasClass(Pattern.compile("cb-button-active"));
		});
	}

	public FroalaComponentAssertions isRichTextOptionsToolbarIsVisible() {
		return assertAll("RichText toolbar should be visible",
				() -> assertThat(getComponent().getRichTextOptionsElement()).hasClass(Pattern.compile("cb-button-active")));
	}

	public FroalaComponentAssertions isRichTextOptionsToolbarIsHidden() {
		return assertAll("RichText toolbar should be hidden",
				() -> assertThat(getComponent().getRichTextElement()).hasClass(Pattern.compile("cb-button-active")));
	}

	public FroalaComponentAssertions isMarkUpModeActive() {
		return isMarkUpModeActive(null);
	}
	
	public FroalaComponentAssertions isMarkUpModeActive(Duration timeout) {
		return assertAll("MarkUp mode should be selected", () -> {
			assertThat(getComponent().getMarkupElement()).isVisible(createVisibleOptions(timeout));
			assertThat(getComponent().getMarkupElement()).hasClass(Pattern.compile("cb-button-active"));
		});
	}

	private IsVisibleOptions createVisibleOptions(Duration timeout) {
		if (timeout == null) {
			return null;
		} 
		return new IsVisibleOptions().setTimeout(timeout.toMillis());
	}

}
