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

import java.util.Arrays;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.EditCountryFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.EditFieldComponentFactory;
import com.intland.codebeamer.integration.classic.component.field.edit.EditLanguageFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.EditReferenceComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.EditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.EditWikiTextFieldComponent;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemFieldCreateFormComponent extends AbstractCodebeamerComponent<TrackerItemFieldCreateFormComponent, TrackerItemFieldCreateFormAssertions> {

	private DataManagerService dataManagerService;
	
	private Tracker tracker;

	public TrackerItemFieldCreateFormComponent(DataManagerService dataManagerService, CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "#addUpdateTaskForm");
		this.dataManagerService = dataManagerService;
		this.tracker = tracker;
	}

	@Override
	public TrackerItemFieldCreateFormAssertions assertThat() {
		return new TrackerItemFieldCreateFormAssertions(this);
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input[type='submit'][name='SUBMIT']");
	}
	
	public CodebeamerLocator getSaveAndNewButton() {
		return this.locator("input[type='submit'][name='SUBMIT_AND_NEW']");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input[type='submit'][name='_cancel']");
	}

	public TrackerItemFieldCreateFormComponent setTextField(String fieldName, String value) {
		return field(fieldName, f -> f.getTextFieldComponent().fill(value));
	}
	
	public TrackerItemFieldCreateFormComponent setWikiText(String fieldName, String value) {
		return field(fieldName, f -> f.getWikiTextFieldComponent().fill(value));
	}
	
	public TrackerItemFieldCreateFormComponent setWikiText(String fieldName, Consumer<EditWikiTextFieldComponent> wikiTextFieldComponentConsumer) {
		return field(fieldName, f -> wikiTextFieldComponentConsumer.accept(f.getWikiTextFieldComponent()));
	}
	
	public TrackerItemFieldCreateFormComponent setChoiceField(String fieldName, String value) {
		return field(fieldName, f -> f.getChoiceFieldComponent().selectOption(value));
	}
	
	public TrackerItemFieldCreateFormComponent setDurationField(String fieldName, String value) {
		return field(fieldName, f -> f.getDurationFieldComponent().fill(value));
	}
	
	public TrackerItemFieldCreateFormComponent setColorField(String fieldName, HtmlColor htmlColor) {
		return field(fieldName, f -> f.getColorFieldComponent().fill(htmlColor));
	}
	
	public TrackerItemFieldCreateFormComponent setIntegerField(String fieldName, int value) {
		return field(fieldName, f -> f.getIntegerFieldComponent().fill(value));
	}
	
	public TrackerItemFieldCreateFormComponent setDecimalField(String fieldName, double value) {
		return field(fieldName, f -> f.getDecimalFieldComponent().fill(value));
	}
	
	public TrackerItemFieldCreateFormComponent setDateField(String fieldName, int year, int month, int day, int hour, int minute) {
		return field(fieldName, f -> f.getDateFieldComponent().fill(year, month, day, hour, minute));
	}
	
	public TrackerItemFieldCreateFormComponent setBooleanField(String fieldName, boolean value) {
		return field(fieldName, f -> f.getBooleanFieldComponent().selectOption(value));
	}
	
	public TrackerItemFieldCreateFormComponent addCountryField(String fieldName, Country... county) {
		return field(fieldName, f -> {
			EditCountryFieldSelectorComponent component = f.getCountryFieldSelectorComponent();
			Arrays.stream(county).forEach(component::selectOption);
		});
	}
	
	public TrackerItemFieldCreateFormComponent addLanguageField(String fieldName, Language... language) {
		return field(fieldName, f -> {
			EditLanguageFieldSelectorComponent component = f.getLanguageFieldSelectorComponent();
			Arrays.stream(language).forEach(component::selectOption);
		});
	}
	
	public TrackerItemFieldCreateFormComponent addUserTo(String fieldName, String... username) {
		return field(fieldName, f -> {
			EditRoleAndMemberSelectorComponent component = f.getRoleAndMemberSelectorComponent();
			Arrays.stream(username).forEach(component::selectUser);
		});
	}

	public TrackerItemFieldCreateFormComponent addReferenceTo(String fieldName, String... references) {
		return field(fieldName, f -> {
			EditReferenceComponent component = f.getReferenceEditComponent();
			Arrays.stream(references).forEach(component::select);
		});
	}

	public TrackerItemFieldCreateFormComponent addReferenceToPosition(String fieldName, String reference, int position) {
		return field(fieldName, f -> {
			EditReferenceComponent component = f.getReferenceEditComponent();
			component.select(reference, position);
		});
	}

	public TrackerItemFieldCreateFormComponent addRoleTo(String fieldName, String... role) {
		return field(fieldName, f -> {
			EditRoleAndMemberSelectorComponent component = f.getRoleAndMemberSelectorComponent();
			Arrays.stream(role).forEach(component::selectRole);
		});
	}
	
	public TrackerItemFieldCreateFormComponent field(String fieldName, Consumer<EditFieldComponentFactory> factory) {
		factory.accept(getFieldComponentFactory(fieldName));
		return this;
	}
	
	protected EditFieldComponentFactory getFieldComponentFactory(String fieldName) {
		return new EditFieldComponentFactory(dataManagerService, getCodebeamerPage(), tracker, fieldName);
	}

}
