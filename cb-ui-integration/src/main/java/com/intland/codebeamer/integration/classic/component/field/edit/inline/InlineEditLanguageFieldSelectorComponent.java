package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import java.util.Arrays;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditLanguageFieldSelectorComponent;
import com.microsoft.playwright.Locator;

public class InlineEditLanguageFieldSelectorComponent extends
		AbstractEditLanguageFieldSelectorComponent<InlineEditLanguageFieldSelectorComponent, InlineEditLanguageFieldSelectorAssertions> {

	public InlineEditLanguageFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditLanguageFieldSelectorComponent selectOption(Language... language) {
		waitForReferenceInlineEditStart();

		Arrays.stream(language).forEach(this::selectLanguage);

		stopInlineEdit();
		return this;
	}

	public InlineEditLanguageFieldSelectorComponent removeOption(Language... language) {
		waitForReferenceInlineEditStart();

		Arrays.stream(language).forEach(this::removeLanguage);

		stopInlineEdit();
		return this;
	}

	public InlineEditLanguageFieldSelectorComponent removeAllOptions() {
		waitForReferenceInlineEditStart();

		getSelectedLanguages().forEach(this::removeLanguage);

		stopInlineEdit();
		return this;
	}

	@Override
	public InlineEditLanguageFieldSelectorAssertions assertThat() {
		return new InlineEditLanguageFieldSelectorAssertions(this);
	}

	@Override
	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2).setDelay(500));
		waitForReferenceInlineEditEnd();
	}

	private void selectLanguage(Language language) {
		getValueField().type(language.getName());

		this.languageDialogComponent.select(language);

		getElement(language).waitForAttached();
	}

	private void removeLanguage(Language language) {
		CodebeamerLocator element = getElement(language);
		element.waitForAttached();

		getRemoveElement(language).click();

		element.waitForDetached();
	}

}
