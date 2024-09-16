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

package com.intland.codebeamer.integration.classic.page.tracker.view.document.center;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerDocumentViewTableComponent extends AbstractCodebeamerComponent<TrackerDocumentViewTableComponent, TrackerDocumentViewTableAssertions> {

	public TrackerDocumentViewTableComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#requirements");
	}

	public TrackerDocumentViewTableRowComponent getRowByTrackerItemName(String trackerItemName) {
		return new TrackerDocumentViewTableRowComponent(this.getCodebeamerPage(), trackerItemName);
	}

	public int getNumberOfRows() {
		return this.locator("tr").count();
	}

	@Override
	public TrackerDocumentViewTableAssertions assertThat() {
		return new TrackerDocumentViewTableAssertions(this);
	}
}
