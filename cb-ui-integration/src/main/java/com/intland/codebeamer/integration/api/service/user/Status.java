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

package com.intland.codebeamer.integration.api.service.user;

public enum Status {
	ACTIVATED("ACTIVATED"),

	DISABLED("DISABLED"),

	IN_ACTIVATION("INACTIVATION");

	private String value;

	Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
