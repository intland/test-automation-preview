package com.intland.codebeamer.integration.classic.component.field.edit;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

public class EditFieldComponentFactory {

	private final DataManagerService dataManagerService;

	private final CodebeamerPage codebeamerPage;
	
	private final String fieldName;

	private final Tracker tracker;

	public EditFieldComponentFactory(DataManagerService dataManagerService, CodebeamerPage codebeamerPage, Tracker tracker, String fieldName) {
		this.dataManagerService = dataManagerService;
		this.codebeamerPage = codebeamerPage;
		this.tracker = tracker;
		this.fieldName = fieldName;
	}

	public EditLanguageFieldSelectorComponent getLanguageFieldSelectorComponent () {
		return new EditLanguageFieldSelectorComponent(codebeamerPage, fieldName);
	}
	
	public EditCountryFieldSelectorComponent getCountryFieldSelectorComponent() {
		return new EditCountryFieldSelectorComponent(codebeamerPage, fieldName);
	}
	
	public EditRoleAndMemberSelectorComponent getRoleAndMemberSelectorComponent() {
		return new EditRoleAndMemberSelectorComponent(codebeamerPage, fieldName);
	}
	
	public EditChoiceFieldComponent getChoiceFieldComponent() {
		return new EditChoiceFieldComponent(dataManagerService, codebeamerPage, tracker, fieldName);
	}
	
	public EditTextFieldComponent getTextFieldComponent() {
		return new EditTextFieldComponent(codebeamerPage, fieldName);
	}
	
	public EditWikiTextFieldComponent getWikiTextFieldComponent() {
		return new EditWikiTextFieldComponent(codebeamerPage, fieldName);
	}
	
	public EditIntegerFieldComponent getIntegerFieldComponent() {
		return new EditIntegerFieldComponent(codebeamerPage, fieldName);
	}

	public EditDecimalFieldComponent getDecimalFieldComponent() {
		return new EditDecimalFieldComponent(codebeamerPage, fieldName);
	}
	
	public EditDateFieldComponent getDateFieldComponent() {
		return new EditDateFieldComponent(codebeamerPage, fieldName);
	}
	
	public EditBooleanFieldComponent getBooleanFieldComponent() {
		return new EditBooleanFieldComponent(codebeamerPage, fieldName);
	}
	
	public EditDurationFieldComponent getDurationFieldComponent() {
		return new EditDurationFieldComponent(codebeamerPage, fieldName);
	}

	public EditColorFieldComponent getColorFieldComponent() {
		return new EditColorFieldComponent(codebeamerPage, fieldName);
	}

	public EditReferenceComponent getReferenceEditComponent() {
		return new EditReferenceComponent(codebeamerPage, fieldName);
	}


}
