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

package com.intland.codebeamer.integration.api.builder;

public enum TrackerType {

	BUG("BUG", 2),
	CHANGE_REQUEST("CHANGE", 3),
	REQUIREMENT("REQUIREMENT", 5),
	TASK("TASK", 6),
	USER_STORY("USERSTORY", 10),
	RISK("RISK", 11),
	EPIC("EPIC", 12),
	TEST_CASE("TESTCASE", 102),
	RELEASE("RELEASE", 103),
	TEST_SET("TESTSET", 108),
	TEST_CONF("TESTCONF", 109),
	TEAM("TEAM", 150);

	private String shortName;

	private Integer technicalId;

	private TrackerType(String shortName, Integer technicalId) {
		this.shortName = shortName;
		this.technicalId = technicalId;
	}

	public String getShortName() {
		return shortName;
	}

	public Integer getTechnicalId() {
		return technicalId;
	}
}
