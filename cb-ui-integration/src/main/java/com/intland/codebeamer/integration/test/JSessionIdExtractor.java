package com.intland.codebeamer.integration.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSessionIdExtractor {

	protected String extract(String cookieString) {
		Pattern pattern = Pattern.compile("JSESSIONID=([^;]+)");
		Matcher matcher = pattern.matcher(cookieString);
		if (matcher.find()) {
			return matcher.group(1);
		}

		throw new IllegalArgumentException("JSESSIONID cannot be extracted from %s".formatted(cookieString));
	}

}
