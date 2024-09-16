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

public class ReviewStatisticsComponent extends AbstractReviewTabComponent<ReviewStatisticsComponent, ReviewStatisticsAssertions> {

	public ReviewStatisticsComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "[data-cy='review-statistics']");
	}

	@Override
	public ReviewStatisticsAssertions assertThat() {
		return new ReviewStatisticsAssertions(this);
	}

	public CodebeamerLocator getStartedLabelTableDataElement() {
		return this.locator("[data-cy='startedLabel']");
	}

	public CodebeamerLocator getUserImageElement() {
		return this.locator("img.user-image");
	}

	public CodebeamerLocator getUserAndTimeTableDataElement() {
		return this.locator("div.user-and-time");
	}

	public CodebeamerLocator getFinishedLabelTableDataElement() {
		return this.locator("[data-cy='finishedLabel']");
	}

	public CodebeamerLocator getFinishedValueTableDataElement() {
		return this.locator("[data-cy='finishedValueLabel']");
	}

	public CodebeamerLocator getSignedLabelTableDataElement() {
		return this.locator("[data-cy='signedLabel']");
	}

	public CodebeamerLocator getSignedValueTableDataElement() {
		return this.locator("[data-cy='signedValueLabel']");
	}

	public CodebeamerLocator getDeadlineLabelTableDataElement() {
		return this.locator("[data-cy='deadlineLabel']");
	}

	public CodebeamerLocator getDeadlineValueTableDataElement() {
		return this.locator("[data-cy='deadlineValueLabel']");
	}

	public CodebeamerLocator getBaselineLabelTableDataElement() {
		return this.locator("[data-cy='baselineLabel']");
	}

	public CodebeamerLocator getBaselineValueTableDataElement() {
		return this.locator("[data-cy='baselineValueLabel']");
	}

	public CodebeamerLocator getReviewerStatisticsAccordionButton() {
		return this.locator("[data-cy='reviewerStatisticsFieldsetTriggerButton']");
	}

	public CodebeamerLocator getHighChartsElement() {
		return this.locator("highcharts-chart");
	}

	public CodebeamerLocator getStatisticsTableElement() {
		return this.locator("[id='statistics-table']");
	}

	public CodebeamerLocator getItemReviewStatisticsFieldsetElement() {
		return this.locator("[data-cy='itemReviewStatisticsFieldset']");
	}

	public CodebeamerLocator getApprovedLegendElement() {
		return this.locator("[data-cy='review-statistics-legend-item-approved']");
	}

	public CodebeamerLocator getRejectedLegendElement() {
		return this.locator("[data-cy='review-statistics-legend-item-rejected']");
	}

	public CodebeamerLocator getNonReviewedLegendElement() {
		return this.locator("[data-cy='review-statistics-legend-item-not-reviewed']");
	}

	public CodebeamerLocator getShowOnlyInconsistentItemsCheckbox() {
		return this.locator("[data-cy='showOnlyInconsistentItemsCheckbox']");
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	protected String getTabId() {
		return "[data-cy='statisticsTabLabel']";
	}
}