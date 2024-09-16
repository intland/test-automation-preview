package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;

public abstract class AbstractEditChoiceFieldComponent<C extends AbstractEditChoiceFieldComponent<C, A>, A extends AbstractEditChoiceFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	protected AbstractEditChoiceFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public abstract CodebeamerLocator getValueSelector();

	public CodebeamerLocator getOptionsSelector() {
		return getValueSelector().concat("option");
	}

	@Override
	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}


}
