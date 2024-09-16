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

public class ReportActionBarNavigation {

	protected final CodebeamerPage codebeamerPage;

	public ReportActionBarNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToEditReportPage")
	public EditReportPage redirectedToEditReportPage(Report report) {
		return new EditReportPage(codebeamerPage, report).isActive();
	}
}
