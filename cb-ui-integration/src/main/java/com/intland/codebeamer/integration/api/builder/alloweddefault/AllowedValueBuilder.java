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

public class AllowedValueBuilder extends AbstractAllowedDefaultValueBuilder<AllowedValueBuilder> {

	public AllowedValueBuilder addAllowedValueInStatus(String statusName, String defaultValue) {
		return addAllowedDefaultValueInStatus(statusName, defaultValue);
	}

	public AllowedValueBuilder addAllowedTrackerItemValueInStatus(String statusName, Project project, String trackerName,
			String trackerItemName) {
		return addAllowedDefaultTrackerItemValueInStatus(statusName, project, trackerName, trackerItemName);
	}

	public AllowedValueBuilder addAllowedTrackerItemValueInStatus(String statusName, TrackerItemId trackerItemId) {
		return addAllowedDefaultTrackerItemValueInStatus(statusName, trackerItemId);
	}

	public AllowedValueBuilder addAllowedUserValueInStatus(String statusName, String userName) {
		return addAllowedDefaultUserValueInStatus(statusName, userName);
	}

	public AllowedValueBuilder addAllowedRoleValueInStatus(String statusName, String roleName) {
		return addAllowedDefaultRoleValueInStatus(statusName, roleName);
	}

	public AllowedValueBuilder addNobodyInStatus(String statusName) {
		return super.addNobodyInStatus(statusName);
	}
}
