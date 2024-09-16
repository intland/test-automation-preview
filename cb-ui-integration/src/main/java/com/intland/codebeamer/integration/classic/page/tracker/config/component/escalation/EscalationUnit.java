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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation;

public enum EscalationUnit {

	SECOND("3"),
	MINUTE("4"),
	HOUR("5"),
	DAY("6");

	private final String value;

	EscalationUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
