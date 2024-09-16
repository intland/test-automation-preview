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

package com.intland.codebeamer.integration.classic.page.reviewhub;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewBreadcrumbAssertions;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewBreadcrumbComponent;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewListAssertions;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewListComponent;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.ReviewsDropdownComponent;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.TopbarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("AllReviewPage")
public class AllReviewPage extends AbstractCodebeamerPage<AllReviewPage> {

	private static final String ALL_REVIEW_PAGE_PATH =  CBXPathUtil.buildPath("reviewList");

	@Component("Topbar component")
	private TopbarComponent topbarComponent;

	@Component("All Review Breadcrumb")
	private AllReviewBreadcrumbComponent allReviewBreadcrumbComponent;

	@Component("All Review")
	private AllReviewListComponent allReviewListComponent;

	@Component("Reviews dropdown")
	private ReviewsDropdownComponent reviewsDropdownComponent;

	public AllReviewPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.allReviewListComponent = new AllReviewListComponent(codebeamerPage);
		this.allReviewBreadcrumbComponent = new AllReviewBreadcrumbComponent(codebeamerPage);
		this.reviewsDropdownComponent = new ReviewsDropdownComponent(getCodebeamerPage());
		this.topbarComponent = new TopbarComponent(getCodebeamerPage());
	}

	@Override
	public AllReviewPage isActive() {
		assertUrl(ALL_REVIEW_PAGE_PATH, "All Review page should be the active page");
		return this;
	}

	@Action("Visit")
	public AllReviewPage visit() {
		navigate(ALL_REVIEW_PAGE_PATH);
		return isActive();
	}

	@Action("Open Review")
	public AllReviewPage changeToOpenReviewTab() {
		allReviewListComponent.getOpenReviewTabPanel().click();
		return this;
	}

	@Action("Close Review")
	public AllReviewPage changeToCloseReviewTab() {
		allReviewListComponent.getCloseReviewTabPanel().click();
		return this;
	}

	public AllReviewListComponent getAllReviewListComponent() {
		return allReviewListComponent;
	}

	public AllReviewBreadcrumbComponent getAllReviewBreadcrumbComponent() {
		return allReviewBreadcrumbComponent;
	}

	public AllReviewPage assertAllReviewListComponent(Consumer<AllReviewListAssertions> assertion) {
		assertion.accept(getAllReviewListComponent().assertThat());
		return this;
	}

	public AllReviewPage assertAllReviewBreadcrumbComponent(Consumer<AllReviewBreadcrumbAssertions> assertion) {
		assertion.accept(getAllReviewBreadcrumbComponent().assertThat());
		return this;
	}

	public ReviewsDropdownComponent getReviewsDropdownComponent() {
		return reviewsDropdownComponent;
	}

	public TopbarComponent getTopbarComponent() {
		return topbarComponent;
	}
}
