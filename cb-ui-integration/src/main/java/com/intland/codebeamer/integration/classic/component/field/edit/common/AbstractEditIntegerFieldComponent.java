package com.intland.codebeamer.integration.classic.component.field.edit.common;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;

public abstract class AbstractEditIntegerFieldComponent<C extends AbstractEditIntegerFieldComponent<C, A>, A extends AbstractEditIntegerFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	public AbstractEditIntegerFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public abstract CodebeamerLocator getValueField();

}
