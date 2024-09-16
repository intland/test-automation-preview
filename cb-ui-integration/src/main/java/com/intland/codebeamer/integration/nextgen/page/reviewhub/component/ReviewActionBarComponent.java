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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.editreviewdialog.EditReviewDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewActionBarComponent extends AbstractCodebeamerComponent<ReviewActionBarComponent, ReviewActionBarAssertion> {

	private EditReviewDialog editReviewDialog;

	public ReviewActionBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".review-main-header");
		this.editReviewDialog = new EditReviewDialog(getCodebeamerPage());
	}

	public ReviewActionBarComponent reviewMoreMenu() {
		getMoreMenuButton().click();
		return this;
	}

	public EditReviewDialog editReview() {
		getEditReviewButton().click();
		return this.editReviewDialog;
	}

	public CodebeamerLocator getEditReviewButton() {
		return getCodebeamerPage().locator("#edit-review-button");
	}

	public CodebeamerLocator getCancelReviewButton() {
		return getCodebeamerPage().locator("#cancel-review-button");
	}

	public CodebeamerLocator getMoreMenuButton() {
		return this.locator("button[data-cy='review-more-menu-button']");
	}

	public CodebeamerLocator getCompleteReviewButton() {
		return this.locator("button[data-cy='completeReviewButton']");
	}

	public CodebeamerLocator getReviewNameElement() {
		return this.locator("div[data-cy='reviewName']");
	}

	@Override
	public ReviewActionBarAssertion assertThat() {
		return new ReviewActionBarAssertion(this);
	}
}
