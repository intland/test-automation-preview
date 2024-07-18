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

public class TrackerConfigPermissionsTab
		extends AbstractTrackerConfigTab<TrackerConfigPermissionsTab, TrackerConfigPermissionsAssertions> {

	public TrackerConfigPermissionsTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-permissions");
	}

	@Override
	public TrackerConfigPermissionsAssertions assertThat() {
		return new TrackerConfigPermissionsAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-permissions-tab";
	}


	@Override
	protected String getMainFormId() {
		return "#tracker-customize-permissions";
	}
}
