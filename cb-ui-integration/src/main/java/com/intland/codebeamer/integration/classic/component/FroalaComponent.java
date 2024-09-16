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

import java.nio.file.Path;
import java.time.Duration;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public class FroalaComponent extends AbstractCodebeamerComponent<FroalaComponent, FroalaComponentAssertions> {

	public FroalaComponent(CodebeamerPage codebeamerPage, String frameLocator, String parentSelector) {
		super(codebeamerPage, frameLocator, parentSelector + " div.editor-wrapper");
	}
	
	public FroalaComponent(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + " div.editor-wrapper");
	}

	public FroalaComponent save() {
		getSaveButton().click(new Locator.ClickOptions().setDelay(200));
		getSaveButton().waitForDetached();
		return this;
	}

	public FroalaComponent pressEnter() {
		getMarkUpTextArea().pressEnter();
		return this;
	}

	public FroalaComponent pasteFromClipboard() {
		getRichTextValueElement().press("Control+v");
		return this;
	}
	
	public FroalaComponent fill(String value) {
		return fill(value, Type.MARKUP);
	}
	
	public FroalaComponent fill(String value, Type type) {
		switchToEditorType(type);
		if (Type.MARKUP.equals(type)) {
			getMarkUpTextArea().fill(value);
			switchToEditorType(Type.RICH_TEXT);
		}
		
		if (Type.RICH_TEXT.equals(type)) {
			getRichTextValueElement().fill(value);
		}
		return this;
	}
	
	public FroalaComponent switchToEditorType(Type type) {
		if (Type.MARKUP.equals(type)) {
			if (isMarkUpModeActive()) {
				return this;
			}

			runAfterOpeningRichTextOptionsToolbar(() -> getMarkupElement().click());
			return this;
		}

		if (Type.RICH_TEXT.equals(type)) {
			if (isRichTextModeActive()) {
				return this;
			}
			
			getRichText2Element().scrollIntoView().click();
			return this;
		}
		
		throw new IllegalArgumentException("Type(%s) is not suppoted".formatted(type));
	}

	public FroalaComponent addAttachment(Path attachment) {
		getCodebeamerPage().uploadFiles(() -> getUploadFileButton().click(), attachment);
		pressEnter();
		return this;
	}
	
	public CodebeamerLocator getUploadFileButton() {
		return this.locator("button[data-cmd='insertFile']");
	}
	
	public CodebeamerLocator getSaveButton() {
		return this.locator("button[data-cmd='cbSave']");
	}
	
	public CodebeamerLocator getCancelButton() {
		return this.locator("button[data-cmd='cbCancel']");
	}
	
	public CodebeamerLocator getMarkupElement() {
		return this.locator("div[data-command='cbWysiwygOptions'] button[data-cmd='cbMarkup']");
	}
	
	// Better names
	public CodebeamerLocator getRichText2Element() {
		return this.locator("div.fr-btn-grp button[data-cmd='cbWysiwyg']");
	}
	
	public CodebeamerLocator getRichTextElement() {
		return this.locator("div[data-command='cbWysiwygOptions'] button[data-cmd='cbWysiwyg']");
	}
	
	public CodebeamerLocator getRichTextOptionsElement() {
		return this.locator("button[data-cmd='cbWysiwygOptions']");
	}
	
	public CodebeamerLocator getMarkUpTextArea() {
		return this.locator("textarea[id^='editor']");
	}
	
	public CodebeamerLocator getRichTextValueElement() {
		return this.locator(".fr-element");
	}
	
	@Override
	public FroalaComponentAssertions assertThat() {
		return new FroalaComponentAssertions(this);
	}

	public enum Type {
		RICH_TEXT,
		MARKUP
	}
	
	private boolean isMarkUpModeActive() {
		try {
			runAfterOpeningRichTextOptionsToolbar(() -> assertThat().isMarkUpModeActive(Duration.ofSeconds(1)));
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}
	
	private boolean isRichTextModeActive() {
		try {
			runAfterOpeningRichTextOptionsToolbar(() -> assertThat().isRichTextModeActive(Duration.ofSeconds(1)));
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	private boolean isRichTextOptionsToolbarVisible() {
		try {
			assertThat().isRichTextOptionsToolbarIsVisible();
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	private void runAfterOpeningRichTextOptionsToolbar(Runnable runnable) {
		openRichTextOptionsToolbar();
		runnable.run();
		closeRichTextOptionsToolbar();
	}

	private void openRichTextOptionsToolbar() {
		if (!isRichTextOptionsToolbarVisible()) {
			getRichTextOptionsElement().scrollIntoView().click();
		}
	}

	private void closeRichTextOptionsToolbar() {
		if (isRichTextOptionsToolbarVisible()) {
			getRichTextOptionsElement().scrollIntoView().click();
		}
	}
	
}
