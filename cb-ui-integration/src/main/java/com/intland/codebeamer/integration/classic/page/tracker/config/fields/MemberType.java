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

package com.intland.codebeamer.integration.classic.page.tracker.config.fields;

import java.util.Arrays;

public enum MemberType {

	USERS(2),
	GROUPS(4),
	ROLES(8);

	private int typeId;

	MemberType(int typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeIdAsString() {
		return String.valueOf(typeId);
	}

	public static MemberType findById(int value) {
		return Arrays.stream(MemberType.values())
				.filter(type -> type.getTypeId().intValue() == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"MemberType cannot be found by id: %s".formatted(Integer.valueOf(value))));
	}
}
