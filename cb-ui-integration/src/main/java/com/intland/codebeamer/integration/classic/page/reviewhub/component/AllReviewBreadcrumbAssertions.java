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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class AllReviewBreadcrumbAssertions
		extends AbstractCodebeamerComponentAssert<AllReviewBreadcrumbComponent, AllReviewBreadcrumbAssertions> {

	protected AllReviewBreadcrumbAssertions(AllReviewBreadcrumbComponent component) {
		super(component);
	}

	public AllReviewBreadcrumbAssertions isReady() {
		return assertAll("All Review Breadcrumb Visible", () -> {
			assertThat(getComponent().getAllReviewsTitleElement()).isVisible();
			assertThat(getComponent().getBookMarkButton()).isVisible();
			assertThat(getComponent().getBookMarkButton()).isEnabled();
		});
	}
}
