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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class WorkingSetSelectorAssertions extends
		AbstractCodebeamerComponentAssert<WorkingSetSelectorComponent, WorkingSetSelectorAssertions> {

	protected WorkingSetSelectorAssertions(WorkingSetSelectorComponent component) {
		super(component);
	}

	public WorkingSetSelectorAssertions isSelectorOpened() {
		return assertAll("Selector should be open", () -> assertThat(getComponent().getOpenedSelector()).isVisible());
	}

	public WorkingSetSelectorAssertions workingSetContainsBaseline(String baselineName) {
		return assertAll("Working set should contain baseline: %s".formatted(baselineName),
				() -> assertThat(getComponent().getWorkingSetOption(baselineName)).isVisible()
		);
	}
}
