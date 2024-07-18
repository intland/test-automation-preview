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

package com.intland.codebeamer.integration.api.builder.reference;

import com.intland.codebeamer.integration.api.service.project.Project;

public class TrackerReferenceFilterBuilder extends AbstractReferenceBuilder {

	public TrackerReferenceFilterBuilder addTrackerFilter(Project project, String trackerName) {
		filters.add(new ReferenceFilter(project, trackerName, null, null));

		return this;
	}

	public TrackerReferenceFilterBuilder addTrackerFilterWithStatus(Project project, String trackerName, StatusMeaning status) {
		filters.add(new ReferenceFilter(project, trackerName, null, status));

		return this;
	}
}
