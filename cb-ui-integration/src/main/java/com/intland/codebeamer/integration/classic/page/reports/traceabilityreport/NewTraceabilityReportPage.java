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

package com.intland.codebeamer.integration.classic.page.reports.traceabilityreport;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("NewTraceabilityReportPage")
public class NewTraceabilityReportPage extends AbstractCodebeamerPage<NewTraceabilityReportPage> {

	private static final String NEWTRACEABILITYREPORT = "traceabilityReport/new";

	public NewTraceabilityReportPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Visit")
	public NewTraceabilityReportPage visit() {
		navigate(NEWTRACEABILITYREPORT);
		return isActive();
	}

	@Override
	public NewTraceabilityReportPage isActive() {
		assertUrl(NEWTRACEABILITYREPORT, "New traceability report page should be the active page");
		return this;
	}
}
