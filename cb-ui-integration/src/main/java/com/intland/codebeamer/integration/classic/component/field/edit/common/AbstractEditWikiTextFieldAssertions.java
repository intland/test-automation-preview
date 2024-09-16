package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.time.Duration;

import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;
import com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions;

public abstract class AbstractEditWikiTextFieldAssertions<C extends AbstractEditWikiTextFieldComponent<C, A>, A extends AbstractEditWikiTextFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditWikiTextFieldAssertions(C component) {
		super(component);
	}

	public A is(String value) {
		return assertAll("Field should have '%s' value".formatted(value),
				() -> assertThat(getComponent().getFroala().getRichTextValueElement()).hasText(value));
	}

	public A contains(String value) {
		return assertAll("Field should contain '%s' value".formatted(value),
				() -> assertThat(getComponent().getFroala().getRichTextValueElement()).containsText(value));
	}
	
	public A isRichTextModeActive() {
		return isRichTextModeActive(null);
	}
	
	public A isRichTextModeActive(Duration timeout) {
		return assertAll("RichText mode should be selected", () -> {
			assertThat(getComponent().getFroala().getRichTextElement()).isVisible(createVisibleOptions(timeout));
			assertThat(getComponent().getFroala().getRichTextElement()).hasClass("cb-button-active");
		});
	}
	
	public A isMarkUpModeActive() {
		return isMarkUpModeActive(null);
	}
	
	public A isMarkUpModeActive(Duration timeout) {
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
