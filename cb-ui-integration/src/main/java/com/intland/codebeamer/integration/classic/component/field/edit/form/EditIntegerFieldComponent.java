package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditIntegerFieldComponent;

public class EditIntegerFieldComponent extends
		AbstractEditIntegerFieldComponent<EditIntegerFieldComponent, EditIntegerFieldAssertions> {

	public EditIntegerFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditIntegerFieldComponent fill(int value) {
		getValueField().click().clear().fill(String.valueOf(value));
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=number]");
	}

	@Override
	public EditIntegerFieldAssertions assertThat() {
		return new EditIntegerFieldAssertions(this);
	}

}
