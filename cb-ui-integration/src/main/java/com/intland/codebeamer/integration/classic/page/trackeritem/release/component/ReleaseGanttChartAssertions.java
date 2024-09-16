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

package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleaseGanttChartAssertions extends
		AbstractCodebeamerComponentAssert<ReleaseGanttChartComponent, ReleaseGanttChartAssertions> {

	private static final Pattern OPENED_GANTT_CHART_ACCORDION_PATTERN = Pattern.compile(".*opened.*");

	protected ReleaseGanttChartAssertions(ReleaseGanttChartComponent component) {
		super(component);
	}

	public ReleaseGanttChartAssertions isReleaseGanttChartOpened() {
		assertAll("Release gantt chart should be opened",
				() -> assertThat(getComponent().getAccordionHeaderElement()).hasClass(OPENED_GANTT_CHART_ACCORDION_PATTERN));
		return this;
	}

	public ReleaseGanttChartAssertions isReleaseGanttChartClosed() {
		assertAll("Release gantt chart should be closed",
				() -> assertThat(getComponent().getAccordionHeaderElement()).not()
						.hasClass(OPENED_GANTT_CHART_ACCORDION_PATTERN));
		return this;
	}

	public ReleaseGanttChartAssertions assertTooltip(String input) {
		return assertAll("Tooltip should have '%s' value".formatted(input),
				() -> assertThat(getComponent().getTooltip()).containsText(input));
	}

	public ReleaseGanttChartAssertions assertEditDialogIsHidden() {
		return assertAll("Release chart bar edit dialog is hidden",
				() -> assertThat(getComponent().getReleaseChartBarEditDialog()).isHidden());
	}
}
