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

import java.time.Duration;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions;

public class EditWikiTextFieldAssertions extends AbstractCodebeamerComponentAssert<EditWikiTextFieldComponent, EditWikiTextFieldAssertions> {

	protected EditWikiTextFieldAssertions(EditWikiTextFieldComponent component) {
		super(component);
	}

	public EditWikiTextFieldAssertions is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getFroala().getRichTextValueElement()).hasText(value));
	}

	public EditWikiTextFieldAssertions contains(String value) {
		return assertAll("Field should contain '%s' value".formatted(value),
				() -> assertThat(getComponent().getFroala().getRichTextValueElement()).containsText(value));
	}
	
	public EditWikiTextFieldAssertions isRichTextModeActive() {
		return isRichTextModeActive(null);
	}
	
	public EditWikiTextFieldAssertions isRichTextModeActive(Duration timeout) {
		return assertAll("RichText mode should be selected", () -> {
			assertThat(getComponent().getFroala().getRichTextElement()).isVisible(createVisibleOptions(timeout));
			assertThat(getComponent().getFroala().getRichTextElement()).hasClass("cb-button-active");
		});
	}
	
	public EditWikiTextFieldAssertions isMarkUpModeActive() {
		return isMarkUpModeActive(null);
	}
	
	public EditWikiTextFieldAssertions isMarkUpModeActive(Duration timeout) {
		return assertAll("MarkUp mode should be selected", () -> {
			assertThat(getComponent().getFroala().getMarkupElement()).isVisible(createVisibleOptions(timeout));
			assertThat(getComponent().getFroala().getMarkupElement()).hasClass("cb-button-active");
		});
	}

	private IsVisibleOptions createVisibleOptions(Duration timeout) {
		if (timeout == null) {
			return null;
		} 
		return new IsVisibleOptions().setTimeout(timeout.toMillis());
	}

}
