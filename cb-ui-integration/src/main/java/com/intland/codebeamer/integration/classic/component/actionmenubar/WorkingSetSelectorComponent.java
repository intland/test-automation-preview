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

package com.intland.codebeamer.integration.classic.component.actionmenubar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class WorkingSetSelectorComponent extends AbstractCodebeamerComponent<WorkingSetSelectorComponent, WorkingSetSelectorAssertions> {

	public WorkingSetSelectorComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "td > span.working-set-list");
	}

	@Override
	public WorkingSetSelectorAssertions assertThat() {
		return new WorkingSetSelectorAssertions(this);
	}

	public CodebeamerLocator getBaselineNameElement() {
		return this.locator("span.branchBaselineNamePart");
	}

	public void switchToHeadRevision() {
		openSelector();
		getSwitchToHeadRevisionButton().click();
	}

	public CodebeamerLocator getOpenedSelector() {
		return getCodebeamerPage().locator("div.working-set-multiselect");
	}

	public CodebeamerLocator getWorkingSetOption(String workingSetName) {
		return locator("button#workingSetSelector_ms span:has-text('%s')".formatted(workingSetName));
	}

	private CodebeamerLocator getOpenedSelectorHeader() {
		return getOpenedSelector().concat("div.branchBaselineBadgeContainer");
	}

	private CodebeamerLocator getSwitchToHeadRevisionButton() {
		return getOpenedSelectorHeader().concat("span[data-testid='switchToHead']");
	}

	private void openSelector() {
		try {
			assertThat().isSelectorOpened();
		} catch (AssertionError e) {
			this.locator("button#workingSetSelector_ms").click();
		}
	}
}
