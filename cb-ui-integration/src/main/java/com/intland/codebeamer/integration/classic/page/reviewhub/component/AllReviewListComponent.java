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

package com.intland.codebeamer.integration.classic.page.reviewhub.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class AllReviewListComponent extends AbstractCodebeamerComponent<AllReviewListComponent, AllReviewListAssertions> {

	public AllReviewListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-review-list");
	}

	@Override
	public AllReviewListAssertions assertThat() {
		return new AllReviewListAssertions(this);
	}

	public CodebeamerLocator getAllReviewsHeaderElement() {
		return this.locator("div[data-cy='allReviewsHeaderText']");
	}

	public CodebeamerLocator getAllReviewsHeaderIcon() {
		return this.locator("[class='review-list-header-icon']");
	}

	public CodebeamerLocator getCreateReviewButton() {
		return this.locator("button[data-cy='create-review-button']");
	}

	public CodebeamerLocator getClearButton() {
		return this.locator("[data-cy='reviewSearchClearButton']");
	}

	public CodebeamerLocator getApplyButton() {
		return this.locator("[data-cy='reviewSearchApplyButton']");
	}

	public CodebeamerLocator getOpenReviewTabPanel() {
		return this.locator("[data-cy='reviewListOpenReviewsTabButton']");
	}

	public CodebeamerLocator getOpenReviewTabTitleElement() {
		return this.locator("[data-cy='activeTabTitle-Open Reviews']");
	}

	public CodebeamerLocator getCloseReviewTabPanel() {
		return this.locator("[data-cy='reviewListClosedReviewsTabButton']");
	}

	public CodebeamerLocator getCloseReviewTabTitleElement() {
		return this.locator("[data-cy='activeTabTitle-Closed Reviews']");
	}
}
