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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReviewAdminFormAssertions
		extends AbstractCodebeamerComponentAssert<ReviewAdminFormComponent, ReviewAdminFormAssertions> {

	public ReviewAdminFormAssertions(ReviewAdminFormComponent component) {
		super(component);
	}

	public ReviewAdminFormAssertions isReviewAdminPageTitleVisible() {
		return assertAll("Review admin page title should be visible",
				() -> assertThat(getComponent().getReviewAdminPageTitle()).isVisible());
	}
}
