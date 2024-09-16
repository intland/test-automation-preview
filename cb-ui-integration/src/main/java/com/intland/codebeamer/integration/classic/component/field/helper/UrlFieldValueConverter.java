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

package com.intland.codebeamer.integration.classic.component.field.helper;

import org.apache.commons.lang3.StringUtils;

public class UrlFieldValueConverter {

	private static final String OPEN = "[";
	
	private static final String CLOSE = "]";
	
	public UrlFieldValue create(String alias, String url) {
		return new UrlFieldValue(alias, url);
	}
	
	public UrlFieldValue createFromRawValue(String value) {
		if (!StringUtils.startsWith(value, OPEN) || !StringUtils.endsWith(value, CLOSE)) {
			throw new AssertionError("Value must start with %s and end with %s".formatted(OPEN ,CLOSE));
		}
		
		String[] parts = StringUtils.substringBetween(value, OPEN, CLOSE).split("\\|");
		if (parts.length == 1) {
			return new UrlFieldValue(parts[0].trim(), parts[0].trim());
		}
		return new UrlFieldValue(parts[0].trim(), parts[1].trim());
	}
	
	public static record UrlFieldValue(String alias, String url) {}
	
}