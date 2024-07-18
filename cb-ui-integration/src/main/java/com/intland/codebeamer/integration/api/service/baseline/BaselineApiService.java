package com.intland.codebeamer.integration.api.service.baseline;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.BaselineApi;
import com.intland.swagger.client.internal.api.BaselinesApi;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.CreateBaselineRequest;
import com.intland.swagger.client.model.ProjectReference;
import com.intland.swagger.client.model.TrackerReference;

public class BaselineApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();

	private final BaselineApi baselineApi;

	private final BaselinesApi baselinesApi;

	public BaselineApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public BaselineApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public BaselineApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public BaselineApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.baselineApi = new BaselineApi(getUserApiClient());
		this.baselinesApi = new BaselinesApi(getUserApiClient());
	}

	public Baseline createProjectBaseline(String baselineName, Project project) {
		try {
			com.intland.swagger.client.model.Baseline baselineModel = baselineApi.createBaseline(
					createCreateBaselineRequest(baselineName, project, null));
			return createBaselineFromBaselineModel(baselineModel);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Baseline createTrackerBaseline(String baselineName, Project project, Tracker tracker) {
		try {
			com.intland.swagger.client.model.Baseline baselineModel = baselineApi.createBaseline(
					createCreateBaselineRequest(baselineName, project, tracker));
			return createBaselineFromBaselineModel(baselineModel);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteBaseline(BaselineId baselineId) {
		try {
			logger.debug("Deleting baseline by id: {}", baselineId);
			baselinesApi.deleteBaseline(Integer.valueOf(baselineId.id()));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private CreateBaselineRequest createCreateBaselineRequest(String baselineName, Project project, Tracker tracker) {
		CreateBaselineRequest createBaselineRequest = new CreateBaselineRequest().name(baselineName);
		ProjectReference projectReference = new ProjectReference();
		projectReference.setId(Integer.valueOf(project.id().id()));
		projectReference.setName(project.name());
		createBaselineRequest.setProject(projectReference);

		if (tracker != null) {
			TrackerReference trackerReference = new TrackerReference();
			trackerReference.setId(Integer.valueOf(tracker.id().id()));
			trackerReference.setName(tracker.name());
			createBaselineRequest.tracker(trackerReference);
		}
		return createBaselineRequest;
	}

	private Baseline createBaselineFromBaselineModel(com.intland.swagger.client.model.Baseline baselineModel) {

		BaselineId baselineId = new BaselineId(Optional.ofNullable(baselineModel.getId())
				.orElseThrow(() -> new IllegalArgumentException("Baseline ID is null")).intValue());
		ProjectId projectId = new ProjectId(Optional.ofNullable(baselineModel.getProject())
				.map(AbstractReference::getId)
				.orElseThrow(() -> new IllegalArgumentException("Project ID is null")).intValue());
		TrackerId trackerId = Optional.ofNullable(baselineModel.getTracker())
				.map(AbstractReference::getId)
				.map(TrackerId::new)
				.orElse(null);
		return new Baseline(baselineId, baselineModel.getName(), projectId, trackerId);
	}

}
