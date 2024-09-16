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

package com.intland.codebeamer.integration.classic.component.reportselector;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.filterselector.FilterSelectorComponent;
import com.intland.codebeamer.integration.classic.component.loadingIndicator.LoadingIndicatorComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.manager.TrackerViewManagerAssertions;
import com.intland.codebeamer.integration.classic.page.tracker.view.manager.TrackerViewManagerComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReportSelectorComponent extends
		AbstractCodebeamerComponent<ReportSelectorComponent, ReportSelectorAssertions> {

	@Component("Filter selector")
	private FilterSelectorComponent filterSelectorComponent;

	@Component("Tracker view manager")
	private TrackerViewManagerComponent trackerViewManagerComponent;

	public ReportSelectorComponent(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, null);
	}

	public ReportSelectorComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator,  ".reportSelectorTable");
		filterSelectorComponent = new FilterSelectorComponent(codebeamerPage, frameLocator);
		trackerViewManagerComponent = new TrackerViewManagerComponent(codebeamerPage, frameLocator);
	}

	public ReportSelectorAssertions assertThat() {
		return new ReportSelectorAssertions(this);
	}

	public ReportSelectorComponent chooseGroupByField(String listElementName) {
		selectGroupBy();
		filterSelectorComponent.addGroupByOption(listElementName);
		return this;
	}

	public ReportSelectorComponent chooseOrderByField(String listElementName) {
		selectOrderBy();
		filterSelectorComponent.addOrderByOption(listElementName);
		return this;
	}

	public ReportSelectorComponent viewManager(Consumer<TrackerViewManagerComponent> consumer) {
		getViewManagerElement().click();
		consumer.accept(trackerViewManagerComponent);
		return this;
	}

	public ReportSelectorComponent assertViewManager(Consumer<TrackerViewManagerAssertions> assertions) {
		getViewManagerElement().click();
		assertions.accept(trackerViewManagerComponent.assertThat());
		return this;
	}

	public ReportSelectorComponent filter() {
		getSearchButton().click();
		new LoadingIndicatorComponent(getCodebeamerPage(), "#inlinedPopupIframe").waitForDetached();
		return this;
	}

	public CodebeamerLocator getOpenInTableViewLink() {
		return locator(".drillDownLink");
	}

	private CodebeamerLocator getSearchButton () {
		return this.locator("#actionBarSearchButton");
	}

	private void selectGroupBy() {
		this.locator(".groupByLabel").click();
	}

	private void selectOrderBy() {
		this.locator(".orderByLabel").click();
	}

	private CodebeamerLocator getViewManagerElement() {
		return this.locator(".reportSelectorButton");
	}

}
