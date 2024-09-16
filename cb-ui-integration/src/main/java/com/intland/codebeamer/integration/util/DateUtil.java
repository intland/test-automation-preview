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

package com.intland.codebeamer.integration.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	private static final String ENGLISH_DATE_FORMAT_PATTERN = "MMM dd yyyy";

	public static String getFormattedDate(String datePattern) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
		return dateTime.format(formatter);
	}

	public static String getEnglishFormattedDate() {
		return getFormattedDate(ENGLISH_DATE_FORMAT_PATTERN);
	}

}