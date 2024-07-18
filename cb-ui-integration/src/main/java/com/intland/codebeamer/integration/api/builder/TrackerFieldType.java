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

/**
 * Constants for TrackerLayoutLabelDto static variable. Represents the field types.
 */
public enum TrackerFieldType {

	TEXT(0),
	INTEGER(1),
	DECIMAL(2),
	DATE(3),
	BOOLEAN(4),
	PRINCIPAL(5),
	CHOICE(6),
	REFERENCE(7),
	LANGUAGE(8),
	COUNTRY(9),
	WIKITEXT(10),
	DURATION(11),
	TABLE(12),
	COLOR(13),
	URL(14);

	private final int typeId;

	TrackerFieldType(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return typeId;
	}
}
