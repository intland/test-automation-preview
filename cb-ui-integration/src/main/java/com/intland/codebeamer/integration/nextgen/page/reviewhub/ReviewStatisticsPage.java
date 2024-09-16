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
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewStatisticsAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewStatisticsComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("ReviewStatisticsPage")
public class ReviewStatisticsPage extends AbstractCodebeamerPage<ReviewStatisticsPage> {

	private static final String REVIEW_STATISTICS_PAGE_PATH = CBXPathUtil.buildPath("review/%s/statistics");

	private Review review;

	@Component("Statistics tab")
	private ReviewStatisticsComponent reviewStatisticsComponent;

	public ReviewStatisticsPage(CodebeamerPage codebeamerPage, Review review) {
		super(codebeamerPage);
		this.reviewStatisticsComponent = new ReviewStatisticsComponent(codebeamerPage);
		this.review = review;
	}

	@Action("Visit")
	public ReviewStatisticsPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public ReviewStatisticsPage isActive() {
		assertUrl(formatPageUrl(), "Review statistics should be an active page");
		return this;
	}

	public ReviewStatisticsComponent getReviewStatisticsComponent() {
		return reviewStatisticsComponent;
	}

	public ReviewStatisticsPage assertReviewStatisticsComponent(Consumer<ReviewStatisticsAssertions> assertion) {
		assertion.accept(getReviewStatisticsComponent().assertThat());
		return this;
	}

	private String formatPageUrl() {
		return REVIEW_STATISTICS_PAGE_PATH.formatted(review.id().id());
	}
}