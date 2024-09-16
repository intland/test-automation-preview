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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.workconfigitemreferencemodal.GroupingLevelLabel;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemsTableComponent
		extends AbstractCodebeamerComponent<TrackerItemsTableComponent, TrackerItemsTableAssertions> {

	public TrackerItemsTableComponent(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, null);
	}

	public TrackerItemsTableComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#trackerItems");
	}

	@Override
	public TrackerItemsTableAssertions assertThat() {
		return new TrackerItemsTableAssertions(this);
	}

	public TrackerItemTableHeaderComponent getTableHeader() {
		return new TrackerItemTableHeaderComponent(getCodebeamerPage(), getFrameLocator());
	}

	public TrackerItemTableRowComponent getTableRow(TrackerItemId trackerItemId) {
		return new TrackerItemTableRowComponent(getCodebeamerPage(), getFrameLocator(), trackerItemId);
	}

	public TrackerItemTableRowComponent getTableRowInGroup(TrackerItemId trackerItemId,
			GroupingLevelLabel... groupingLevelLabel) {
		return getTrackerItemsByGroupByLabel(groupingLevelLabel).stream()
				.filter(c -> trackerItemId.equals(c.getTrackerItemId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Tracker item cannot be found by id: " + trackerItemId));
	}

	protected CodebeamerLocator getItemCountElement(GroupingLevelLabel groupingLevelLabel) {
		return this.locator("tr td.groupping-level-%s:has-text('%s: %s (')"
				.formatted(groupingLevelLabel.level(), groupingLevelLabel.name(), groupingLevelLabel.value()));
	}

	protected CodebeamerLocator getAllItemCountElement() {
		return this.locator("tr td[class*=groupping-level-footer]");
	}

	protected List<CodebeamerLocator> getAllRowElements() {
		return this.locator("tbody tr").all();
	}

	protected TrackerItemTableRowComponent getTrackerItemTableRowComponent(int rowIndex) {
		return new TrackerItemTableRowComponent(getCodebeamerPage(), getFrameLocator(), rowIndex);
	}

	protected List<TrackerItemTableRowComponent> getTrackerItemsByGroupByLabel(GroupingLevelLabel... groupingLevelLabel) {
		List<TrackerItemTableRowComponent> result = new LinkedList<>();
		List<CodebeamerLocator> rows = getAllRowElements();

		int currentGroupingLevelIndex = 0;
		for (int i = 0; i < rows.size(); i++) {
			if (currentGroupingLevelIndex < groupingLevelLabel.length
					&& isGroupingLevelEquals(rows.get(i).text(), groupingLevelLabel[currentGroupingLevelIndex])) {
				++currentGroupingLevelIndex;
			} else {
				TrackerItemTableRowComponent trackerItemTableRowComponent = getTrackerItemTableRowComponent(i + 1);
				if (!trackerItemTableRowComponent.getGroupingLevelClass().isEmpty()
						&& trackerItemTableRowComponent.getIndentLevel() <= currentGroupingLevelIndex) {
					--currentGroupingLevelIndex;
				}

				if (currentGroupingLevelIndex >= groupingLevelLabel.length) {
					if (!trackerItemTableRowComponent.getGroupingLevelClass().isEmpty()) {
						break;
					}
					result.add(trackerItemTableRowComponent);
				}
			}
		}
		return result;
	}

	private boolean isGroupingLevelEquals(String elementText, GroupingLevelLabel groupingLevelLabel) {
		Pattern patternNormal = Pattern.compile("%s: %s.*".formatted(groupingLevelLabel.name(), groupingLevelLabel.value()));
		Matcher matcher = patternNormal.matcher(elementText);
		return matcher.find();
	}

}
