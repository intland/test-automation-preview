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

import java.util.Arrays;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.reports.NewReportPageNavigation;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReportQueryAccordionComponent
		extends AbstractCodebeamerComponent<ReportQueryAccordionComponent, ReportQueryAccordionAssertions> {

	private static final String PROJECT_CHECKBOX_SELECTOR = "ul.ui-multiselect-optgroup li:has(label:has(span:text-is('%s'))) input[type='checkbox'][name='multiselect_queryWidgetProjectSelector']";

	private static final String TRACKER_CHECKBOX_SELECTOR = "ul.ui-multiselect-optgroup li:has(label:has(span:text-is('%s'))) input[type='checkbox'][name='multiselect_queryWidgetTrackerSelector']";

	private NewReportPageNavigation newReportPageNavigation;

	public ReportQueryAccordionComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.queryContainer");
		this.newReportPageNavigation = new NewReportPageNavigation(getCodebeamerPage());
	}

	public ReportQueryAccordionComponent openReportQueryAccordion() {
		assertThat().isReportQueryAccordionClosed();
		this.getAccordionHeaderElement().click();
		return this;
	}

	public ReportQueryAccordionComponent closeReportQueryAccordion() {
		assertThat().isReportQueryAccordionOpened();
		this.getAccordionHeaderElement().click();
		return this;
	}

	public NewReportPageNavigation save() {
		this.getSaveButton().click();
		return this.newReportPageNavigation;
	}

	public ReportQueryAccordionComponent selectProjects(Project... projects) {
		this.getProjectSelectorDropdown().click();
		Arrays.stream(projects).forEach(this::selectProject);
		return this;
	}

	public ReportQueryAccordionComponent selectTrackers(Tracker... trackers) {
		this.getTrackerSelectorDropdown().click();
		Arrays.stream(trackers).forEach(this::selectTracker);
		return this;
	}

	public NewReportPageNavigation go() {
		this.getGoButton().click();
		return this.newReportPageNavigation;
	}

	public NewReportPageNavigation traceabilityReport() {
		this.getTraceabilityReportButton().click();
		return this.newReportPageNavigation;
	}

	public ReportQueryAccordionComponent toggleQueryType() {
		this.getToggleQueryTypeButton().click();
		return this;
	}

	public ReportQueryAccordionComponent setCbQL(String cbQLString) {
		this.getCbQLTextArea().type(cbQLString);
		return this;
	}

	public CodebeamerLocator getAccordionHeaderElement() {
		return this.locator("h4.search-title.accordion-header");
	}

	public CodebeamerLocator getReportNameElement() {
		return this.locator("#reportName");
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("a#saveButton");
	}

	public CodebeamerLocator getAccordionContentElement() {
		return this.locator("div.accordion-content");
	}

	public CodebeamerLocator getProjectSelectorDropdown() {
		return this.locator("button#queryWidgetProjectSelector_ms");
	}

	public CodebeamerLocator getSelectedProjectLabelValue() {
		return this.locator("button#queryWidgetProjectSelector_ms .labelValueText");
	}

	public CodebeamerLocator getTrackerSelectorDropdown() {
		return this.locator("button#queryWidgetTrackerSelector_ms");
	}

	public CodebeamerLocator getSelectedTrackerLabelValue() {
		return this.locator("button#queryWidgetTrackerSelector_ms span:not([class])");
	}

	public CodebeamerLocator getGoButton() {
		return this.locator("button#queryWidgetGoButton");
	}

	public CodebeamerLocator getToggleQueryTypeButton() {
		return this.locator("button#queryWidgetAdvancedButton");
	}

	public CodebeamerLocator getCbQLTextArea() {
		return this.locator("pre.CodeMirror-line");
	}

	public CodebeamerLocator getTraceabilityReportButton() {
		return this.locator("button#traceabilityReportButton");
	}

	@Override
	public ReportQueryAccordionAssertions assertThat() {
		return new ReportQueryAccordionAssertions(this);
	}

	private void selectProject(Project project) {
		this.getCodebeamerPage().locator(PROJECT_CHECKBOX_SELECTOR.formatted(project.name())).check(true);
	}

	private void selectTracker(Tracker tracker) {
		this.getCodebeamerPage().locator(TRACKER_CHECKBOX_SELECTOR.formatted(tracker.name())).check(true);
	}
}
