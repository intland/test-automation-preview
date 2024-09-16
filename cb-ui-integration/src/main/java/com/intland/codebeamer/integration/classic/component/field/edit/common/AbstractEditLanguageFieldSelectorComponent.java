package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.dialog.EditLanguageDialogComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public abstract class AbstractEditLanguageFieldSelectorComponent<C extends AbstractEditLanguageFieldSelectorComponent<C, A>, A extends AbstractEditLanguageFieldSelectorAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	@Component(value = "Edit language dialog", includeInSitemap = false)
	protected EditLanguageDialogComponent languageDialogComponent;

	public AbstractEditLanguageFieldSelectorComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.languageDialogComponent = new EditLanguageDialogComponent(codebeamerPage);
	}

	public List<Language> getSelectedLanguages() {
		return getSelectedLanguageElement().all().stream()
				.map(CodebeamerLocator::text)
				.map(Language::findByName)
				.toList();
	}

	public CodebeamerLocator getElement(Language language) {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li:has(p:text-is('%s'))".formatted(language.getName()));
	}

	public CodebeamerLocator getRemoveElement(Language language) {
		return getElement(language).concat("span.token-input-delete-token-facebook");
	}

	public CodebeamerLocator getSelectedLanguageElement() {
		getValueContainerElement().waitForAttached();
		return this.locator(" ul li p.name");
	}

	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" ul.token-input-list-facebook");
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" ul li input[type=text]");
	}

}
