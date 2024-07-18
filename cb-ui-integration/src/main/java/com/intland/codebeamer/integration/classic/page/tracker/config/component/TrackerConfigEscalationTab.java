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

package com.intland.codebeamer.integration.classic.page.tracker.config.component;

import com.intland.codebeamer.integration.CodebeamerPage;

public class TrackerConfigEscalationTab extends AbstractTrackerConfigTab<TrackerConfigEscalationTab, TrackerConfigEscalationAssertions> {

	public TrackerConfigEscalationTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-escalation");
	}

	@Override
	public TrackerConfigEscalationAssertions assertThat() {
		return new TrackerConfigEscalationAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-escalation-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#tracker-customize-escalation";
	}
}
