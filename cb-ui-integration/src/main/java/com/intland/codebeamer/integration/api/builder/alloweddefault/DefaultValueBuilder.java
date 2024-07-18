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
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class DefaultValueBuilder extends AbstractAllowedDefaultValueBuilder<DefaultValueBuilder> {

	public DefaultValueBuilder addDefaultValueInStatus(String statusName, String defaultValue) {
		return addAllowedDefaultValueInStatus(statusName, defaultValue);
	}

	public DefaultValueBuilder addDefaultTrackerItemValueInStatus(String statusName, Project project, String trackerName,
			String trackerItemName) {
		return addAllowedDefaultTrackerItemValueInStatus(statusName, project, trackerName, trackerItemName);
	}

	public DefaultValueBuilder addDefaultTrackerItemValueInStatus(String statusName, TrackerItemId trackerItemId) {
		return addAllowedDefaultTrackerItemValueInStatus(statusName, trackerItemId);
	}

	public DefaultValueBuilder addDefaultUserValueInStatus(String statusName, String userName) {
		return addAllowedDefaultUserValueInStatus(statusName, userName);
	}

	public DefaultValueBuilder addDefaultRoleValueInStatus(String statusName, String roleName) {
		return addAllowedDefaultRoleValueInStatus(statusName, roleName);
	}

	public DefaultValueBuilder addNobodyInStatus(String statusName) {
		return super.addNobodyInStatus(statusName);
	}
}
