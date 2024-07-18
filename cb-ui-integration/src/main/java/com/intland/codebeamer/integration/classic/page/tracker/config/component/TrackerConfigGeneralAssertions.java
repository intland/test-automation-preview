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

public class TrackerConfigGeneralAssertions
		extends AbstractTrackerConfigAssertions<TrackerConfigGeneralTab, TrackerConfigGeneralAssertions> {

	protected TrackerConfigGeneralAssertions(TrackerConfigGeneralTab component) {
		super(component);
	}

	public TrackerConfigGeneralAssertions nameEqualsTo(String trackerName) {
		return assertAll("Tracker name should be: %s".formatted(trackerName),
				() -> assertThat(getComponent().getNameField()).hasValue(trackerName));
	}
}
