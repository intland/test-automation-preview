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

public enum FieldType {

	TEXT(0),
	INTEGER(1),
	DECIMAL(2),
	DATE(3),
	BOOL(4),
	MEMBER(5),
	CHOICE(6),
	REFERENCE(7),
	LANGUAGE(8),
	COUNTRY(9),
	WIKITEXT(10),
	DURATION(11),
	TABLE(12),
	COLOR(13),
	WIKILINK(14);

	private int typeId;

	FieldType(int typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeIdAsString() {
		return String.valueOf(typeId);
	}

	public static FieldType findById(int value) {
		return Arrays.stream(FieldType.values())
				.filter(type -> type.getTypeId().intValue() == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"FieldType cannot be found by id: %s".formatted(Integer.valueOf(value))));
	}
}
