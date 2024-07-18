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

public class TrackerConfigNotificationsTab extends AbstractTrackerConfigTab<TrackerConfigNotificationsTab, TrackerConfigNotificationsAssertions> {

	public TrackerConfigNotificationsTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-notification");
	}

	@Override
	public TrackerConfigNotificationsAssertions assertThat() {
		return new TrackerConfigNotificationsAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-notification-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#trackerItemSubscriptionForm";
	}
}
