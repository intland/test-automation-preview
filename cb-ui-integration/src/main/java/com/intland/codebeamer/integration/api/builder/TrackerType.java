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

import java.util.Arrays;
import java.util.Objects;

public enum TrackerType {

	ISSUE("ISSUE", 1),
	BUG("BUG", 2),
	CHANGE_REQUEST("CHANGE", 3),
	REQUIREMENT("REQUIREMENT", 5),
	TASK("TASK", 6),
	WORKLOG("WORKLOG", 8),
	TEST_RUN("TESTRUN", 9),
	USER_STORY("USERSTORY", 10),
	RISK("RISK", 11),
	EPIC("EPIC", 12),
	DOCUMENT("DOCUMENT", 13),
	CONFIG_ITEM("CONFIG_ITEM", 101),
	TEST_CASE("TESTCASE", 102),
	RELEASE("RELEASE", 103),
	COMPONENT("COMPONENT", 105),
	PLATFORM("PLATFORM", 106),
	CONTACT("CONTACT", 107),
	TEST_SET("TESTSET", 108),
	TEST_CONF("TESTCONF", 109),
	RPE_REPORT("RPEREPORT", 110),
	TEAM("TEAM", 150),
	AREA("AREA", 151);

	private String shortName;

	private int technicalId;

	private TrackerType(String shortName, int technicalId) {
		this.shortName = shortName;
		this.technicalId = technicalId;
	}

	public String getShortName() {
		return shortName;
	}

	public int getTechnicalId() {
		return technicalId;
	}

	public static TrackerType fromId(Integer typeId) {
		return fromId(Objects.requireNonNull(typeId).intValue());
	}
	
	public static TrackerType fromId(int typeId) {
		return Arrays.stream(TrackerType.values())
				.filter(type -> type.getTechnicalId() == typeId)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown technical id: %s".formatted(typeId)));
	}
}
