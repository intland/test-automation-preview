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

public enum ImportRuleConditionType {
	NO_CONDITION(0),
	CONTAINS(1),
	CONTAINS_CIS(2),
	NOT_CONTAIN(3),
	NOT_CONTAIN_CIS(4),
	WHOLE_WORD(5),
	WHOLE_WORD_CIS(6),
	HAS_STYLE(7),
	REGEX(101),
	REGEX_CIS(102);

	private final int value;

	ImportRuleConditionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
