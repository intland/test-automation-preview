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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.swagger.client.model.WorkingSetTrackerMerge;

public class WorkingSetTrackerMergeBuilder {

	private final WorkingSetTrackerMerge mergeModel;

	public WorkingSetTrackerMergeBuilder(TrackerId source, TrackerId target) {
		this.mergeModel = new WorkingSetTrackerMerge();
		this.mergeModel.setBranchId(Integer.valueOf(source.id()));
		this.mergeModel.setTargetBranchId(Integer.valueOf(target.id()));
	}

	public WorkingSetTrackerMergeBuilder addUpdatedFieldAndMarkAsMerged(TrackerItemId itemId, int... fieldIds) {
		this.addUpdatedFields(itemId, fieldIds);
		return this.markAsMerged(itemId);
	}

	public WorkingSetTrackerMergeBuilder addUpdatedFields(TrackerItemId itemId, int... fieldIds) {
		if (mergeModel.getUpdatedFields() == null) {
			mergeModel.setUpdatedFields(new HashMap<>());
		}

		Set<Integer> fieldIdSet = Arrays.stream(fieldIds).boxed().collect(Collectors.toSet());
		mergeModel.getUpdatedFields().put(String.valueOf(itemId.id()), fieldIdSet);
		return this;
	}

	public WorkingSetTrackerMergeBuilder markAsMerged(TrackerItemId... itemIds) {
		for (TrackerItemId itemId : itemIds) {
			mergeModel.addMarkAsMergedItem(Integer.valueOf(itemId.id()));
		}
		return this;
	}

	public WorkingSetTrackerMergeBuilder markAllAsMerged() {
		mergeModel.markedAllAsMerged(Boolean.TRUE);
		return this;
	}

	public WorkingSetTrackerMergeBuilder addDeleteOnTarget(TrackerItemId... itemIds) {
		for (TrackerItemId itemId : itemIds) {
			mergeModel.addDeleteOnTargetItem(Integer.valueOf(itemId.id()));
		}
		return this;
	}

	public WorkingSetTrackerMergeBuilder addCopyToTarget(TrackerItemId... itemIds) {
		for (TrackerItemId itemId : itemIds) {
			mergeModel.addCopyToTargetItem(Integer.valueOf(itemId.id()));
		}
		return this;
	}

	public WorkingSetTrackerMerge build() {
		return mergeModel;
	}
}
