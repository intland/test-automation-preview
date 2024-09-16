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

package com.intland.codebeamer.integration.classic.page.reports;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportActionBarAssertions;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportActionBarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ViewReportPage")
public class ViewReportPage extends AbstractCodebeamerPage<ViewReportPage> {

	private static final String VIEW_REPORT_PAGE_PATH = "query/%s";

	private final Report report;

	@Component("Report action bar")
	private final ReportActionBarComponent reportActionBarComponent;

	public ViewReportPage(CodebeamerPage codebeamerPage, Report report) {
		super(codebeamerPage);
		this.report = report;
		this.reportActionBarComponent = new ReportActionBarComponent(codebeamerPage);
	}

	@Action("Visit")
	public ViewReportPage visit() {
		navigate(formatViewReportPageUrl());
		return isActive();
	}

	@Override
	public ViewReportPage isActive() {
		assertUrl(formatViewReportPageUrl(), "View report page should be active page");
		return this;
	}

	public ReportActionBarComponent getReportActionBarComponent() {
		return reportActionBarComponent;
	}

	public ViewReportPage assertReportActionBarComponent(Consumer<ReportActionBarAssertions> assertion) {
		assertion.accept(getReportActionBarComponent().assertThat());
		return this;
	}

	private String formatViewReportPageUrl() {
		return VIEW_REPORT_PAGE_PATH.formatted(report.id().id());
	}
}