package com.intland.codebeamer.integration.ui.nextgen.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ToastComponent extends AbstractCodebeamerComponent<ToastComponent, ToastAssertions>  {

	public ToastComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "[class*='toast-message']");
	}

	public CodebeamerLocator getToastSuccess() {
		return this.locator("[class*='p-toast-message-success']");
	}
	
	public CodebeamerLocator getToastError() {
		return this.locator("[class*='toast-message-error']");
	}

	@Override
	public ToastAssertions assertThat() {
		return new ToastAssertions(this);
	}

}