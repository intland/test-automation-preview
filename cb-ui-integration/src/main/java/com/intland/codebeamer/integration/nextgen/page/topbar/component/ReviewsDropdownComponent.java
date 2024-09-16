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

package com.intland.codebeamer.integration.nextgen.page.topbar.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewsDropdownComponent extends AbstractCodebeamerComponent<ReviewsDropdownComponent, ReviewsDropdownAssertions> {

	public ReviewsDropdownComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.layout-topbar");
	}

	public ReviewsDropdownNavigation reviews() {
		getReviewsButton().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public ReviewsDropdownComponent reviewsMenu() {
		getReviewsButton().click();
		return this;
	}

	public ReviewsDropdownNavigation createReview() {
		getCreateReviewOption().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public ReviewsDropdownNavigation showAllReviews() {
		getAllReviewsOption().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public ReviewsDropdownNavigation reviewTemplates() {
		getReviewsTemplatesOption().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public ReviewsDropdownNavigation reviewAdministration() {
		getReviewsButton().click();
		getReviewAdministrationOption().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public ReviewsDropdownNavigation mergeRequests() {
		getMergeRequestsOption().click();
		return new ReviewsDropdownNavigation(getCodebeamerPage());
	}

	public CodebeamerLocator getReviewsButton() {
		return this.locator("[data-cy='reviews-menu']");
	}

	public CodebeamerLocator getCreateReviewOption() {
		return this.locator("#create-review-sub-menu");
	}

	public CodebeamerLocator getAllReviewsOption() {
		return this.locator("#view-all-reviews-sub-menu");
	}

	public CodebeamerLocator getReviewsTemplatesOption() {
		return this.locator("#view-review-template-sub-menu");
	}

	public CodebeamerLocator getReviewAdministrationOption() {
		return this.locator("#review-admin-sub-menu");
	}

	public CodebeamerLocator getMergeRequestsOption() {
		return this.locator("#view-review-merge-request-sub-menu");
	}

	public ReviewsDropdownComponent assertReviewsDropdown(Consumer<ReviewsDropdownAssertions> assertion) {
		assertion.accept(assertThat());
		return this;
	}

	@Override
	public ReviewsDropdownAssertions assertThat() {
		return new ReviewsDropdownAssertions(this);
	}
}
