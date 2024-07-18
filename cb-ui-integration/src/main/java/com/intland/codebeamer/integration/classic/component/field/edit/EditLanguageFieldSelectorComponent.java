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

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditLanguageFieldSelectorComponent extends AbstractCodebeamerComponent<EditLanguageFieldSelectorComponent, EditLanguageFieldSelectorAssertions> {

	private EditLanguageDialogComponent languageDialogComponent;

	public EditLanguageFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.languageDialogComponent = new EditLanguageDialogComponent(codebeamerPage);
	}

	public EditLanguageFieldSelectorComponent selectOption(Language language) {
		getValueField().doubleClick().type(language.getName());
		this.languageDialogComponent.select(language);
		return this;
	}
	
	public List<Language> getLanguage() {
		return this.locator(" + td.fieldValue ul li p.name").all().stream()
				.map(CodebeamerLocator::text)
				.map(Language::findByName)
				.toList();
	}
	
	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" + td.fieldValue ul.token-input-list-facebook");
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}

	@Override
	public EditLanguageFieldSelectorAssertions assertThat() {
		return new EditLanguageFieldSelectorAssertions(this);
	}

}
