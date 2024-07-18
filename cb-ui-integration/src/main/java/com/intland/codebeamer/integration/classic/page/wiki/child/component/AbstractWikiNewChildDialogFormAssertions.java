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

package com.intland.codebeamer.integration.classic.page.wiki.child.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractWikiNewChildDialogFormAssertions
		extends
		AbstractCodebeamerComponentAssert<AbstractWikiNewChildDialogFormComponent, AbstractWikiNewChildDialogFormAssertions> {

	public AbstractWikiNewChildDialogFormAssertions(AbstractWikiNewChildDialogFormComponent component) {
		super(component);
	}

	public AbstractWikiNewChildDialogFormAssertions isPageNameEmpty() {
		return assertAll("Page name should be empty", () -> assertThat(getComponent().getPageNameField()).isEmpty());
	}
	
}
