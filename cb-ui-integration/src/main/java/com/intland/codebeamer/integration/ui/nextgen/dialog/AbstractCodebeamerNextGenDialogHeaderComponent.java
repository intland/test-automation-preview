package com.intland.codebeamer.integration.ui.nextgen.dialog;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class AbstractCodebeamerNextGenDialogHeaderComponent extends
		AbstractCodebeamerComponent<AbstractCodebeamerNextGenDialogHeaderComponent, AbstractCodebeamerNextGenDialogHeaderAssertion> {

	public AbstractCodebeamerNextGenDialogHeaderComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator + "div.p-dialog-header");
	}

	public CodebeamerLocator getTitle() {
		return this.locator("span.p-dialog-title");
	}

	public CodebeamerLocator getCloseButton() {
		return this.locator("button.p-dialog-header-close");
	}

	@Override
	public AbstractCodebeamerNextGenDialogHeaderAssertion assertThat() {
		return new AbstractCodebeamerNextGenDialogHeaderAssertion(this);
	}

}
