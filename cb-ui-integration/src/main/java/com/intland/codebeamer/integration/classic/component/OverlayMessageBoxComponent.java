package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class OverlayMessageBoxComponent
		extends AbstractCodebeamerComponent<OverlayMessageBoxComponent, OverlayMessageBoxAssertion> {

	public OverlayMessageBoxComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".overlayMessageBoxContainer");
	}

	public CodebeamerLocator getErrorMessageElement() {
		return this.locator(".overlayMessageBox.error");		
	}
	
	public CodebeamerLocator getSuccessMessageElement() {
		return this.locator(".overlayMessageBox.notification");		
	}

	@Override
	public OverlayMessageBoxAssertion assertThat() {
		return new OverlayMessageBoxAssertion(this);
	}

}
