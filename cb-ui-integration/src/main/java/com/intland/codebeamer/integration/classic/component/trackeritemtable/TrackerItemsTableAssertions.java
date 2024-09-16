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

package com.intland.codebeamer.integration.classic.component.trackeritemtable;

import java.util.List;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.workconfigitemreferencemodal.GroupingLevelLabel;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemsTableAssertions
		extends AbstractCodebeamerComponentAssert<TrackerItemsTableComponent, TrackerItemsTableAssertions> {

	public TrackerItemsTableAssertions(TrackerItemsTableComponent component) {
		super(component);
	}

	public TrackerItemsTableAssertions groupIs(TrackerItem trackerItem, GroupingLevelLabel... groupingLevelLabel) {
		return groupIs(List.of(trackerItem), groupingLevelLabel);
	}
	
	public TrackerItemsTableAssertions groupIs(List<TrackerItem> trackerItems, GroupingLevelLabel... groupingLevelLabel) {
		return assertAll("Tracker items for the specific labels should be: %s".formatted(trackerItems),
				() -> {
					List<TrackerItemTableRowComponent> rows = getComponent().getTrackerItemsByGroupByLabel(groupingLevelLabel);
					for (int i = 0; i < trackerItems.size(); i++) {
						rows.get(i).assertThat().is(trackerItems.get(i));
					}
				});
	}

	public TrackerItemsTableAssertions isGroupItemCountEquals(GroupingLevelLabel groupingLevelLabel, int itemCount) {
		return assertAll("Grouping Level Item count is equals with %s".formatted(itemCount),
				() -> assertThat(getComponent()
						.getItemCountElement(groupingLevelLabel)).containsText("(%s Item".formatted(itemCount)));
	}

	public TrackerItemsTableAssertions isAllItemCountEquals(int itemCount) {
		return assertAll("All Item count is equals with %s".formatted(itemCount),
				() -> assertThat(getComponent()
						.getAllItemCountElement()).containsText("Grand Totals (%s Items)".formatted(itemCount)));
	}
}
