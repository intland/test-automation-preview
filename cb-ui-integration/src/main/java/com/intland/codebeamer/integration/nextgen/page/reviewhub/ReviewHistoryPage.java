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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewHistoryAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewHistoryComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("ReviewHistoryPage")
public class ReviewHistoryPage extends AbstractCodebeamerPage<ReviewHistoryPage> {

	private static final String REVIEW_HISTORY_PAGE_PATH = CBXPathUtil.buildPath("review/%s/history");

	private Review review;

	@Component("History tab")
	private ReviewHistoryComponent reviewHistoryComponent;

	public ReviewHistoryPage(CodebeamerPage codebeamerPage, Review review) {
		super(codebeamerPage);
		this.reviewHistoryComponent = new ReviewHistoryComponent(codebeamerPage);
		this.review = review;
	}

	@Action("Visit")
	public ReviewHistoryPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public ReviewHistoryPage isActive() {
		assertUrl(formatPageUrl(), "Review history should be an active page");
		return this;
	}

	public ReviewHistoryComponent getReviewHistoryComponent() {
		return reviewHistoryComponent;
	}

	public ReviewHistoryPage assertReviewHistoryComponent(Consumer<ReviewHistoryAssertions> assertion) {
		assertion.accept(getReviewHistoryComponent().assertThat());
		return this;
	}

	private String formatPageUrl() {
		return REVIEW_HISTORY_PAGE_PATH.formatted(review.id().id());
	}
}