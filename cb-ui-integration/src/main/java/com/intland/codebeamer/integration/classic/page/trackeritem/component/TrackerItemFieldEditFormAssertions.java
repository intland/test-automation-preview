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

package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.classic.component.field.edit.form.EditBooleanFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditChoiceFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditColorFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCountryFieldSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDateFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDecimalFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDurationFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditIntegerFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditLanguageFieldSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditReferenceFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditRoleAndMemberSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditTextAreaFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditTextFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditUrlFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditWikiTextFieldAssertions;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemFieldEditFormAssertions extends AbstractCodebeamerComponentAssert<TrackerItemFieldEditFormComponent, TrackerItemFieldEditFormAssertions> {

	protected TrackerItemFieldEditFormAssertions(TrackerItemFieldEditFormComponent component) {
		super(component);
	}
	
	public TrackerItemFieldEditFormAssertions choiceField(String fieldName, Consumer<EditChoiceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getChoiceFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions wikiTextField(String fieldName, Consumer<EditWikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getWikiTextFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions textField(String fieldName, Consumer<EditTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getTextFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldEditFormAssertions textAreaField(String fieldName, Consumer<EditTextAreaFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getTextAreaFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldEditFormAssertions integerField(String fieldName, Consumer<EditIntegerFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getIntegerFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions decimalField(String fieldName, Consumer<EditDecimalFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getDecimalFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions dateField(String fieldName, Consumer<EditDateFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getDateFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions booleanField(String fieldName, Consumer<EditBooleanFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getBooleanFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions durationField(String fieldName, Consumer<EditDurationFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getDurationFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldEditFormAssertions referenceField(String fieldName, Consumer<EditReferenceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getReferenceEditComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions colorField(String fieldName, Consumer<EditColorFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getColorFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions urlField(String fieldName, Consumer<EditUrlFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getUrlEditComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions countryField(String fieldName, Consumer<EditCountryFieldSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getCountryFieldSelectorComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions languageField(String fieldName, Consumer<EditLanguageFieldSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getLanguageFieldSelectorComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}
	
	public TrackerItemFieldEditFormAssertions memberField(String fieldName, Consumer<EditRoleAndMemberSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory().getRoleAndMemberSelectorComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldEditFormAssertions hasError() {
		assertAll("Form should have error message", () -> getComponent().getGlobalMessagesComponent().assertThat().hasError());
		return this;
	}

	private String getFieldSelector(String fieldName) {
		return getComponent().getFieldSelector(fieldName);
	}
}
