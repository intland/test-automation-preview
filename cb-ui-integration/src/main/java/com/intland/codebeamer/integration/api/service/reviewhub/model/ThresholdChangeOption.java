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

package com.intland.codebeamer.integration.api.service.reviewhub.model;

public enum ThresholdChangeOption {

	DO_NOT_CHANGE("DoNotChange"),

	SET_TO_APPROVED("SetToApproved"),

	SET_TO_REJECTED("SetToRejected"),

	SET_TO_MIXED("SetToMixed");

	private String value;

	ThresholdChangeOption(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
