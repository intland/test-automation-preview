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

public enum CharsetName {
	DOS_CP858("Cp858"),
	WESTERN_ISO_8859_1("ISO-8859-1"),
	UNICODE_UTF_8("UTF-8"),
	CENTRAL_EUROPEAN_ISO_8859_2("ISO-8859-2"),
	WESTERN_WINDOWS_1252("Windows-1252"),
	TRADITIONAL_CHINESE_BIG5("Big5"),
	SIMPLE_CHINESE_GB2312("GB2312"),
	ENGLISH_US_ASCII("US-ASCII");

	private final String value;

	CharsetName(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return value;
	}
}
