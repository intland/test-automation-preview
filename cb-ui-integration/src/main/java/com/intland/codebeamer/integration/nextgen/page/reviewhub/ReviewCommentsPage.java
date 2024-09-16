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
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewCommentsAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewCommentsComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("ReviewCommentsPage")
public class ReviewCommentsPage extends AbstractCodebeamerPage<ReviewCommentsPage> {

	private static final String REVIEW_COMMENTS_PAGE_PATH = CBXPathUtil.buildPath("review/%s/comments");

	private Review review;

	@Component("Comments tab")
	private ReviewCommentsComponent reviewCommentsComponent;

	public ReviewCommentsPage(CodebeamerPage codebeamerPage, Review review) {
		super(codebeamerPage);
		this.reviewCommentsComponent = new ReviewCommentsComponent(codebeamerPage);
		this.review = review;
	}

	@Action("Visit")
	public ReviewCommentsPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public ReviewCommentsPage isActive() {
		assertUrl(formatPageUrl(), "Review comments should be an active page");
		return this;
	}

	public ReviewCommentsComponent getReviewCommentsComponent() {
		return reviewCommentsComponent;
	}

	public ReviewCommentsPage assertReviewCommentsComponent(Consumer<ReviewCommentsAssertions> assertion) {
		assertion.accept(getReviewCommentsComponent().assertThat());
		return this;
	}

	private String formatPageUrl() {
		return REVIEW_COMMENTS_PAGE_PATH.formatted(review.id().id());
	}
}