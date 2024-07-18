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

package com.intland.codebeamer.integration.api.builder.alloweddefault;

public enum ValueType {

	EMPTY(0),
	TEXT(0),
	TRACKER_ITEM_NAME(9),
	TRACKER_ITEM_ID(9),
	USER(1),
	ROLE(13);

	private final int referenceId;

	ValueType(int referenceId) {
		this.referenceId = referenceId;
	}

	public int getReferenceId() {
		return referenceId;
	}
}
