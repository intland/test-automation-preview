package com.intland.codebeamer.integration.api.service.tracker;

import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.builder.TrackerFieldFactory;
import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermissionBuilder;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermissionManager;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.IntegrationApi;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.internal.api.TrackerPermissionApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerField;

public class TrackerApiService extends AbstractApiService {

	private final TrackerApi trackerApi;

	private final TrackerPermissionApi trackerPermissionApi;

	private final IntegrationApi integrationApi;

	private final RoleApiService roleApiService;

	private final ProjectApiService projectApiService;

	private final TrackerFieldApiService trackerFieldApiService;

	private final TrackerItemApiService trackerItemApiService;

	private final UserApiService userApiService;

	private final TrackerPermissionManager trackerPermissionManager;

	public TrackerApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public TrackerApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public TrackerApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public TrackerApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.trackerApi = new TrackerApi(getUserApiClient());
		this.trackerPermissionApi = new TrackerPermissionApi(getUserApiClient());
		this.integrationApi = new IntegrationApi(getUserApiClient());
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
		this.projectApiService = dataManagerService.getProjectApiService(apiUser);
		this.trackerFieldApiService = dataManagerService.getTrackerFieldApiService(apiUser);
		this.trackerItemApiService = dataManagerService.getTrackerItemApiService(apiUser);
		this.userApiService = dataManagerService.getUserApiService(apiUser);
		this.trackerPermissionManager = new TrackerPermissionManager(trackerPermissionApi);
	}
	
	public void deleteTracker(TrackerId trackerId) {
		try {
			trackerApi.deleteTracker(trackerId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Tracker createDefaultEpicTracker(Project project, String trackerName) {
		return createDefaultEpicTracker(project.id(), trackerName);
	}

	public Tracker createDefaultEpicTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.EPIC, projectId, trackerName);
	}

	public TrackerFieldFactory createEpicTracker(Project project, String trackerName) {
		return createEpicTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createEpicTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.EPIC);
	}

	public Tracker createDefaultRequirementTracker(Project project, String trackerName) {
		return createDefaultRequirementTracker(project.id(), trackerName);
	}

	public Tracker createDefaultRequirementTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.REQUIREMENT, projectId, trackerName);
	}

	public TrackerFieldFactory createRequirementTracker(Project project, String trackerName) {
		return createRequirementTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createRequirementTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.REQUIREMENT);
	}

	public Tracker createDefaultUserStoryTracker(Project project, String trackerName) {
		return createDefaultUserStoryTracker(project.id(), trackerName);
	}

	public Tracker createDefaultUserStoryTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.USER_STORY, projectId, trackerName);
	}

	public TrackerFieldFactory createUserStoryTracker(Project project, String trackerName) {
		return createUserStoryTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createUserStoryTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.USER_STORY);
	}

	public Tracker createDefaultRiskTracker(Project project, String trackerName) {
		return createDefaultRiskTracker(project.id(), trackerName);
	}

	public Tracker createDefaultRiskTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.RISK, projectId, trackerName);
	}

	public TrackerFieldFactory createRiskTracker(Project project, String trackerName) {
		return createRiskTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createRiskTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.RISK);
	}

	public Tracker createDefaultTaskTracker(Project project, String trackerName) {
		return createDefaultTaskTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTaskTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TASK, projectId, trackerName);
	}

	public TrackerFieldFactory createTaskTracker(Project project, String trackerName) {
		return createTaskTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createTaskTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TASK);
	}

	public Tracker createDefaultChangeRequestTracker(Project project, String trackerName) {
		return createDefaultChangeRequestTracker(project.id(), trackerName);
	}

	public Tracker createDefaultChangeRequestTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.CHANGE_REQUEST, projectId, trackerName);
	}

	public TrackerFieldFactory createChangeRequestTracker(Project project, String trackerName) {
		return createChangeRequestTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createChangeRequestTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.CHANGE_REQUEST);
	}

	public Tracker createDefaultBugTracker(Project project, String trackerName) {
		return createDefaultBugTracker(project.id(), trackerName);
	}

	public Tracker createDefaultBugTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.BUG, projectId, trackerName);
	}

	public TrackerFieldFactory createBugTracker(Project project, String trackerName) {
		return createBugTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createBugTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.BUG);
	}

	public Tracker createDefaultReleaseTracker(Project project, String trackerName) {
		return createDefaultReleaseTracker(project.id(), trackerName);
	}

	public Tracker createDefaultReleaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.RELEASE, projectId, trackerName);
	}

	public TrackerFieldFactory createReleaseTracker(Project project, String trackerName) {
		return createReleaseTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createReleaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.RELEASE);
	}

	public Tracker createDefaultTestSetTracker(Project project, String trackerName) {
		return createDefaultTestSetTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestSetTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_SET, projectId, trackerName);
	}

	public TrackerFieldFactory createTestSetTracker(Project project, String trackerName) {
		return createTestSetTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createTestSetTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_SET);
	}

	public Tracker createDefaultTestCaseTracker(Project project, String trackerName) {
		return createDefaultTestCaseTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestCaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_CASE, projectId, trackerName);
	}

	public TrackerFieldFactory createTestCaseTracker(Project project, String trackerName) {
		return createTestCaseTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createTestCaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_CASE);
	}

	public Tracker createDefaultTestConfigurationTracker(Project project, String trackerName) {
		return createDefaultTestConfigurationTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestConfigurationTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_CONF, projectId, trackerName);
	}

	public TrackerFieldFactory createTestConfigurationTracker(Project project, String trackerName) {
		return createTestConfigurationTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createTestConfigurationTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_CONF);
	}

	public Tracker createDefaultTeamTracker(Project project, String trackerName) {
		return createDefaultTeamTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTeamTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEAM, projectId, trackerName);
	}

	public TrackerFieldFactory createTeamTracker(Project project, String trackerName) {
		return createTeamTracker(project.id(), trackerName);
	}

	public TrackerFieldFactory createTeamTracker(ProjectId projectId, String trackerName) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEAM);
	}

	public TrackerConfiguration addFieldsToTracker(TrackerId trackerId, List<TrackerField> fields) {
		try {
			TrackerConfiguration currentConfig = trackerApi.getTrackerConfiguration(trackerId.id());
			currentConfig.getFields().addAll(fields);
			return trackerApi.postTrackerConfiguration(currentConfig);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void updateTrackerPermissions(Tracker tracker, Function<TrackerPermissionBuilder, TrackerPermissionBuilder> permBuilder) {
		updateTrackerPermissions(tracker.id(), permBuilder);
	}

	public void updateTrackerPermissions(TrackerId trackerId, Function<TrackerPermissionBuilder, TrackerPermissionBuilder> permBuilder) {
		trackerPermissionManager.updateTrackerPermissions(trackerId, permBuilder);
	}

	private Tracker createTrackerInternal(TrackerType type, ProjectId projectId, String trackerName) {
		try {
			IdNamed tracker = integrationApi.createDefaultTrackerConfig(type.getShortName(), projectId.id(), trackerName);

			return new Tracker(new TrackerId(tracker.getId()), tracker.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private TrackerFieldFactory createTrackerFieldFactory(ProjectId projectId, String trackerName, TrackerType trackerType) {
		return new TrackerFieldFactory(createTrackerInternal(trackerType, projectId, trackerName), trackerFieldApiService, this,
				trackerItemApiService, userApiService, roleApiService, projectApiService);
	}

}