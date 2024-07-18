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

package com.intland.codebeamer.integration.api.builder.alloweddefault;

import com.intland.codebeamer.integration.api.service.project.Project;

public record AllowedDefaultValue(String value, ValueType valueType, Project project, String trackerName) {

	public AllowedDefaultValue(String value, ValueType valueType) {
		this(value, valueType, null, null);
	}
}
