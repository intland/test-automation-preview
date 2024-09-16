package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditLanguageFieldSelectorComponent;

public class EditLanguageFieldSelectorComponent extends
		AbstractEditLanguageFieldSelectorComponent<EditLanguageFieldSelectorComponent, EditLanguageFieldSelectorAssertions> {

	public EditLanguageFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditLanguageFieldSelectorComponent removeAllOptions() {
		getValueField().click();
		getSelectedLanguages().forEach(c -> getRemoveElement(c).click());
		return this;
	}

	public EditLanguageFieldSelectorComponent removeOption(Language language) {
		getValueField().click();
		getRemoveElement(language).click();
		return this;
	}

	public EditLanguageFieldSelectorComponent selectOption(Language language) {
		if (getSelectedLanguages().contains(language)) {
			throw new AssertionError("Language is already added to the field");
		}

		getValueField().click().type(language.getName());
		this.languageDialogComponent.select(language);
		return this;
	}

	@Override
	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}

	@Override
	public EditLanguageFieldSelectorAssertions assertThat() {
		return new EditLanguageFieldSelectorAssertions(this);
	}

}
