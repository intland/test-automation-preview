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

package com.intland.codebeamer.integration.classic.page.reports.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReportQueryAccordionAssertions
		extends AbstractCodebeamerComponentAssert<ReportQueryAccordionComponent, ReportQueryAccordionAssertions> {

	private static final Pattern OPENED_REPORT_QUERY_ACCORDION_PATTERN = Pattern.compile(".*opened.*");

	protected ReportQueryAccordionAssertions(ReportQueryAccordionComponent component) {
		super(component);
	}

	public ReportQueryAccordionAssertions isReportQueryAccordionOpened() {
		assertAll("Report query accordion should be opened", () -> {
			assertThat(getComponent().getAccordionHeaderElement()).hasClass(OPENED_REPORT_QUERY_ACCORDION_PATTERN);
			assertThat(getComponent().getAccordionContentElement()).hasClass(OPENED_REPORT_QUERY_ACCORDION_PATTERN);
		});
		return this;
	}

	public ReportQueryAccordionAssertions isReportQueryAccordionClosed() {
		assertAll("Report query accordion should be closed", () -> {
			assertThat(getComponent().getAccordionHeaderElement()).not().hasClass(OPENED_REPORT_QUERY_ACCORDION_PATTERN);
			assertThat(getComponent().getAccordionContentElement()).not().hasClass(OPENED_REPORT_QUERY_ACCORDION_PATTERN);
		});
		return this;
	}

	public ReportQueryAccordionAssertions selectedProjectsEqualsTo(String labelValueText) {
		assertAll("Selected project label value text should contain '%s'".formatted(labelValueText),
				() -> assertThat(getComponent().getSelectedProjectLabelValue()).hasText(labelValueText));
		return this;
	}

	public ReportQueryAccordionAssertions selectedTrackersEqualsTo(String labelValueText) {
		assertAll("Selected tracker label value text should contain '%s'".formatted(labelValueText),
				() -> assertThat(getComponent().getSelectedTrackerLabelValue()).hasText(labelValueText));
		return this;
	}

	public ReportQueryAccordionAssertions cbQLEqulsTo(String cbQLString) {
		assertAll("CbQL string in the text area should be '%s'".formatted(cbQLString),
				() -> assertThat(getComponent().getCbQLTextArea()).hasText(cbQLString));
		return this;
	}

	public ReportQueryAccordionAssertions isReportQueryAccordionReady() {
		assertAll("Report query accordion is ready to use", () -> {
			assertThat(getComponent().getAccordionHeaderElement()).isVisible();
			assertThat(getComponent().getReportNameElement()).isVisible();
			assertThat(getComponent().getSaveButton()).isVisible();
			assertThat(getComponent().getAccordionContentElement()).isVisible();
			assertThat(getComponent().getProjectSelectorDropdown()).isVisible();
			assertThat(getComponent().getSelectedProjectLabelValue()).isVisible();
			assertThat(getComponent().getTrackerSelectorDropdown()).isVisible();
			assertThat(getComponent().getSelectedTrackerLabelValue()).isVisible();
			assertThat(getComponent().getGoButton()).isVisible();
			assertThat(getComponent().getToggleQueryTypeButton()).isVisible();
			assertThat(getComponent().getTraceabilityReportButton()).isVisible();
		});
		return this;
	}
}
