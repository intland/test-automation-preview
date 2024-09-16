package com.intland.codebeamer.integration.ui.nextgen.dialog.confirmation;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ConfirmationDialogHeaderComponent extends
		AbstractCodebeamerComponent<ConfirmationDialogHeaderComponent, ConfirmationDialogHeaderAssertion> {

	public ConfirmationDialogHeaderComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator + "div.p-dialog-header");
	}

	public CodebeamerLocator getTitle() {
		return this.locator("span.p-dialog-title");
	}

	public CodebeamerLocator getCloseButton() {
		return this.locator("button.p-dialog-header-close");
	}

	@Override
	public ConfirmationDialogHeaderAssertion assertThat() {
		return new ConfirmationDialogHeaderAssertion(this);
	}

}
