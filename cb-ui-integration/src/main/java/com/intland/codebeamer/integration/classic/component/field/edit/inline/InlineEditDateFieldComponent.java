package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditDateFieldComponent;

public class InlineEditDateFieldComponent extends
		AbstractEditDateFieldComponent<InlineEditDateFieldComponent, InlineEditDateFieldAssertions> {

	public InlineEditDateFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditDateFieldComponent fill(int year, int month, int day, int hour, int minute) {
		getValueField().clear().fill(String.valueOf(getFormattedDate(year, month, day, hour, minute)));
		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" input.hasDatepicker");
	}

	@Override
	public InlineEditDateFieldAssertions assertThat() {
		return new InlineEditDateFieldAssertions(this);
	}

}
