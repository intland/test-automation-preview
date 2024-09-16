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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.reports.ReportActionBarNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReportActionBarComponent extends AbstractCodebeamerComponent<ReportActionBarComponent, ReportActionBarAssertions> {

	public ReportActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#reportActionBar");
	}

	public CodebeamerLocator getEditReportIcon() {
		return this.locator("[class='actionLink edit-report actionBarIcon']");
	}

	@Override
	public ReportActionBarAssertions assertThat() {
		return new ReportActionBarAssertions(this);
	}

	public ReportActionBarNavigation editReportPage() {
		getEditReportIcon().click();
		return new ReportActionBarNavigation(getCodebeamerPage());
	}
}