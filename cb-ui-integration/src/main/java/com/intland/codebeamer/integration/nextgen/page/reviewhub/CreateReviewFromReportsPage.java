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

package com.intland.codebeamer.integration.nextgen.page.reviewhub;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("CreateReviewFromReportsPage")
public class CreateReviewFromReportsPage extends AbstractCreateReviewPage<CreateReviewFromReportsPage> {

	private Report report;

	public CreateReviewFromReportsPage(CodebeamerPage codebeamerPage, Report report) {
		super(codebeamerPage);
		this.report = report;
	}

	@Override
	protected String getPath() {
		return "/create?reportId=%s".formatted(report.id());
	}
}
