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

package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.InterwikiLinkNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class InterwikiLinkComponent
		extends AbstractCodebeamerComponent<InterwikiLinkComponent, InterwikiLinkAssertions> {

	public InterwikiLinkComponent(CodebeamerPage codebeamerPage, String parentLocator, String linkText) {
		super(codebeamerPage, parentLocator + " span.interwikilink:has-text('%s')".formatted(linkText));
	}

	@Override
	public InterwikiLinkAssertions assertThat() {
		return new InterwikiLinkAssertions(this);
	}

	public InterwikiLinkNavigation click() {
		getLink().click();
		return new InterwikiLinkNavigation(getCodebeamerPage());
	}

	protected CodebeamerLocator getLink() {
		return this.locator("a.interwikilink");
	}
}
