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

package com.intland.codebeamer.integration.classic.component.loadingIndicator;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class LoadingIndicatorComponent
		extends AbstractCodebeamerComponent<LoadingIndicatorComponent, LoadingIndicatorAssertions> {

	public LoadingIndicatorComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".showBusySignDialog");
	}

	public LoadingIndicatorComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, ".showBusySignDialog");
	}

	@Override
	public LoadingIndicatorAssertions assertThat() {
		return new LoadingIndicatorAssertions(this);
	}

	public void waitForDetached() {
		this.locator("").waitForDetached();
	}

	public void waitForAttached() {
		this.locator("").waitForAttached();
	}
}
