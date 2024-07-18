/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.ui.nextgen.dialog;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public abstract class AbstractCodebeamerNextGenDialog<P extends AbstractCodebeamerNextGenDialog> extends AbstractCodebeamerDialog<P> {

	public AbstractCodebeamerNextGenDialogHeaderComponent header;
	
	public AbstractCodebeamerNextGenDialogFooterComponent footer;
	
	public AbstractCodebeamerNextGenDialog(CodebeamerPage codebeamerPage, String dialogLocator, String saveButtonLocator, String cancelButtonLocator) {
		super(codebeamerPage, dialogLocator);
		this.header = new AbstractCodebeamerNextGenDialogHeaderComponent(codebeamerPage, dialogLocator);
		this.footer = new AbstractCodebeamerNextGenDialogFooterComponent(codebeamerPage, dialogLocator, saveButtonLocator, cancelButtonLocator);
	}

	public CodebeamerLocator getTitle() {
		return header.getTitle();
	}
	
	public CodebeamerLocator getCloseButton() {
		return header.getCloseButton();
	}
	
	public CodebeamerLocator getSaveButton() {
		return footer.getSaveButton();
	}

	public CodebeamerLocator getCancelButton() {
		return footer.getCancelButton();
	}
	
}
