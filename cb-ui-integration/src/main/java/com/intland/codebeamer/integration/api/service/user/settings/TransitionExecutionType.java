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
package com.intland.codebeamer.integration.api.service.user.settings;

public enum TransitionExecutionType {
	USE_TRACKER_DEFAULT,
	USE_QUICK_TRANSITIONS, // the transitions are always executed silently when possible
	USE_OVERLAY_TRANSITIONS; // always open an editor overlay when executing transitions

	public static TransitionExecutionType getDefault() {
		return USE_TRACKER_DEFAULT;
	}
}
