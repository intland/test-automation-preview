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
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewTemplateComponent
		extends AbstractCodebeamerComponent<ReviewTemplateComponent, ReviewTemplateAssertions> {

	public ReviewTemplateComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".review-template-list-content-container");
	}

	@Action("Click on create review template")
	public ReviewHubNavigation createReviewTemplate() {
		createNewReviewTemplateButton().click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	@Action("Go to next page of review template")
	public ReviewHubNavigation nextPageOfReviewTemplate() {
		getNextPageForReviewTemplateListButton().click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	@Action("Go to previous page of review template")
	public ReviewHubNavigation previousPageOfReviewTemplate() {
		getPreviousPageForReviewTemplateListButton().click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	@Action("Go to first page of review template")
	public ReviewHubNavigation firstPageOfReviewTemplate() {
		getFirstPageForReviewTemplateListButton().click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	@Action("Go to last page of review template")
	public ReviewHubNavigation lastPageOfReviewTemplate() {
		getLastPageForReviewTemplateListButton().click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	@Action("Select no of rows to display on review template")
	public ReviewHubNavigation selectNoOfRowsForReviewTemplate(String noOfRows) {
		selectNoOfRowsForReviewTemplateListElement().click();
		getCodebeamerPage().locator("li[role='option'][aria-label='%s']".formatted(noOfRows)).click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	public CodebeamerLocator createNewReviewTemplateButton() {
		return this.locator("button[id='createReviewTemplateButton']");
	}

	public CodebeamerLocator selectProjectForReviewTemplateElement() {
		return this.locator(".p-multiselect");
	}

	public CodebeamerLocator getNextPageForReviewTemplateListButton() {
		return getCodebeamerPage().locator("button.p-paginator-next");
	}

	public CodebeamerLocator getPreviousPageForReviewTemplateListButton() {
		return this.locator("button.p-paginator-prev");
	}

	public CodebeamerLocator getFirstPageForReviewTemplateListButton() {
		return this.locator("button.p-paginator-first");
	}

	public CodebeamerLocator getLastPageForReviewTemplateListButton() {
		return this.locator("button.p-paginator-last");
	}

	public CodebeamerLocator selectNoOfRowsForReviewTemplateListElement() {
		return this.locator("div.p-paginator-rpp-options");
	}

	@Override
	public ReviewTemplateAssertions assertThat() {
		return new ReviewTemplateAssertions(this);
	}
}
