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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public abstract class AbstractAllowedDefaultValueBuilder<T> {

	private Map<String, List<AllowedDefaultValue>> valueHolder = new HashMap<>();

	private boolean isSimpleValue = false;

	private boolean isTrackerItemValue = false;

	private boolean isMemberValue = false;

	public Map<String, List<AllowedDefaultValue>> build() {
		if ((isSimpleValue && isTrackerItemValue) || (isSimpleValue && isMemberValue) || (isTrackerItemValue && isMemberValue)) {
			throw new IllegalStateException("Cannot mix default value types!");
		}

		return valueHolder;
	}

	protected T addAllowedDefaultValueInStatus(String statusName, String defaultValue) {
		isSimpleValue = true;
		addElement(statusName, new AllowedDefaultValue(defaultValue, ValueType.TEXT));
		return (T) this;
	}

	protected T addAllowedDefaultTrackerItemValueInStatus(String statusName, Project project, String trackerName,
			String trackerItemName) {
		isTrackerItemValue = true;
		addElement(statusName, new AllowedDefaultValue(trackerItemName, ValueType.TRACKER_ITEM_NAME, project, trackerName));
		return (T) this;
	}

	protected T addAllowedDefaultTrackerItemValueInStatus(String statusName, TrackerItemId trackerItemId) {
		isTrackerItemValue = true;
		addElement(statusName, new AllowedDefaultValue(String.valueOf(trackerItemId.id()), ValueType.TRACKER_ITEM_ID));
		return (T) this;
	}

	protected T addAllowedDefaultUserValueInStatus(String statusName, String userName) {
		isMemberValue = true;
		addElement(statusName, new AllowedDefaultValue(userName, ValueType.USER));
		return (T) this;
	}

	protected T addAllowedDefaultRoleValueInStatus(String statusName, String roleName) {
		isMemberValue = true;
		addElement(statusName, new AllowedDefaultValue(roleName, ValueType.ROLE));
		return (T) this;
	}

	protected T addNobodyInStatus(String statusName) {
		isMemberValue = true;
		addElement(statusName, new AllowedDefaultValue("NULL", ValueType.EMPTY));
		return (T) this;
	}

	private boolean addElement(String statusName, AllowedDefaultValue value) {
		return valueHolder.computeIfAbsent(statusName, v -> new ArrayList<>()).add(value);
	}
}
