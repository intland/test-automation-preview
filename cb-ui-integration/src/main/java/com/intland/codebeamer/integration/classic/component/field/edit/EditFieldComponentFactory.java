package com.intland.codebeamer.integration.classic.component.field.edit;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditBooleanFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditChoiceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditColorFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCountryFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDateFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDecimalFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditDurationFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditIntegerFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditLanguageFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditReferenceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditTextAreaFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditUrlFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditWikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditBooleanFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditChoiceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditColorFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditCountryFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditDateFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditDecimalFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditDurationFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditIntegerFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditLanguageFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditReferenceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditTextAreaFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditUrlFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditWikiTextFieldComponent;

public class EditFieldComponentFactory {

	private final CodebeamerPage codebeamerPage;

	private final String frameLocator;

	public EditFieldComponentFactory(CodebeamerPage codebeamerPage, String frameLocator) {
		this.codebeamerPage = codebeamerPage;
		this.frameLocator = frameLocator;
	}

	public EditLanguageFieldSelectorComponent getLanguageFieldSelectorComponent(String fieldLocator) {
		return new EditLanguageFieldSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditLanguageFieldSelectorComponent getInlineLanguageFieldSelectorComponent(String fieldLocator) {
		return new InlineEditLanguageFieldSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditCountryFieldSelectorComponent getCountryFieldSelectorComponent(String fieldLocator) {
		return new EditCountryFieldSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditCountryFieldSelectorComponent getInlineCountryFieldSelectorComponent(String fieldLocator) {
		return new InlineEditCountryFieldSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditRoleAndMemberSelectorComponent getRoleAndMemberSelectorComponent(String fieldLocator) {
		return new EditRoleAndMemberSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditRoleAndMemberSelectorComponent getInlineRoleAndMemberSelectorComponent(String fieldLocator) {
		return new InlineEditRoleAndMemberSelectorComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditChoiceFieldComponent getChoiceFieldComponent(String fieldLocator) {
		return new EditChoiceFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditChoiceFieldComponent getInlineChoiceFieldComponent(String fieldLocator) {
		return new InlineEditChoiceFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditTextFieldComponent getTextFieldComponent(String fieldLocator) {
		return new EditTextFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditTextFieldComponent getInlineTextFieldComponent(String fieldLocator) {
		return new InlineEditTextFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditTextAreaFieldComponent getTextAreaFieldComponent(String fieldLocator) {
		return new EditTextAreaFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditTextAreaFieldComponent getInlineTextAreaFieldComponent(String fieldLocator) {
		return new InlineEditTextAreaFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditWikiTextFieldComponent getWikiTextFieldComponent(String fieldLocator) {
		return new EditWikiTextFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditWikiTextFieldComponent getInlineWikiTextFieldComponent(String fieldLocator) {
		return new InlineEditWikiTextFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditIntegerFieldComponent getIntegerFieldComponent(String fieldLocator) {
		return new EditIntegerFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditIntegerFieldComponent getInlineIntegerFieldComponent(String fieldLocator) {
		return new InlineEditIntegerFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditDecimalFieldComponent getDecimalFieldComponent(String fieldLocator) {
		return new EditDecimalFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditDecimalFieldComponent getInlineDecimalFieldComponent(String fieldLocator) {
		return new InlineEditDecimalFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditDateFieldComponent getDateFieldComponent(String fieldLocator) {
		return new EditDateFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditDateFieldComponent getInlineDateFieldComponent(String fieldLocator) {
		return new InlineEditDateFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditBooleanFieldComponent getBooleanFieldComponent(String fieldLocator) {
		return new EditBooleanFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditBooleanFieldComponent getInlineBooleanFieldComponent(String fieldLocator) {
		return new InlineEditBooleanFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditDurationFieldComponent getDurationFieldComponent(String fieldLocator) {
		return new EditDurationFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditDurationFieldComponent getInlineDurationFieldComponent(String fieldLocator) {
		return new InlineEditDurationFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditColorFieldComponent getColorFieldComponent(String fieldLocator) {
		return new EditColorFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditColorFieldComponent getInlineColorFieldComponent(String fieldLocator) {
		return new InlineEditColorFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditReferenceFieldComponent getReferenceEditComponent(String fieldLocator) {
		return new EditReferenceFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditReferenceFieldComponent getInlineReferenceEditComponent(String fieldLocator) {
		return new InlineEditReferenceFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public EditUrlFieldComponent getUrlEditComponent(String fieldLocator) {
		return new EditUrlFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

	public InlineEditUrlFieldComponent getInlineUrlEditComponent(String fieldLocator) {
		return new InlineEditUrlFieldComponent(codebeamerPage, fieldLocator, frameLocator);
	}

}
