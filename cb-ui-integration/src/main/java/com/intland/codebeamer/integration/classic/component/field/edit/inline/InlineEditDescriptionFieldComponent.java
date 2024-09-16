package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditWikiTextFieldComponent;

public class InlineEditDescriptionFieldComponent
		extends AbstractEditWikiTextFieldComponent<InlineEditDescriptionFieldComponent, InlineEditDescriptionFieldAssertions> {

	public InlineEditDescriptionFieldComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "fieldset#descriptionPart");
		this.froala = new FroalaComponent(codebeamerPage, frameLocator, getSelector());
	}

	@Override
	public InlineEditDescriptionFieldAssertions assertThat() {
		return new InlineEditDescriptionFieldAssertions(this);
	}

}
