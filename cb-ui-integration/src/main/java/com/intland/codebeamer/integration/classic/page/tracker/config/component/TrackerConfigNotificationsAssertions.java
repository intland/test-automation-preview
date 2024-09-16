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

import java.util.Arrays;

import com.intland.codebeamer.integration.api.service.Member;

public class TrackerConfigNotificationsAssertions extends
		AbstractTrackerConfigAssertions<TrackerConfigNotificationsTab, TrackerConfigNotificationsAssertions> {

	public static final String THE_CHECKBOX_VALUE_SHOULD_BE = "The checkbox value should be %s";

	public static final String FIELD_SHOULD_CONTAINS = "Field should contain %s";

	protected TrackerConfigNotificationsAssertions(TrackerConfigNotificationsTab component) {
		super(component);
	}

	public TrackerConfigNotificationsAssertions anyNotificationContains(Member... members) {
		return assertAll(FIELD_SHOULD_CONTAINS.formatted(Arrays.toString(members)),
				() -> getComponent().getAnyNotificationField().assertThat().contains(members));
	}

	public TrackerConfigNotificationsAssertions statusChangeNotificationContains(Member... members) {
		return assertAll(FIELD_SHOULD_CONTAINS.formatted(Arrays.toString(members)),
				() -> getComponent().getStatusChangeNotificationField().assertThat().contains(members));
	}

	public TrackerConfigNotificationsAssertions statusNotificationContains(String statusName, Member... members) {
		return assertAll(FIELD_SHOULD_CONTAINS.formatted(Arrays.toString(members)),
				() -> getComponent().getSubscriberForStatusField(statusName).assertThat().contains(members));
	}

	public TrackerConfigNotificationsAssertions assertOnlyMemberForAnyNotificationCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getOnlyMemberForAnyNotificationCheckbox().assertThat().is(value));
	}

	public TrackerConfigNotificationsAssertions assertOnlyMemberForStatusChangeNotificationCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getOnlyMemberForStatusChangeNotificationCheckbox().assertThat().is(value));
	}

	public TrackerConfigNotificationsAssertions assertOnlyMembersForStatusCheckbox(String statusName, boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getOnlyMemberForStatusCheckbox(statusName).assertThat().is(value));
	}

}
