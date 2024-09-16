package com.intland.codebeamer.integration.ui.nextgen.dialog;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class AbstractCodebeamerNextGenDialogFooterComponent
		extends AbstractCodebeamerComponent<AbstractCodebeamerNextGenDialogFooterComponent, AbstractCodebeamerNextGenDialogFooterAssertion> {

	private String saveButtonLocator;
	
	private String cancelButtonLocator;

	public AbstractCodebeamerNextGenDialogFooterComponent(CodebeamerPage codebeamerPage, String frameLocator, String saveButtonLocator, String cancelButtonLocator) {
		super(codebeamerPage, frameLocator + "div.p-dialog-footer p-footer");
		this.saveButtonLocator = saveButtonLocator;
		this.cancelButtonLocator = cancelButtonLocator;
	}
	
	public CodebeamerLocator getSaveButton() {
		return this.locator("button[data-cy='%s']".formatted(this.saveButtonLocator));
	}
	
	public CodebeamerLocator getCancelButton() {
		return this.locator("button[data-cy='%s']".formatted(this.cancelButtonLocator));
	}
	
	@Override
	public AbstractCodebeamerNextGenDialogFooterAssertion assertThat() {
		return new AbstractCodebeamerNextGenDialogFooterAssertion(this);
	}

}
