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
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewActionBarComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewCommentsComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewHistoryComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemMainAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemMainComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemReferencesAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemReferencesComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemTreeAssertion;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemTreeComponent;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewStatisticsComponent;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.ReviewsDropdownAssertions;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.ReviewsDropdownComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("ReviewItemPage")
public class ReviewItemPage extends AbstractCodebeamerPage<ReviewItemPage> {

	private static final String REVIEW_ITEM_PAGE_PATH = CBXPathUtil.buildPath("review/%s");

	@Component("Review item main component")
	private ReviewItemMainComponent reviewItemMainComponent;

	@Component("Review item tree component")
	private ReviewItemTreeComponent reviewItemTreeComponent;

	@Component("Reviews dropdown")
	private ReviewsDropdownComponent reviewsDropdownComponent;

	@Component("Comments tab")
	private ReviewCommentsComponent reviewCommentsComponent;

	@Component("Review item references accordion component")
	private ReviewItemReferencesComponent reviewItemReferencesComponent;

	@Component("Statistics tab")
	private ReviewStatisticsComponent reviewStatisticsComponent;

	@Component("History tab")
	private ReviewHistoryComponent reviewHistoryComponent;

	@Component("Action bar")
	private ReviewActionBarComponent reviewActionBarComponent;

	private Review review;

	public ReviewItemPage(CodebeamerPage codebeamerPage, Review review) {
		super(codebeamerPage);
		this.review = review;
		this.reviewItemMainComponent = new ReviewItemMainComponent(getCodebeamerPage());
		this.reviewItemTreeComponent = new ReviewItemTreeComponent(getCodebeamerPage());
		this.reviewsDropdownComponent = new ReviewsDropdownComponent(getCodebeamerPage());
		this.reviewCommentsComponent = new ReviewCommentsComponent(getCodebeamerPage());
		this.reviewStatisticsComponent = new ReviewStatisticsComponent(getCodebeamerPage());
		this.reviewHistoryComponent = new ReviewHistoryComponent(getCodebeamerPage());
		this.reviewActionBarComponent = new ReviewActionBarComponent(getCodebeamerPage());
		this.reviewItemReferencesComponent = new ReviewItemReferencesComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ReviewItemPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public ReviewItemPage isActive() {
		assertUrl(formatPageUrl(), "Review item page should be active page");
		return this;
	}

	public ReviewActionBarComponent getReviewActionBarComponent() {
		return reviewActionBarComponent;
	}

	@Override
	public ReviewItemPage assertPage(Consumer<ReviewItemPage> assertion) {
		assertion.accept(this);
		return this;
	}

	public ReviewsDropdownComponent getReviewsDropdownComponent() {
		return reviewsDropdownComponent;
	}

	public ReviewItemMainComponent getReviewItemMainComponent() {
		return reviewItemMainComponent;
	}

	public ReviewItemTreeComponent getReviewItemTreeComponent() {
		return reviewItemTreeComponent;
	}

	public ReviewItemReferencesComponent getReviewItemReferencesComponent() {
		return reviewItemReferencesComponent;
	}

	public ReviewItemPage assertReviewsDropdown(Consumer<ReviewsDropdownAssertions> assertion) {
		assertion.accept(getReviewsDropdownComponent().assertThat());
		return this;
	}

	public ReviewItemPage assertReviewItemMain(Consumer<ReviewItemMainAssertions> assertion) {
		assertion.accept(getReviewItemMainComponent().assertThat());
		return this;
	}

	public ReviewItemPage assertReviewItemTree(Consumer<ReviewItemTreeAssertion> assertion) {
		assertion.accept(getReviewItemTreeComponent().assertThat());
		return this;
	}

	public ReviewItemPage reviewCommentsTab(Consumer<ReviewCommentsComponent> formConsumer) {
		formConsumer.accept(reviewCommentsComponent);
		return this;
	}

	public ReviewItemPage reviewStatisticsTab(Consumer<ReviewStatisticsComponent> formConsumer) {
		formConsumer.accept(reviewStatisticsComponent);
		return this;
	}

	public ReviewItemPage reviewHistoryTab(Consumer<ReviewHistoryComponent> formConsumer) {
		formConsumer.accept(reviewHistoryComponent);
		return this;
	}

	public ReviewItemPage assertReviewReferencesComponent(Consumer<ReviewItemReferencesAssertions> assertion) {
		assertion.accept(getReviewItemReferencesComponent().assertThat());
		return this;
	}

	public ReviewItemPage changeToStatisticsTab() {
		reviewStatisticsComponent.activateTab();
		return this;
	}

	public ReviewItemPage changeToCommentsTab() {
		reviewCommentsComponent.activateTab();
		return this;
	}

	public ReviewItemPage changeToHistoryTab() {
		reviewHistoryComponent.activateTab();
		return this;
	}

	private String formatPageUrl() {
		return REVIEW_ITEM_PAGE_PATH.formatted(review.id().id());
	}
}
