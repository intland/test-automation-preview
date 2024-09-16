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

package com.intland.codebeamer.integration.classic.page.reports.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReportActionBarAssertions extends AbstractCodebeamerComponentAssert<ReportActionBarComponent, ReportActionBarAssertions> {

	public ReportActionBarAssertions(ReportActionBarComponent component) {
		super(component);
	}

	public ReportActionBarAssertions isEditReportIconReady() {
		return assertAll("Edit Report Icon Visible", () -> {
			assertThat(getComponent().getEditReportIcon()).isVisible();
			assertThat(getComponent().getEditReportIcon()).isEnabled();
		});
	}
}