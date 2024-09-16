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

public class ReviewStatisticsAssertions extends AbstractReviewTabAssertions<ReviewStatisticsComponent, ReviewStatisticsAssertions> {

	protected ReviewStatisticsAssertions(ReviewStatisticsComponent component) {
		super(component);
	}

	public ReviewStatisticsAssertions isReady() {
		return assertAll("Review Statistics Page is ready", () -> {
			assertThat(getComponent().getStartedLabelTableDataElement()).isVisible();
			assertThat(getComponent().getUserImageElement()).isVisible();
			assertThat(getComponent().getUserAndTimeTableDataElement()).isVisible();
			assertThat(getComponent().getFinishedLabelTableDataElement()).isVisible();
			assertThat(getComponent().getFinishedValueTableDataElement()).isVisible();
			assertThat(getComponent().getSignedLabelTableDataElement()).isVisible();
			assertThat(getComponent().getSignedValueTableDataElement()).isVisible();
			assertThat(getComponent().getDeadlineLabelTableDataElement()).isVisible();
			assertThat(getComponent().getDeadlineValueTableDataElement()).isVisible();
			assertThat(getComponent().getBaselineLabelTableDataElement()).isVisible();
			assertThat(getComponent().getBaselineValueTableDataElement()).isVisible();
			assertThat(getComponent().getReviewerStatisticsAccordionButton()).isVisible();
			assertThat(getComponent().getHighChartsElement()).isVisible();
			assertThat(getComponent().getStatisticsTableElement()).isVisible();
			assertThat(getComponent().getItemReviewStatisticsFieldsetElement()).isVisible();
			assertThat(getComponent().getApprovedLegendElement()).isEditable();
			assertThat(getComponent().getRejectedLegendElement()).isEditable();
			assertThat(getComponent().getNonReviewedLegendElement()).isEditable();
			assertThat(getComponent().getShowOnlyInconsistentItemsCheckbox()).isEditable();
		});
	}
}