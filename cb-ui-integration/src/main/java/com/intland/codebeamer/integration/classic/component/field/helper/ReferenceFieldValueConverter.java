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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;

public class ReferenceFieldValueConverter {

    private static String PATTERN = "(?<type>\\d)-(?<artifactId>\\d+)/(?<version>\\d)"
    		+ "#(?<fourthNumber>\\d+)"
    		+ "#(?<boolean1>false|true)"
    		+ "#(?<boolean2>false|true)"
    		+ "#(?<boolean3>false|true)"
    		+ "#(?<boolean4>false|true)"
    		+ "#(?<boolean5>false|true)"
    		+ "#(?<boolean6>false|true)";

	public ReferenceFieldValue createFromRawValue(String value) {
		Matcher m = Pattern.compile(PATTERN).matcher(value);

		if (!m.find()) {
			throw new IllegalArgumentException("Value(%s) cannot be parsed".formatted(value));
		}
		
		ArtifactType type = ArtifactType.findById(convertToInt(m.group("type")));
		int referenceId = convertToInt(m.group("artifactId"));
		
		return new ReferenceFieldValue(type, referenceId);
	}

	private int convertToInt(String group) {
		return Integer.valueOf(group).intValue();
	}
	
	public static record ReferenceFieldValue(ArtifactType type, int id) {}

}