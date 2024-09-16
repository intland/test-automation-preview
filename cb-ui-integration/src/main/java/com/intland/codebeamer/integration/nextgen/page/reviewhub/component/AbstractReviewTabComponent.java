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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractReviewTabComponent<C extends AbstractReviewTabComponent<C, A>, A extends AbstractReviewTabAssertions<C, A>> extends AbstractCodebeamerComponent<C, A> {

	protected AbstractReviewTabComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	public C activateTab() {
		getTab().click();
		return (C) this;
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	protected abstract String getTabId();
}