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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums;

public enum NumberFormat {
	EN("en"),
	DE("de");

	private final String value;

	NumberFormat(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
