package com.intland.codebeamer.integration.ui.nextgen.dialog.confirmation;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ConfirmationDialogFooterComponent
		extends AbstractCodebeamerComponent<ConfirmationDialogFooterComponent, ConfirmationDialogFooterAssertion> {

	public ConfirmationDialogFooterComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div.p-dialog-footer p-footer");
	}
	
	public CodebeamerLocator getYesButton() {
		return this.locator("button[data-cy='confirm-dialog-confirm-button']");
	}

	public CodebeamerLocator getNoButton() {
		return this.locator("button[data-cy='confirm-dialog-deny-button']");
	}
	
	@Override
	public ConfirmationDialogFooterAssertion assertThat() {
		return new ConfirmationDialogFooterAssertion(this);
	}

}
