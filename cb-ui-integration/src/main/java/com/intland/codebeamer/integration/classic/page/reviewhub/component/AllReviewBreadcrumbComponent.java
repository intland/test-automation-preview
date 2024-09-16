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

public class AllReviewBreadcrumbComponent
		extends AbstractCodebeamerComponent<AllReviewBreadcrumbComponent, AllReviewBreadcrumbAssertions> {

	public AllReviewBreadcrumbComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-breadcrumb");
	}

	public CodebeamerLocator getAllReviewsTitleElement() {
		return this.locator("[class='non-link truncated-text ng-star-inserted']");
	}

	public CodebeamerLocator getBookMarkButton() {
		return this.locator("[data-cy='bookmarkButton']");
	}

	@Override
	public AllReviewBreadcrumbAssertions assertThat() {
		return new AllReviewBreadcrumbAssertions(this);
	}
}