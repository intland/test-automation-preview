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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.editreviewdialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class EditReviewDialogNavigation {

	private CodebeamerPage codebeamerPage;

	private Review review;

	public EditReviewDialogNavigation(CodebeamerPage codebeamerPage, Review review) {
		this.codebeamerPage = codebeamerPage;
		this.review = review;
	}

	@Action("redirectToReviewItemPage")
	public ReviewItemPage redirectToReviewItemPage() {
		return new ReviewItemPage(codebeamerPage, review).isActive();
	}
}
