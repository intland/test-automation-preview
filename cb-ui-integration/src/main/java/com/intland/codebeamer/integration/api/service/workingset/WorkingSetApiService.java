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

import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.WorkingSetApi;
import com.intland.swagger.client.model.WorkingSetCreateResponse;

public class WorkingSetApiService extends AbstractApiService {

	private final WorkingSetApi workingSetApi;

	public WorkingSetApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public WorkingSetApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public WorkingSetApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public WorkingSetApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		workingSetApi = new WorkingSetApi(getUserApiClient());
	}

	public WorkingSetId createWorkingSet(String name, ProjectId projectId,
			Function<WorkingSetBuilder, WorkingSetBuilder> builder) {
		try {
			WorkingSetCreateResponse responseModel = workingSetApi.createWorkingSet(Integer.valueOf(projectId.id()),
					builder.apply(new WorkingSetBuilder(name)).build());
			return new WorkingSetId(responseModel.getId().intValue());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void mergeTrackers(TrackerId source, TrackerId target,
			Function<WorkingSetTrackerMergeBuilder, WorkingSetTrackerMergeBuilder> builder) {
		try {
			workingSetApi.mergeWorkingSetTrackers(builder.apply(new WorkingSetTrackerMergeBuilder(source, target)).build());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public List<Tracker> findTrackersInWorkingSet(WorkingSetId workingSetId) {
		try {
			return workingSetApi.getWorkingSetTrackers(Integer.valueOf(workingSetId.id()), Boolean.FALSE)
					.stream()
					.map(wsTracker -> new Tracker(new TrackerId(wsTracker.getId().intValue()), wsTracker.getName()))
					.toList();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Tracker findTrackerInWorkingSetByName(WorkingSetId workingSetId, String name) {
		try {
			return this.findTrackersInWorkingSet(workingSetId)
					.stream()
					.filter(tracker -> tracker.name().equals(name))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("Tracker {%s} does not exist in Working-Set {%d}"
							.formatted(name, Integer.valueOf(workingSetId.id()))));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
