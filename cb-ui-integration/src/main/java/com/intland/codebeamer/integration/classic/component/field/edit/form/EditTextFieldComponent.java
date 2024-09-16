package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditTextFieldComponent;

public class EditTextFieldComponent extends AbstractEditTextFieldComponent<EditTextFieldComponent, EditTextFieldAssertions> {

	public EditTextFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditTextFieldComponent fill(String value) {
		getValueField().click().clear().fill(value);
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public EditTextFieldAssertions assertThat() {
		return new EditTextFieldAssertions(this);
	}

}
