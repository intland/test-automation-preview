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

package com.intland.codebeamer.integration.classic.page.baseline;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.baseline.component.BaselineActionBarComponent;
import com.intland.codebeamer.integration.classic.page.baseline.component.BaselineCompareResultsAccordionContentComponent;
import com.intland.codebeamer.integration.classic.page.baseline.component.BaselineTableComponent;
import com.intland.codebeamer.integration.classic.page.baseline.component.BaselinesAccordionContentComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public abstract class AbstractBaselinePage<P extends AbstractBaselinePage> extends AbstractCodebeamerPage<P> {

	protected final Project project;

	protected Baseline leftBaseline;

	protected Baseline rightBaseline;

	@Component("Action bar")
	private final BaselineActionBarComponent baselineActionBarComponent;

	@Component("Baseline table")
	private final BaselineTableComponent baselineTableComponent;

	@Component("Baselines accordion content")
	private final BaselinesAccordionContentComponent baselinesAccordionContentComponent;

	@Component("Compare Results accordion content")
	private final BaselineCompareResultsAccordionContentComponent baselineCompareResultsAccordionContentComponent;

	public AbstractBaselinePage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.baselineActionBarComponent = new BaselineActionBarComponent(codebeamerPage);
		this.baselineTableComponent = new BaselineTableComponent(codebeamerPage);
		this.baselinesAccordionContentComponent = new BaselinesAccordionContentComponent(codebeamerPage, project);
		this.baselineCompareResultsAccordionContentComponent = new BaselineCompareResultsAccordionContentComponent(
				codebeamerPage);
	}

	public AbstractBaselinePage(CodebeamerPage codebeamerPage, Project project, Baseline leftBaseline,
			Baseline rightBaseline) {
		super(codebeamerPage);
		this.project = project;
		this.leftBaseline = leftBaseline;
		this.rightBaseline = rightBaseline;
		this.baselineActionBarComponent = new BaselineActionBarComponent(codebeamerPage);
		this.baselineTableComponent = new BaselineTableComponent(codebeamerPage);
		this.baselinesAccordionContentComponent = new BaselinesAccordionContentComponent(codebeamerPage, project);
		this.baselineCompareResultsAccordionContentComponent = new BaselineCompareResultsAccordionContentComponent(
				codebeamerPage);
	}

	@Action("Visit")
	public P visit() {
		navigate(getUrl());
		return isActive();
	}

	@Override
	public P isActive() {
		assertUrl(getPath(), "Baseline page should be the active page");
		return (P) this;
	}

	public BaselineActionBarComponent getBaselineActionBarComponent() {
		return baselineActionBarComponent;
	}

	public P baselineActionBarComponent(Consumer<BaselineActionBarComponent> formConsumer) {
		formConsumer.accept(baselineActionBarComponent);
		return (P) this;
	}

	public BaselineTableComponent getBaselineTableComponent() {
		return baselineTableComponent;
	}

	public P baselineTableComponent(Consumer<BaselineTableComponent> formConsumer) {
		formConsumer.accept(baselineTableComponent);
		return (P) this;
	}

	public BaselinesAccordionContentComponent getBaselinesAccordionContentComponent() {
		return baselinesAccordionContentComponent;
	}

	public P baselinesAccordionContentComponent(Consumer<BaselinesAccordionContentComponent> formConsumer) {
		formConsumer.accept(baselinesAccordionContentComponent);
		return (P) this;
	}

	public BaselineCompareResultsAccordionContentComponent getBaselineCompareResultsAccordionContentComponent() {
		return baselineCompareResultsAccordionContentComponent;
	}

	public P baselineCompareResultsAccordionContentComponent(
			Consumer<BaselineCompareResultsAccordionContentComponent> formConsumer) {
		formConsumer.accept(baselineCompareResultsAccordionContentComponent);
		return (P) this;
	}

	private String getUrl() {
		return getPath().toString().replaceFirst("\\.\\*/", "").replaceAll("\\.\\*$", "");
	}

	protected abstract Pattern getPath();
}
