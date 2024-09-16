package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDurationFieldComponent;

public class EditDurationFieldComponent extends
		AbstractEditDurationFieldComponent<EditDurationFieldComponent, EditDurationFieldAssertions> {

	public EditDurationFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditDurationFieldComponent fill(String value) {
		validateInput(value);
		getValueField().click().clear().fill(value);
		return this;
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public EditDurationFieldAssertions assertThat() {
		return new EditDurationFieldAssertions(this);
	}

}
