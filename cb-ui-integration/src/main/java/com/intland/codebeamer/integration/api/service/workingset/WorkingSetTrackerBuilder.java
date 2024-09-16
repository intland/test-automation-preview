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

package com.intland.codebeamer.integration.api.service.workingset;

import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.swagger.client.model.WorkingSetCreateTracker;

public class WorkingSetTrackerBuilder {

	private final WorkingSetCreateTracker workingSetTracker;

	public WorkingSetTrackerBuilder(Tracker tracker) {
		this.workingSetTracker = new WorkingSetCreateTracker();
		this.workingSetTracker.setType(WorkingSetCreateTracker.TypeEnum.INCLUDED);
		this.workingSetTracker.setTrackerId(Integer.valueOf(tracker.id().id()));
		this.workingSetTracker.setName(tracker.name());
	}

	public WorkingSetTrackerBuilder baseline(BaselineId baselineId) {
		this.workingSetTracker.setBaselineId(Integer.valueOf(baselineId.id()));
		return this;
	}

	public WorkingSetTrackerBuilder baseline(WorkingSetId workingSetId) {
		this.workingSetTracker.setBaselineId(Integer.valueOf(workingSetId.id()));
		return this;
	}

	public WorkingSetTrackerBuilder shared() {
		this.workingSetTracker.setType(WorkingSetCreateTracker.TypeEnum.SHARED);
		return this;
	}

	public WorkingSetCreateTracker build() {
		return workingSetTracker;
	}
}
