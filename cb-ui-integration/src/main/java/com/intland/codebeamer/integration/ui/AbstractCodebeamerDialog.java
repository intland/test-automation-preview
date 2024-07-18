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

package com.intland.codebeamer.integration.ui;

import java.time.Duration;

import com.intland.codebeamer.integration.CodebeamerPage;

public abstract class AbstractCodebeamerDialog<P extends AbstractCodebeamerDialog> {

	private final CodebeamerPage codebeamerPage;

	private final String dialogLocator;

	public AbstractCodebeamerDialog(CodebeamerPage codebeamerPage, String dialogLocator) {
		this.codebeamerPage = codebeamerPage;
		this.dialogLocator = dialogLocator;
	}

	public P sleep(long seconds) {
		try {
			Thread.sleep(Duration.ofSeconds(seconds));
			return getMySelf();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	protected CodebeamerPage getCodebeamerPage() {
		return codebeamerPage;
	}

	protected String getDialogLocator() {
		return dialogLocator;
	}

	private P getMySelf() {
		return (P) this;
	}

}
