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

package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets.AddTestCasesToTestSetsDialog;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.GenerateTestRunsDialog;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns.testruntrackerselector.TestRunTrackerSelectorDialog;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.ManageItemTemplatesDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public class MoreActionMenuComponent extends AbstractCodebeamerComponent<MoreActionMenuComponent, MoreActionMenuAssertions> {

	private final Tracker tracker;

	public MoreActionMenuComponent(CodebeamerPage codebeamerPage, String selector, Tracker tracker) {
		super(codebeamerPage, selector);
		this.tracker = tracker;
	}

	public ManageItemTemplatesDialog selectManageItemTemplates() {
		getManageItemTemplatesLink().click();
		return new ManageItemTemplatesDialog(getCodebeamerPage(), tracker);
	}

	public GenerateTestRunsDialog generateTestRuns() {
		getGenerateTestRunsLink().click();
		return new GenerateTestRunsDialog(getCodebeamerPage(), tracker,
				"iframe[src*='createtestrunmultiplesources.spr'],iframe[src*='empty.jsp']");
	}

	public TestRunTrackerSelectorDialog selectTestRunTracker() {
		getGenerateTestRunsLink().click();
		return new TestRunTrackerSelectorDialog(getCodebeamerPage(), tracker);
	}

	public AddTestCasesToTestSetsDialog addTestCasesToTestSets() {
		getAddTestCasesToTestSetsLink().click();
		return new AddTestCasesToTestSetsDialog(getCodebeamerPage(), tracker);
	}

	public AddTestCasesToTestSetsDialog addTestCasesToTestSets(double timeout) {
		getAddTestCasesToTestSetsLink().click(new Locator.ClickOptions().setTimeout(timeout));
		return new AddTestCasesToTestSetsDialog(getCodebeamerPage(), tracker);
	}

	public CodebeamerLocator getManageItemTemplatesLink() {
		return this.locatorByTestId("manageItemTemplatesButton");
	}

	public CodebeamerLocator getGenerateTestRunsLink() {
		return this.locatorByTestId("generateTestRunsButton");
	}

	public CodebeamerLocator getAddTestCasesToTestSetsLink() {
		return this.locatorByTestId("addTestCasesToTestSetButton");
	}

	@Override
	public MoreActionMenuAssertions assertThat() {
		return new MoreActionMenuAssertions(this);
	}

}
