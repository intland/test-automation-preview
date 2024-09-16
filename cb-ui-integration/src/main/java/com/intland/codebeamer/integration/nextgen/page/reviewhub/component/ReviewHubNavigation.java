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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewItemPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewTemplatePage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ReviewHubNavigation {

	private CodebeamerPage codebeamerPage;

	public ReviewHubNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("Redirected to ReviewItem page")
	public ReviewItemPage redirectedToReviewItemPage(Review review) {
		return new ReviewItemPage(codebeamerPage, review).isActive();
	}

	@Action("Redirected to review template page")
	public ReviewTemplatePage redirectedToReviewTemplatePage() {
		return new ReviewTemplatePage(codebeamerPage).isActive();
	}
}
