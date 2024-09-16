package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDecimalFieldComponent;

public class InlineEditDecimalFieldComponent extends
		AbstractEditDecimalFieldComponent<InlineEditDecimalFieldComponent, InlineEditDecimalFieldAssertions> {

	public InlineEditDecimalFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditDecimalFieldComponent fill(double value) {
		getValueField().clear().fill(String.valueOf(value));
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input[type=number]");
	}

	@Override
	public InlineEditDecimalFieldAssertions assertThat() {
		return new InlineEditDecimalFieldAssertions(this);
	}

}
