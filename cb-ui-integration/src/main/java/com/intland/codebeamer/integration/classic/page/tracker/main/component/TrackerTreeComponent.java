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

package com.intland.codebeamer.integration.classic.page.tracker.main.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerTreeComponent extends AbstractCodebeamerComponent<TrackerTreeComponent, TrackerTreeAssertions> {

	public TrackerTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#west");
	}

	public CodebeamerLocator getWorkTrackerByName(String trackerName) {
		return this.locator("li#work:has([rawname='%s'])".formatted(trackerName));
	}

	public CodebeamerLocator getConfigTrackerByName(String trackerName) {
		return this.locator("li#config:has([rawname='%s'])".formatted(trackerName));
	}

	@Override
	public TrackerTreeAssertions assertThat() {
		return new TrackerTreeAssertions(this);
	}
}
