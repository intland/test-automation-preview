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

public class ReviewTemplateAssertions extends
		AbstractCodebeamerComponentAssert<ReviewTemplateComponent, ReviewTemplateAssertions> {

	protected ReviewTemplateAssertions(ReviewTemplateComponent component) {
		super(component);
	}

	public ReviewTemplateAssertions isReady() {
		return assertAll("All review template page", () -> {
			assertThat(getComponent().createNewReviewTemplateButton()).isVisible();
			assertThat(getComponent().getFirstPageForReviewTemplateListButton()).isVisible();
			assertThat(getComponent().getNextPageForReviewTemplateListButton()).isVisible();
			assertThat(getComponent().getPreviousPageForReviewTemplateListButton()).isVisible();
			assertThat(getComponent().getLastPageForReviewTemplateListButton()).isVisible();
			assertThat(getComponent().selectNoOfRowsForReviewTemplateListElement()).isVisible();
			assertThat(getComponent().selectProjectForReviewTemplateElement()).isVisible();
		});
	}
}
