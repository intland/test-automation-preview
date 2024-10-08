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

package com.intland.codebeamer.integration.classic.page.tracker.config.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractTrackerConfigTab<C extends AbstractTrackerConfigTab<C, A>, A extends AbstractTrackerConfigAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	public AbstractTrackerConfigTab(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	public C save() {
		getSaveButton().click();
		if (waitForLoadingDialog()) {
			getLoadingDialogElement().waitForDetached();
		}
		return (C) this;
	}

	public C cancel() {
		getCancelButton().click(); // TODO navigation or just assert?
		return (C) this;
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator(getMainFormId() + " .actionBar input[name='SAVE']");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator(getMainFormId() + " .actionBar input[name='_cancel']");
	}

	public CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}

	/**
	 * Whether to wait for the 'Loading' dialog to show up and disappear.
	 * Not every tab displays a loading icon.
	 */
	protected boolean waitForLoadingDialog() {
		return true;
	}

	protected abstract String getTabId();

	protected abstract String getMainFormId();
}
