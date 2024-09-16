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

import static com.intland.codebeamer.integration.classic.page.trackeritem.release.dialog.ReleaseChartBarEditDialog.DIALOG_SELECTOR;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.dialog.ReleaseChartBarEditDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseGanttChartComponent
		extends AbstractCodebeamerComponent<ReleaseGanttChartComponent, ReleaseGanttChartAssertions> {

	public ReleaseGanttChartComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#releaseGanttChart");
	}

	public ReleaseGanttChartComponent openReleaseGanttChart() {
		assertThat().isReleaseGanttChartClosed();
		getAccordionElement().click();
		return this;
	}

	public ReleaseGanttChartComponent closeReleaseGanttChart() {
		assertThat().isReleaseGanttChartOpened();
		getAccordionElement().click();
		return this;
	}

	public ReleaseGanttChartComponent moveStartDateOfReleaseBar(int index, int percent) {
		getReleaseBarElement(index).dragLeftSide(getGanttRowElement(index), percent);
		return this;
	}

	public ReleaseGanttChartComponent moveEndDateOfReleaseBar(int index, int percent) {
		getReleaseBarElement(index).dragRightSide(getGanttRowElement(index), percent);
		return this;
	}

	/**
	 * Opens the dialog to edit a gantt chart bar for a specific release item.
	 *
	 * @param index the ordinal number of the release in the chart, starting from 1
	 * @return the dialog object
	 */
	public ReleaseChartBarEditDialog editReleaseBar(int index) {
		doubleClickOnReleaseBar(index);
		return new ReleaseChartBarEditDialog(getCodebeamerPage());
	}

	public ReleaseGanttChartComponent doubleClickOnReleaseBar(int index) {
		getReleaseBarElement(index).doubleClick();
		return this;
	}

	public ReleaseGanttChartComponent hoverOnReleaseBar(int index) {
		getReleaseBarElement(index).hover();
		return this;
	}

	public CodebeamerLocator getReleaseBarElement(int index) {
		// UI-AUTOMATION: each gantt bar element has a task-id, however, it does not contain the exact tracker item id, only
		// an index, it would be nice to identify these bars inside the chart with the corresponding item id
		return this.locator("div.gantt_bars_area div.gantt_task_line:nth-child(%d)".formatted(index));
	}

	public CodebeamerLocator getGanttRowElement(int index) {
		return this.locator("div.gantt_task_row:nth-child(%d)".formatted(index));
	}

	public CodebeamerLocator getAccordionHeaderElement() {
		return getAccordionElement().concat("h4.accordion-header");
	}

	public CodebeamerLocator getAccordionElement() {
		return getCodebeamerPage().locator("div.ganttAccordion");
	}

	public CodebeamerLocator getTooltip() {
		return this.locator("div.gantt_tooltip");
	}

	public CodebeamerLocator getReleaseChartBarEditDialog() {
		return this.locator(DIALOG_SELECTOR);
	}

	@Override
	public ReleaseGanttChartAssertions assertThat() {
		return new ReleaseGanttChartAssertions(this);
	}
}
