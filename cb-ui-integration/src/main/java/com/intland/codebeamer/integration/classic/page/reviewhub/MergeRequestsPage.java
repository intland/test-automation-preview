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

package com.intland.codebeamer.integration.classic.page.reviewhub;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("MergeRequestsPage")
public class MergeRequestsPage extends AbstractCodebeamerPage<MergeRequestsPage> {

	private static final String MERGE_REQUESTS_PAGE_URL = "proj/review/mergeRequests.spr";

	public MergeRequestsPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Visit")
	public MergeRequestsPage visit() {
		navigate(MERGE_REQUESTS_PAGE_URL);
		return isActive();
	}

	@Override
	public MergeRequestsPage isActive() {
		assertUrl(MERGE_REQUESTS_PAGE_URL, "Merge requests page should be an active page");
		return this;
	}
}