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

package com.intland.codebeamer.integration.nextgen.page.topbar.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReviewsDropdownAssertions extends
		AbstractCodebeamerComponentAssert<ReviewsDropdownComponent, ReviewsDropdownAssertions> {

	public ReviewsDropdownAssertions(ReviewsDropdownComponent component) {
		super(component);
	}

	public ReviewsDropdownAssertions assertDropdownIsVisible() {
		return assertAll("Reviews dropdown is visible", () -> assertThat(getComponent().getReviewsButton()).isVisible());
	}

	public ReviewsDropdownAssertions isReadyForRegularUser() {
		return assertAll("Reviews dropdown is ready", () -> {
			assertThat(getComponent().getCreateReviewOption()).isVisible();
			assertThat(getComponent().getAllReviewsOption()).isVisible();
			assertThat(getComponent().getReviewsTemplatesOption()).isVisible();
			assertThat(getComponent().getMergeRequestsOption()).isVisible();
		});
	}

	public ReviewsDropdownAssertions isReviewAministrationOptionVisible() {
		return assertAll("Review administration option is visible ",
				() -> assertThat(getComponent().getReviewAdministrationOption()).isVisible());
	}
}
