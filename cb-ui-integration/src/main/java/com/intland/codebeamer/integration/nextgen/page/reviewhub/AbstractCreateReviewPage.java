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
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

public abstract class AbstractCreateReviewPage<P extends AbstractCreateReviewPage> extends AbstractCodebeamerPage<P> {

	private static final String CREATE_REVIEW_PAGE_PATH = CBXPathUtil.buildPath("reviewList");

	public AbstractCreateReviewPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Visit")
	public P visit() {
		navigate(CREATE_REVIEW_PAGE_PATH + getPath());
		return isActive();
	}

	@Override
	public P isActive() {
		assertUrl(CREATE_REVIEW_PAGE_PATH + getPath(), "Create review page should be active page");
		return (P) this;
	}

	protected abstract String getPath();
}
