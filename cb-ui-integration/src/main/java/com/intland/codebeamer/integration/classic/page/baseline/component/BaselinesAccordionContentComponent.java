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

package com.intland.codebeamer.integration.classic.page.baseline.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.classic.page.baseline.BaselineLandingPage;
import com.intland.codebeamer.integration.classic.page.baseline.BaselineNavigation;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.BaselineSelectDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class BaselinesAccordionContentComponent extends
		AbstractCodebeamerComponent<BaselinesAccordionContentComponent, BaselinesAccordionContentAssertion> {

	private static final String LOCATOR = "baselinesAccordionContent";

	private final Project project;

	@Component("Baseline selection dialog")
	private final BaselineSelectDialog baselineSelectDialog;

	public BaselinesAccordionContentComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, String.format("div[data-testid='%s']", LOCATOR));
		this.project = project;
		this.baselineSelectDialog = new BaselineSelectDialog(codebeamerPage);
	}

	public BaselineSelectDialog openLeftBaselineSelectDialog() {
		getLeftBaselineSelectorButton().click();
		return baselineSelectDialog;
	}

	public BaselineSelectDialog openRightBaselineSelectDialog() {
		getRightBaselineSelectorButton().click();
		return baselineSelectDialog;
	}

	public BaselineNavigation compare() {
		getCompareBaselinesButton().click();
		return new BaselineNavigation(getCodebeamerPage(), project, getSelectedLeftBaseline(), getSelectedRightBaseline());
	}

	public BaselineLandingPage selectLeftBaseline(Baseline baseline) {
		return openLeftBaselineSelectDialog().select(baseline.id())
				.redirectedToBaselinePage(project, baseline, getSelectedLeftBaseline());
	}

	public BaselineLandingPage selectRightBaseline(Baseline baseline) {
		return openRightBaselineSelectDialog().select(baseline.id())
				.redirectedToBaselinePage(project, getSelectedLeftBaseline(), baseline);
	}

	public CodebeamerLocator getComponentElement() {
		return this.locatorByTestId(LOCATOR);
	}

	public CodebeamerLocator getLeftBaselineSelectorButton() {
		return this.locator("input#leftBaselineSelector");
	}

	public CodebeamerLocator getSelectedLeftBaselineId() {
		return this.locator(".leftBaseline .id");
	}

	public CodebeamerLocator getRightBaselineSelectorButton() {
		return this.locator("input#rightBaselineSelector");
	}

	public CodebeamerLocator getSelectedRightBaselineId() {
		return this.locator(".rightBaseline .id");
	}

	public CodebeamerLocator getCompareBaselinesButton() {
		return this.locator("button#compareSelectedBaselines");
	}

	private Baseline getSelectedLeftBaseline() {
		String selector = "#baselineDropArea .leftBaseline .selectedBaselineInfo %s";
		int id = Integer.parseInt(this.locator(String.format(selector, ".id")).text());
		String name = this.locator(String.format(selector, ".name")).text();
		return new Baseline(new BaselineId(id), name, project.id(), new TrackerId(0));
	}

	private Baseline getSelectedRightBaseline() {
		String selector = "#baselineDropArea .rightBaseline .selectedBaselineInfo %s";
		int id = Integer.parseInt(this.locator(String.format(selector, ".id")).text());
		String name = this.locator(String.format(selector, ".name")).text();
		return new Baseline(new BaselineId(id), name, project.id(), new TrackerId(0));
	}

	@Override
	public BaselinesAccordionContentAssertion assertThat() {
		return new BaselinesAccordionContentAssertion(this);
	}
}