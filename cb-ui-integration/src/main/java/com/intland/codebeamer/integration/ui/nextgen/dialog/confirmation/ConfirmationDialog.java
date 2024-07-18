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

package com.intland.codebeamer.integration.ui.nextgen.dialog.confirmation;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class ConfirmationDialog<P extends ConfirmationDialog> extends AbstractCodebeamerDialog<P> {

	public ConfirmationDialogHeaderComponent header;
	
	public ConfirmationDialogFooterComponent footer;
		
	public ConfirmationDialog(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, "div.p-confirm-dialog.p-component");
	}
	
	public ConfirmationDialog(CodebeamerPage codebeamerPage, String dialogLocator) {
		super(codebeamerPage, dialogLocator);
		this.header = new ConfirmationDialogHeaderComponent(codebeamerPage, dialogLocator);
		this.footer = new ConfirmationDialogFooterComponent(codebeamerPage, dialogLocator);
	}

	public CodebeamerLocator getTitle() {
		return header.getTitle();
	}
	
	public CodebeamerLocator getCloseButton() {
		return header.getCloseButton();
	}
	
	public CodebeamerLocator getYesButton() {
		return footer.getYesButton();
	}

	public CodebeamerLocator getNoButton() {
		return footer.getNoButton();
	}
	
}
