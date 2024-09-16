package com.intland.codebeamer.integration.api.service.tracker;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.builder.TrackerFieldFactory;
import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.api.builder.statetransition.TrackerStateTransitionFactory;
import com.intland.codebeamer.integration.api.builder.trackerview.TrackerView;
import com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewBuilder;
import com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewId;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermissionBuilder;
import com.intland.codebeamer.integration.api.service.tracker.permission.TrackerPermissionManager;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.IntegrationApi;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.internal.api.TrackerConfigApi;
import com.intland.swagger.client.internal.api.TrackerPermissionApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.TrackerBasicInformation;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.ViewGuardResponse;

public class TrackerApiService extends AbstractApiService {

	private static final int FIELD_POSITION_INCREMENTER = 17;

	private final TrackerApi trackerApi;

	private final TrackerConfigApi trackerConfigApi;

	private final TrackerPermissionApi trackerPermissionApi;

	private final IntegrationApi integrationApi;

	private final ProjectApiService projectApiService;

	private final TrackerPermissionManager trackerPermissionManager;

	private final ApiUser apiUser;

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
		this.trackerConfigApi = new TrackerConfigApi(getUserApiClient());
		this.trackerPermissionApi = new TrackerPermissionApi(getUserApiClient());
		this.integrationApi = new IntegrationApi(getUserApiClient());
		this.projectApiService = dataManagerService.getProjectApiService(apiUser);
		this.trackerPermissionManager = new TrackerPermissionManager(trackerPermissionApi);
		this.apiUser = apiUser;
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
		return createEpicTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createEpicTracker(ProjectId projectId, String trackerName) {
		return createEpicTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createEpicTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.EPIC, builder);
	}

	public Tracker createDefaultRequirementTracker(Project project, String trackerName) {
		return createDefaultRequirementTracker(project.id(), trackerName);
	}

	public Tracker createDefaultRequirementTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.REQUIREMENT, projectId, trackerName);
	}

	public TrackerFieldFactory createRequirementTracker(Project project, String trackerName) {
		return createRequirementTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createRequirementTracker(ProjectId projectId, String trackerName) {
		return createRequirementTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createRequirementTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.REQUIREMENT, builder);
	}

	public Tracker createDefaultUserStoryTracker(Project project, String trackerName) {
		return createDefaultUserStoryTracker(project.id(), trackerName);
	}

	public Tracker createDefaultUserStoryTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.USER_STORY, projectId, trackerName);
	}

	public TrackerFieldFactory createUserStoryTracker(Project project, String trackerName) {
		return createUserStoryTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createUserStoryTracker(ProjectId projectId, String trackerName) {
		return createUserStoryTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createUserStoryTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.USER_STORY, builder);
	}

	public Tracker createDefaultRiskTracker(Project project, String trackerName) {
		return createDefaultRiskTracker(project.id(), trackerName);
	}

	public Tracker createDefaultRiskTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.RISK, projectId, trackerName);
	}

	public TrackerFieldFactory createRiskTracker(Project project, String trackerName) {
		return createRiskTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createRiskTracker(ProjectId projectId, String trackerName) {
		return createRiskTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createRiskTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.RISK, builder);
	}

	public Tracker createDefaultTaskTracker(Project project, String trackerName) {
		return createDefaultTaskTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTaskTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TASK, projectId, trackerName);
	}

	public TrackerFieldFactory createTaskTracker(Project project, String trackerName) {
		return createTaskTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTaskTracker(ProjectId projectId, String trackerName) {
		return createTaskTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTaskTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TASK, builder);
	}

	public Tracker createDefaultChangeRequestTracker(Project project, String trackerName) {
		return createDefaultChangeRequestTracker(project.id(), trackerName);
	}

	public Tracker createDefaultChangeRequestTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.CHANGE_REQUEST, projectId, trackerName);
	}

	public TrackerFieldFactory createChangeRequestTracker(Project project, String trackerName) {
		return createChangeRequestTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createChangeRequestTracker(ProjectId projectId, String trackerName) {
		return createChangeRequestTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createChangeRequestTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.CHANGE_REQUEST, builder);
	}

	public Tracker createDefaultBugTracker(Project project, String trackerName) {
		return createDefaultBugTracker(project.id(), trackerName);
	}

	public Tracker createDefaultBugTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.BUG, projectId, trackerName);
	}

	public TrackerFieldFactory createBugTracker(Project project, String trackerName) {
		return createBugTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createBugTracker(ProjectId projectId, String trackerName) {
		return createBugTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createBugTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.BUG, builder);
	}

	public Tracker createDefaultReleaseTracker(Project project, String trackerName) {
		return createDefaultReleaseTracker(project.id(), trackerName);
	}

	public Tracker createDefaultReleaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.RELEASE, projectId, trackerName);
	}

	public TrackerFieldFactory createReleaseTracker(Project project, String trackerName) {
		return createReleaseTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createReleaseTracker(ProjectId projectId, String trackerName) {
		return createReleaseTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createReleaseTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.RELEASE, builder);
	}

	public Tracker createDefaultTestSetTracker(Project project, String trackerName) {
		return createDefaultTestSetTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestSetTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_SET, projectId, trackerName);
	}

	public TrackerFieldFactory createTestSetTracker(Project project, String trackerName) {
		return createTestSetTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestSetTracker(ProjectId projectId, String trackerName) {
		return createTestSetTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestSetTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_SET, builder);
	}

	public Tracker createDefaultTestCaseTracker(Project project, String trackerName) {
		return createDefaultTestCaseTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestCaseTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_CASE, projectId, trackerName);
	}

	public TrackerFieldFactory createTestCaseTracker(Project project, String trackerName) {
		return createTestCaseTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestCaseTracker(ProjectId projectId, String trackerName) {
		return createTestCaseTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestCaseTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_CASE, builder);
	}

	public Tracker createDefaultTestConfigurationTracker(Project project, String trackerName) {
		return createDefaultTestConfigurationTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTestConfigurationTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_CONF, projectId, trackerName);
	}

	public TrackerFieldFactory createTestConfigurationTracker(Project project, String trackerName) {
		return createTestConfigurationTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestConfigurationTracker(ProjectId projectId, String trackerName) {
		return createTestConfigurationTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestConfigurationTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_CONF, builder);
	}

	public Tracker createDefaultTeamTracker(Project project, String trackerName) {
		return createDefaultTeamTracker(project.id(), trackerName);
	}

	public Tracker createDefaultTeamTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEAM, projectId, trackerName);
	}

	public TrackerFieldFactory createTeamTracker(ProjectId projectId, String trackerName) {
		return createTeamTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTeamTracker(Project project, String trackerName) {
		return createTeamTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTeamTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEAM, builder);
	}

	public Tracker createDefaultAreaTracker(Project project, String trackerName) {
		return createDefaultAreaTracker(project.id(), trackerName);
	}

	public Tracker createDefaultAreaTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.AREA, projectId, trackerName);
	}

	public TrackerFieldFactory createAreaTracker(ProjectId projectId, String trackerName) {
		return createAreaTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createAreaTracker(Project project, String trackerName) {
		return createAreaTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createAreaTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.AREA, builder);
	}

	public Tracker createDefaultIssueTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.ISSUE, projectId, trackerName);
	}

	public TrackerFieldFactory createIssueTracker(ProjectId projectId, String trackerName) {
		return createIssueTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createIssueTracker(Project project, String trackerName) {
		return createIssueTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createIssueTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.ISSUE, builder);
	}

	public Tracker createDefaultWorkLogTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.WORKLOG, projectId, trackerName);
	}

	public TrackerFieldFactory createWorkLogTracker(ProjectId projectId, String trackerName) {
		return createWorkLogTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createWorkLogTracker(Project project, String trackerName) {
		return createWorkLogTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createWorkLogTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.WORKLOG, builder);
	}

	public Tracker createDefaultTestRunTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.TEST_RUN, projectId, trackerName);
	}

	public TrackerFieldFactory createTestRunTracker(ProjectId projectId, String trackerName) {
		return createTestRunTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestRunTracker(Project project, String trackerName) {
		return createTestRunTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createTestRunTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.TEST_RUN, builder);
	}

	public Tracker createDefaultDocumentTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.DOCUMENT, projectId, trackerName);
	}

	public TrackerFieldFactory createDocumentTracker(ProjectId projectId, String trackerName) {
		return createDocumentTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createDocumentTracker(Project project, String trackerName) {
		return createDocumentTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createDocumentTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.DOCUMENT, builder);
	}

	public Tracker createDefaultConfigItemTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.CONFIG_ITEM, projectId, trackerName);
	}

	public TrackerFieldFactory createConfigItemTracker(ProjectId projectId, String trackerName) {
		return createConfigItemTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createConfigItemTracker(Project project, String trackerName) {
		return createConfigItemTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createConfigItemTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.CONFIG_ITEM, builder);
	}

	public Tracker createDefaultComponentTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.COMPONENT, projectId, trackerName);
	}

	public TrackerFieldFactory createComponentTracker(ProjectId projectId, String trackerName) {
		return createComponentTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createComponentTracker(Project project, String trackerName) {
		return createComponentTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createComponentTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.COMPONENT, builder);
	}

	public Tracker createDefaultPlatformTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.PLATFORM, projectId, trackerName);
	}

	public TrackerFieldFactory createPlatformTracker(ProjectId projectId, String trackerName) {
		return createPlatformTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createPlatformTracker(Project project, String trackerName) {
		return createPlatformTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createPlatformTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.PLATFORM, builder);
	}

	public Tracker createDefaultContactTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.CONTACT, projectId, trackerName);
	}

	public TrackerFieldFactory createContactTracker(ProjectId projectId, String trackerName) {
		return createContactTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createContactTracker(Project project, String trackerName) {
		return createContactTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createContactTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.CONTACT, builder);
	}

	public Tracker createDefaultRpeReportTracker(ProjectId projectId, String trackerName) {
		return createTrackerInternal(TrackerType.RPE_REPORT, projectId, trackerName);
	}

	public TrackerFieldFactory createRpeReportTracker(ProjectId projectId, String trackerName) {
		return createRpeReportTracker(projectId, trackerName, Function.identity());
	}

	public TrackerFieldFactory createRpeReportTracker(Project project, String trackerName) {
		return createRpeReportTracker(project.id(), trackerName, Function.identity());
	}

	public TrackerFieldFactory createRpeReportTracker(ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(projectId, trackerName, TrackerType.RPE_REPORT, builder);
	}

	public TrackerConfiguration addFieldsToTracker(TrackerId trackerId, List<TrackerField> newFields,
			List<TrackerField> changedFields) {
		try {
			TrackerConfiguration currentConfig = trackerApi.getTrackerConfiguration(trackerId.id());

			updateExistingFields(currentConfig, changedFields);
			addNewFields(currentConfig, newFields);

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

	public TrackerStateTransitionFactory updateStateTransitions(Tracker tracker) {
		return new TrackerStateTransitionFactory(tracker, trackerConfigApi, trackerApi);
	}

	public TrackerView createTrackerView(TrackerId trackerId, Function<TrackerViewBuilder, TrackerViewBuilder> permBuilder) {
		try {
			ViewGuardResponse viewGuardResponse = trackerConfigApi.setViewGuardFilter(trackerId.id(), permBuilder.apply(new TrackerViewBuilder(trackerApi, trackerId)).build());
			return new TrackerView(new TrackerViewId(viewGuardResponse.getId().intValue()), viewGuardResponse.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Only supports adding new fields to a tracker.
	 *
	 * @param project which contains the tracker
	 * @param trackerName to add fields to
	 * @return field factory to define new fields
	 */
	public TrackerFieldFactory updateTracker(Project project, String trackerName) {
		Tracker tracker = projectApiService.findTrackerByName(project, trackerName);

		return createTrackerFieldFactory(tracker, project.id());
	}

	private Tracker createTrackerInternal(TrackerType type, ProjectId projectId, String trackerName) {
		return createTrackerInternal(type, projectId, trackerName, Function.identity());
	}

	private Tracker createTrackerInternal(TrackerType type, ProjectId projectId, String trackerName,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		try {
			Integer templateId = getTemplateId(builder);
			IdNamed tracker = integrationApi.createDefaultTrackerConfig(type.getShortName(), projectId.id(),
					trackerName, templateId);

			updateGeneralTrackerSettings(builder, tracker);

			return new Tracker(new TrackerId(tracker.getId()), tracker.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Integer getTemplateId(Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return builder.apply(new TrackerGeneralSettingsBuilder()).build().getTemplateId();
	}

	private void updateGeneralTrackerSettings(Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder,
			IdNamed tracker) throws ApiException {
		TrackerConfiguration trackerConfig = trackerApi.getTrackerConfiguration(tracker.getId());

		TrackerBasicInformation modifiedTrackerSettings = builder.apply(
						new TrackerGeneralSettingsBuilder(trackerConfig.getBasicInformation()))
				.build();

		trackerConfig.setBasicInformation(modifiedTrackerSettings);

		trackerApi.postTrackerConfiguration(trackerConfig);
	}

	private TrackerFieldFactory createTrackerFieldFactory(Tracker tracker, ProjectId projectId) {
		return new TrackerFieldFactory(tracker, projectId, dataManagerService, trackerApi, apiUser);
	}

	private TrackerFieldFactory createTrackerFieldFactory(ProjectId projectId, String trackerName, TrackerType trackerType,
			Function<TrackerGeneralSettingsBuilder, TrackerGeneralSettingsBuilder> builder) {
		return createTrackerFieldFactory(createTrackerInternal(trackerType, projectId, trackerName, builder), projectId);
	}

	private List<TrackerField> setFieldPositions(TrackerConfiguration currentConfig, List<TrackerField> fields) {
		int maxPosition = getMaxFieldPosition(currentConfig);

		for (TrackerField field : fields) {
			if (field.getPosition() == null) {
				maxPosition += FIELD_POSITION_INCREMENTER;
				field.setPosition(maxPosition);
			}
		}

		return fields;
	}

	private int getMaxFieldPosition(TrackerConfiguration currentConfig) {
		return currentConfig.getFields().stream()
				.map(f -> f.getPosition())
				.filter(Objects::nonNull)
				.max(Integer::compare)
				.orElseGet(() -> 10000).intValue();
	}

	private void addNewFields(TrackerConfiguration currentConfig, List<TrackerField> newFields) {
		currentConfig.getFields().addAll(setFieldPositions(currentConfig, newFields));
	}

	private void updateExistingFields(TrackerConfiguration currentConfig, List<TrackerField> changedFields) {
		ListIterator<TrackerField> currentFieldsIt = currentConfig.getFields().listIterator();

		while (currentFieldsIt.hasNext()) {
			TrackerField currentField = currentFieldsIt.next();

			changedFields.stream()
					.filter(cf -> cf.getLabel().equals(currentField.getLabel()))
					.findFirst()
					.ifPresent(cf -> currentFieldsIt.set(cf));
		}
	}
}
