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
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewAdminFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ReviewAdminPage")
public class ReviewAdminPage extends AbstractCodebeamerPage<ReviewAdminPage> {

	private static final String REVIEW_ADMIN_PAGE_PATH = "x/#/reviewAdmin";

	@Component("Review Admin Form")
	private ReviewAdminFormComponent reviewAdminFormComponent;

	public ReviewAdminPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.reviewAdminFormComponent = new ReviewAdminFormComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ReviewAdminPage visit() {
		navigate(REVIEW_ADMIN_PAGE_PATH);
		return isActive();
	}

	@Override
	public ReviewAdminPage isActive() {
		assertUrl(REVIEW_ADMIN_PAGE_PATH, "Review admin page should be active page");
		return this;
	}

	@Override
	public ReviewAdminPage assertPage(Consumer<ReviewAdminPage> assertion) {
		assertion.accept(this);
		return this;
	}

	public ReviewAdminPage getReviewAdminFormComponent(Consumer<ReviewAdminFormComponent> formConsumer) {
		formConsumer.accept(reviewAdminFormComponent);
		return this;
	}
}
