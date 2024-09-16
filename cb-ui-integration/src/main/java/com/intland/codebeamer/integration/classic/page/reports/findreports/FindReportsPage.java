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

package com.intland.codebeamer.integration.classic.page.reports.findreports;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.reports.AbstractReportPage;
import com.intland.codebeamer.integration.classic.page.reports.findreports.component.ReportFinderDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("Report finder page")
public class FindReportsPage extends AbstractReportPage<FindReportsPage> {

	@Component("Report and Traceability Report Finder Dialog")
	private ReportFinderDialog reportFinderDialog;

	public FindReportsPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.reportFinderDialog = new ReportFinderDialog(codebeamerPage);
	}

	public ReportFinderDialog getReportFinderDialogComponent() {
		return reportFinderDialog;
	}

	@Override
	protected String getPath() {
		return "?findQueries=true";
	}
}
