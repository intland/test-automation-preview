package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.CodebeamerPage;

public class FieldComponentFactory {

	private final CodebeamerPage codebeamerPage;
	
	private final String fieldName;

	public FieldComponentFactory(CodebeamerPage codebeamerPage, String fieldName) {
		this.codebeamerPage = codebeamerPage;
		this.fieldName = fieldName;
	}

	public CountrySelectorComponent getCountrySelectorComponent() {
		return new CountrySelectorComponent(codebeamerPage, fieldName);
	}
	
	public LanguageSelectorComponent getLanguageSelectorComponent() {
		return new LanguageSelectorComponent(codebeamerPage, fieldName);
	}
	
	public RoleAndMemberSelectorComponent getRoleAndMemberSelectorComponent() {
		return new RoleAndMemberSelectorComponent(codebeamerPage, fieldName);
	}
	
	public ChoiceFieldComponent getChoiceFieldComponent() {
		return new ChoiceFieldComponent(codebeamerPage, fieldName);
	}
	
	public TextFieldComponent getTextFieldComponent() {
		return new TextFieldComponent(codebeamerPage, fieldName);
	}
	
	public WikiTextFieldComponent getWikiTextFieldComponent() {
		return new WikiTextFieldComponent(codebeamerPage, fieldName);
	}
	
	public IntegerFieldComponent getIntegerFieldComponent() {
		return new IntegerFieldComponent(codebeamerPage, fieldName);
	}

	public DecimalFieldComponent getDecimalFieldComponent() {
		return new DecimalFieldComponent(codebeamerPage, fieldName);
	}
	
	public DateFieldComponent getDateFieldComponent() {
		return new DateFieldComponent(codebeamerPage, fieldName);
	}
	
	public BooleanFieldComponent getBooleanFieldComponent() {
		return new BooleanFieldComponent(codebeamerPage, fieldName);
	}
	
	public DurationFieldComponent getDurationFieldComponent() {
		return new DurationFieldComponent(codebeamerPage, fieldName);
	}

	public ColorFieldComponent getColorFieldComponent() {
		return new ColorFieldComponent(codebeamerPage, fieldName);
	}
	
}
