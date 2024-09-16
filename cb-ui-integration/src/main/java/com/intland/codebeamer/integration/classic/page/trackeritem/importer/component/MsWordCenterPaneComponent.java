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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class MsWordCenterPaneComponent
		extends AbstractCodebeamerComponent<MsWordCenterPaneComponent, MsWordCenterPaneAssertions> {

	public MsWordCenterPaneComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#rightPane");
	}

	@Override
	public MsWordCenterPaneAssertions assertThat() {
		return new MsWordCenterPaneAssertions(this);
	}

	public MsWordCenterPaneComponent toggleImportSwitch(int itemIndex) {
		getImportSwitchLocatorByIndex(itemIndex).click();
		return this;
	}

	private CodebeamerLocator getImportSwitchLocatorByIndex(int itemIndex) {
		return getItemLocatorByIndex(itemIndex).concat("span.switch");
	}

	public CodebeamerLocator getItemLocatorByIndex(int itemIndex) {
		return this.locator("table#requirements tr[data-id='%d']".formatted(Integer.valueOf(++itemIndex)));
	}
}
