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

package com.intland.codebeamer.integration.classic.page.tracker.actionbar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.MoreActionMenuComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public class TrackerActionButtonsComponent
		extends AbstractCodebeamerComponent<TrackerActionButtonsComponent, TrackerActionButtonsAssertions> {

	private static final String BASELINES_AND_BRANCHES_BUTTON_TITLE = "Baselines and Branches";

	private final Tracker tracker;

	public TrackerActionButtonsComponent(CodebeamerPage codebeamerPage, Tracker tracker) {
		this(codebeamerPage, ".actionBarColumn", tracker);
	}

	public TrackerActionButtonsComponent(CodebeamerPage codebeamerPage, String componentLocator, Tracker tracker) {
		super(codebeamerPage, componentLocator);
		this.tracker = tracker;
	}

	public MoreActionMenuComponent openMoreActionMenu() {
		getMoreActionMenuElement().click();
		this.waitForResponse(urlEndsWith("trackerMenu.spr"), 200, 10000);
		return new MoreActionMenuComponent(getCodebeamerPage(), getSelector("div.yuimenu"), tracker);
	}

	public TrackerItemCreatePage createTrackerItemFromTemplate(String trackerItemTemplateName) {
		getCreateItemFromTemplateElement().click();
		getItemTemplateLinkByName(trackerItemTemplateName).click(new Locator.ClickOptions().setForce(true));
		return new TrackerItemCreatePage(getCodebeamerPage(), tracker.id());
	}

	public CodebeamerLocator getCreateItemFromTemplateElement() {
		return this.locator(".createItemFromTemplate");
	}

	public CodebeamerLocator getItemTemplateLinkByName(String trackerItemTemplateName) {
		return this.locator("#itemTemplatespopup a:has-text('%s')".formatted(trackerItemTemplateName));
	}

	public CodebeamerLocator getMoreActionMenuElement() {
		return this.locatorByTestId("moreActionMenuButton");
	}

	public CodebeamerLocator getBaselinesandBranchesButton() {
		return this.locator("a.actionLink[title='%s']".formatted(BASELINES_AND_BRANCHES_BUTTON_TITLE));
	}

	@Override
	public TrackerActionButtonsAssertions assertThat() {
		return new TrackerActionButtonsAssertions(this);
	}

}
