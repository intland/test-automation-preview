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

package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.filterselector.FilterSelectorComponent;
import com.intland.codebeamer.integration.classic.component.loadingIndicator.LoadingIndicatorComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu.MoreActionMenuComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemActionBarComponent extends AbstractCodebeamerComponent<TrackerItemActionBarComponent, TrackerItemActionBarAssertions> {

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	@Component("Filter selector")
	private FilterSelectorComponent filterSelectorComponent;

	public TrackerItemActionBarComponent(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, "div.actionBar");
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
		filterSelectorComponent = new FilterSelectorComponent(codebeamerPage);
	}
	
	public MoreActionMenuComponent openMoreActionMenu() {
		getMoreActionMenuElement().click();
		String actionMenuId = getMoreActionMenuElement().getAttribute("id");
		return new MoreActionMenuComponent(getCodebeamerPage(), getSelector("div#%spopup".formatted(actionMenuId)),
				trackerId, trackerItemId);
	}
	
	public TrackerItemEditPage edit() {
		getEditItemLink().click();
		return new TrackerItemEditPage(getCodebeamerPage(), trackerId, trackerItemId).isActive();
	}
	
	public TrackerItemEditPage createItemFromTemplate() {
		getCreateItemFromTemplateLink().click();
		return new TrackerItemEditPage(getCodebeamerPage(), trackerId, trackerItemId).isActive();
	}

	public TrackerItemEditPage setStatus(String status) {
		getStatus(status).click();
		return new TrackerItemEditPage(getCodebeamerPage(), trackerId, trackerItemId).isActive();
	}
	
	public CodebeamerLocator getMoreActionMenuElement() {
		return this.locator("td > span[id^='actionMenu']");
	}
	
	public CodebeamerLocator getEditItemLink() {
		return this.locator("a.actionLink[href$='/issue/%d/edit'],a.actionLink[href$='/cmdb/%d/edit']".formatted(trackerItemId.id(), trackerItemId.id()));
	}
	
	public CodebeamerLocator getCreateItemFromTemplateLink() {
		return this.locator("a#createItemFromTemplate.actionLink");
	}

	public CodebeamerLocator getStatus(String status) {
		return this.locator("a.actionLink:has-text('%s')".formatted(status));
	}

	public TrackerItemActionBarComponent chooseGroupByField(String listElementName) {
		selectGroupBy();
		filterSelectorComponent.addGroupByOption(listElementName);
		return this;
	}

	public TrackerItemActionBarComponent filter() {
		getSearchButton().click();
		new LoadingIndicatorComponent(getCodebeamerPage(), "#inlinedPopupIframe").waitForDetached();
		return this;
	}

	private CodebeamerLocator getSearchButton () {
		return this.locator("#actionBarSearchButton");
	}

	private void selectGroupBy() {
		this.locator(".groupByLabel").click();
	}
	
	@Override
	public TrackerItemActionBarAssertions assertThat() {
		return new TrackerItemActionBarAssertions(this);
	}

}
