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

public enum DateFormat {
	FORMAT1("MMM dd yyyy"),
	FORMAT2("MMM dd, yy"),
	FORMAT3("MMM dd, yyyy"),
	FORMAT4("dd/MM/yy"),
	FORMAT5("dd/MM/yyyy"),
	FORMAT6("dd MMM yyyy"),
	FORMAT7("yyyy/MM/dd"),
	FORMAT8("EEEE, MMMM d, yyyy"),
	FORMAT9("dd. MMM. yyyy"),
	FORMAT10("dd. MMM. yy"),
	FORMAT11("dd.MM.yyyy"),
	FORMAT12("dd.MM.yy"),
	FORMAT13("MMMM d, yyyy"),
	FORMAT14("dd. MMMM yyyy"),
	FORMAT15("EEE, MMM d, yy"),
	FORMAT16("EEE d/MM yy"),
	FORMAT17("EEE MMMM d, yyyy"),
	FORMAT18("MM-dd"),
	FORMAT19("yy-MM-dd"),
	FORMAT20("yyyy-MM-dd"),
	FORMAT21("yyyy.MM.dd"),
	FORMAT22("dd-MMM-yyyy"),
	FORMAT23("y/MM/dd"),
	FORMAT24("dd-MMM-yy"),
	FORMAT25("MMM dd"),
	FORMAT26("MMM/dd/yyyy"),
	FORMAT27("MM/dd/yyyy"),
	FORMAT28("y-MM-dd"),
	FORMAT29("y.MM.dd"),
	FORMAT30("dd/MM/y"),
	FORMAT31("dd.MM.y"),
	FORMAT32("dd-MMM-y"),
	FORMAT33("MM/dd/y");

	private final String value;

	DateFormat(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
