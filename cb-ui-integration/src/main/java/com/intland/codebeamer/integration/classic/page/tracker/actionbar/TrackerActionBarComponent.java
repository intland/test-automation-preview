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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.reportselector.ReportSelectorComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.MoreActionMenuComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerActionBarComponent
		extends AbstractCodebeamerComponent<TrackerActionBarComponent, TrackerActionBarAssertions> {

	@Component("Action buttons component")
	private final TrackerActionButtonsComponent actionButtonsComponent;

	@Component("Report selector component")
	private final ReportSelectorComponent reportSelectorComponent;

	public TrackerActionBarComponent(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "div.actionBar.reportSelectorActionBar");
		this.reportSelectorComponent = new ReportSelectorComponent(codebeamerPage);
		this.actionButtonsComponent = new TrackerActionButtonsComponent(codebeamerPage, ".actionBar", tracker);
	}

	public MoreActionMenuComponent openMoreActionMenu() {
		return actionButtonsComponent.openMoreActionMenu();
	}

	public TrackerItemCreatePage createTrackerItemFromTemplate(String trackerItemTemplateName) {
		return actionButtonsComponent.createTrackerItemFromTemplate(trackerItemTemplateName);
	}

	public TrackerActionBarComponent reportSelector(Consumer<ReportSelectorComponent> consumer) {
		consumer.accept(reportSelectorComponent);
		return this;
	}

	@Override
	public TrackerActionBarAssertions assertThat() {
		return new TrackerActionBarAssertions(this);
	}

	public TrackerActionButtonsAssertions assertButtons() {
		return actionButtonsComponent.assertThat();
	}

}
