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

package com.intland.codebeamer.integration.classic.component.reportselector;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReportSelectorAssertions extends AbstractCodebeamerComponentAssert<ReportSelectorComponent, ReportSelectorAssertions>  {

	public ReportSelectorAssertions(ReportSelectorComponent component) {
		super(component);
	}

	public ReportSelectorAssertions openInTableViewDoesNotVisible() {
		return assertAll("Open in Table view link should not be visible",
				() -> assertThat(getComponent().getOpenInTableViewLink()).not().isVisible());
	}
}
