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

public class CBXPathUtil {

	private static final String xString = "x";

	private static final String hashString = "#";

	public static String buildPath(String relativePath) {
		return new UriPathBuilder(xString).path(hashString).path(relativePath).build();
	}

	public static String getApplicationUrl(String url) {
		return new UriPathBuilder(url).path(xString).build();
	}
}
