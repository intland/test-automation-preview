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

package com.intland.codebeamer.integration.classic.page.tracker.view.table.content;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemTableHeaderComponent;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemTableRowComponent;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemsTableComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerTableViewContentComponent extends
		AbstractCodebeamerComponent<TrackerTableViewContentComponent, TrackerTableViewContentAssertions> {

	@Component("Tracker items table")
	private final TrackerItemsTableComponent trackerItemsTableComponent;

	public TrackerTableViewContentComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tableViewPane");
		this.trackerItemsTableComponent = new TrackerItemsTableComponent(codebeamerPage);
	}

	@Override
	public TrackerTableViewContentAssertions assertThat() {
		return new TrackerTableViewContentAssertions(this);
	}

	public TrackerItemTableHeaderComponent getTableHeader() {
		return trackerItemsTableComponent.getTableHeader();
	}

	public TrackerItemTableRowComponent getTableRow(TrackerItemId trackerItemId) {
		return trackerItemsTableComponent.getTableRow(trackerItemId);
	}

}
