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

package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.classic.component.field.edit.EditBooleanFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditChoiceFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditColorFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditCountryFieldSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditDateFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditDecimalFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditDurationFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditIntegerFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditLanguageFieldSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditReferenceAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditTextFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.EditWikiTextFieldAssertions;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemFieldCreateFormAssertions extends AbstractCodebeamerComponentAssert<TrackerItemFieldCreateFormComponent, TrackerItemFieldCreateFormAssertions> {

	protected TrackerItemFieldCreateFormAssertions(TrackerItemFieldCreateFormComponent component) {
		super(component);
	}
	
	public TrackerItemFieldCreateFormAssertions choiceField(String fieldName, Consumer<EditChoiceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getChoiceFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions wikiText(String fieldName, Consumer<EditWikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getWikiTextFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions textField(String fieldName, Consumer<EditTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getTextFieldComponent().assertThat());
		return this;
	}

	public TrackerItemFieldCreateFormAssertions integerField(String fieldName, Consumer<EditIntegerFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getIntegerFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions decimalField(String fieldName, Consumer<EditDecimalFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDecimalFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions dateField(String fieldName, Consumer<EditDateFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDateFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions booleanField(String fieldName, Consumer<EditBooleanFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getBooleanFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions durationField(String fieldName, Consumer<EditDurationFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDurationFieldComponent().assertThat());
		return this;
	}

	public TrackerItemFieldCreateFormAssertions referenceField(String fieldName, Consumer<EditReferenceAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getReferenceEditComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions colorField(String fieldName, Consumer<EditColorFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getColorFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions countryField(String fieldName, Consumer<EditCountryFieldSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getCountryFieldSelectorComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldCreateFormAssertions languageField(String fieldName, Consumer<EditLanguageFieldSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getLanguageFieldSelectorComponent().assertThat());
		return this;
	}
}
