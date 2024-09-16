package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.CodebeamerPage;

public class FieldComponentFactory {

	private final CodebeamerPage codebeamerPage;

	public FieldComponentFactory(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public ReferenceFieldComponent getReferenceFieldComponent(String fieldLocator) {
		return new ReferenceFieldComponent(codebeamerPage, fieldLocator);
	}

	public UrlFieldComponent getUrlFieldComponent(String fieldLocator) {
		return new UrlFieldComponent(codebeamerPage, fieldLocator);
	}

	public CountrySelectorComponent getCountrySelectorComponent(String fieldLocator) {
		return new CountrySelectorComponent(codebeamerPage, fieldLocator);
	}

	public LanguageSelectorComponent getLanguageSelectorComponent(String fieldLocator) {
		return new LanguageSelectorComponent(codebeamerPage, fieldLocator);
	}

	public RoleAndMemberSelectorComponent getRoleAndMemberSelectorComponent(String fieldLocator) {
		return new RoleAndMemberSelectorComponent(codebeamerPage, fieldLocator);
	}

	public ChoiceFieldComponent getChoiceFieldComponent(String fieldLocator) {
		return new ChoiceFieldComponent(codebeamerPage, fieldLocator);
	}

	public TextFieldComponent getTextFieldComponent(String fieldLocator) {
		return new TextFieldComponent(codebeamerPage, fieldLocator);
	}

	public WikiTextFieldComponent getWikiTextFieldComponent(String fieldLocator) {
		return new WikiTextFieldComponent(codebeamerPage, fieldLocator);
	}

	public IntegerFieldComponent getIntegerFieldComponent(String fieldLocator) {
		return new IntegerFieldComponent(codebeamerPage, fieldLocator);
	}

	public DecimalFieldComponent getDecimalFieldComponent(String fieldLocator) {
		return new DecimalFieldComponent(codebeamerPage, fieldLocator);
	}

	public DateFieldComponent getDateFieldComponent(String fieldLocator) {
		return new DateFieldComponent(codebeamerPage, fieldLocator);
	}

	public BooleanFieldComponent getBooleanFieldComponent(String fieldLocator) {
		return new BooleanFieldComponent(codebeamerPage, fieldLocator);
	}

	public DurationFieldComponent getDurationFieldComponent(String fieldLocator) {
		return new DurationFieldComponent(codebeamerPage, fieldLocator);
	}

	public ColorFieldComponent getColorFieldComponent(String fieldLocator) {
		return new ColorFieldComponent(codebeamerPage, fieldLocator);
	}

	public LabelComponent getLabelComponent(String fieldLocator) {
		return new LabelComponent(codebeamerPage, fieldLocator);
	}

}
