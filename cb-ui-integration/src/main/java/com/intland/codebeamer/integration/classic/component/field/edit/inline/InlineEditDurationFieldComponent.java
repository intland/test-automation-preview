package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDurationFieldComponent;

public class InlineEditDurationFieldComponent extends
		AbstractEditDurationFieldComponent<InlineEditDurationFieldComponent, InlineEditDurationFieldAssertions> {

	public InlineEditDurationFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditDurationFieldComponent fill(String value) {
		validateInput(value);
		getValueField().clear().fill(value);
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=text]");
	}

	@Override
	public InlineEditDurationFieldAssertions assertThat() {
		return new InlineEditDurationFieldAssertions(this);
	}

}
