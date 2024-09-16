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

import java.util.ArrayList;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.swagger.client.model.WorkingSetCreate;

public class WorkingSetBuilder {

	private final WorkingSetCreate workingSetCreate;

	public WorkingSetBuilder(String name) {
		this.workingSetCreate = new WorkingSetCreate();
		this.workingSetCreate.setName(name);
	}

	public WorkingSetBuilder parentWorkingSet(WorkingSetId parentWorkingSetId) {
		this.workingSetCreate.setParentWorkingSetId(Integer.valueOf(parentWorkingSetId.id()));
		return this;
	}

	public WorkingSetBuilder color(HtmlColor color) {
		this.workingSetCreate.setColor(color.getHexCode());
		return this;
	}

	public WorkingSetBuilder postfix(String postfix) {
		this.workingSetCreate.setPostfix(postfix);
		return this;
	}

	public WorkingSetBuilder description(String description) {
		this.workingSetCreate.setDescription(description);
		return this;
	}

	public WorkingSetBuilder baseline(BaselineId baselineId) {
		this.workingSetCreate.setBaselineId(Integer.valueOf(baselineId.id()));
		return this;
	}

	public WorkingSetBuilder baseline(WorkingSetId workingSetId) {
		this.workingSetCreate.setBaselineId(Integer.valueOf(workingSetId.id()));
		return this;
	}

	public WorkingSetBuilder addTracker(Tracker tracker,
			Function<WorkingSetTrackerBuilder, WorkingSetTrackerBuilder> builder) {
		if (workingSetCreate.getTrackers() == null) {
			workingSetCreate.setTrackers(new ArrayList<>());
		}

		this.workingSetCreate.getTrackers().add(builder.apply(new WorkingSetTrackerBuilder(tracker)).build());
		return this;
	}

	public WorkingSetBuilder addTracker(Tracker tracker) {
		return this.addTracker(tracker, Function.identity());
	}

	public WorkingSetCreate build() {
		return workingSetCreate;
	}
}
