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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("EditReportPage")
public class EditReportPage extends AbstractCodebeamerPage<EditReportPage> {

	private static final String EDIT_REPORT_PAGE_PATH = "query/%s/edit";

	private final Report report;

	public EditReportPage(CodebeamerPage codebeamerPage, Report report) {
		super(codebeamerPage);
		this.report = report;
	}

	@Action("Visit")
	public EditReportPage visit() {
		navigate(formatViewReportPageUrl());
		return isActive();
	}

	@Override
	public EditReportPage isActive() {
		assertUrl(formatViewReportPageUrl(), "Edit report page should be active page");
		return this;
	}

	private String formatViewReportPageUrl() {
		return EDIT_REPORT_PAGE_PATH.formatted(report.id().id());
	}
}
