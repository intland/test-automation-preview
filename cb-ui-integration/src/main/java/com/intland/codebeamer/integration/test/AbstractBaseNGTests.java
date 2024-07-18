package com.intland.codebeamer.integration.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.CodebeamerApiClientFactory;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.codebeamer.integration.configuration.Configuration;
import com.intland.swagger.client.internal.ApiClient;
import com.intland.swagger.client.internal.api.ProjectApi;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.internal.api.TrackerPermissionApi;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.BaselineDetails;
import com.intland.swagger.client.model.MemberReferenceSearchResult;
import com.intland.swagger.client.model.PaginatedProjectRole;
import com.intland.swagger.client.model.ProjectBaselineResponse;
import com.intland.swagger.client.model.ProjectMemberPermissions;
import com.intland.swagger.client.model.ProjectRoles;
import com.intland.swagger.client.model.ReferenceSearchResult;
import com.intland.swagger.client.model.RoleReference;
import com.intland.swagger.client.model.RoleWithPermissions;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerItem;

public abstract class AbstractBaseNGTests {

	private static final String CB_UI_INTEGRATION_CONFIGURATION = "CB_UI_INTEGRATION_CONFIGURATION";

	private static final Logger logger = LogManager.getLogger();

	private Configuration configuration;

	private TrackerItemApi trackerItemApi;

	private ProjectApi projectApi;

	private TrackerApi trackerApi;

	private TrackerPermissionApi trackerPermissionApi;

	@BeforeClass(alwaysRun = true)
	public void loadConfiguration() throws Exception {
		this.configuration = Objects.requireNonNull(loadConfigurationFrom(getConfigurationName()), "Configuration cannot be null");
		this.trackerItemApi = new TrackerItemApi(createApiClient(getApplicationConfiguration()));
		this.projectApi = new ProjectApi(createApiClient(getApplicationConfiguration()));
		this.trackerApi = new TrackerApi(createApiClient(getApplicationConfiguration()));
		this.trackerPermissionApi = new TrackerPermissionApi(createApiClient(getApplicationConfiguration()));
	}

	protected Configuration getConfiguration() {
		return this.configuration;
	}
	
	protected ApplicationConfiguration getApplicationConfiguration() {
		return this.configuration.applicationConfiguration();
	}
	
	protected String getApplicationUrl() {
		return this.configuration.applicationConfiguration().url();
	}
	
	protected String getApplicationApiUrl() {
		return this.configuration.applicationConfiguration().url();
	}
	
	protected String getRandomText(String text) {
		return "%s%s".formatted(text, System.currentTimeMillis());
	}
	
	protected TrackerItem getTrackerItem(TrackerItemId trackerItemId) {
		try {
			return trackerItemApi.getTrackerItem(Integer.valueOf(trackerItemId.id()), null, null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<AbstractReference> getProjectMembers(Integer projectId) {
		try {
			return Optional.ofNullable(projectApi.getMembersOfProject(projectId, null, null))
					.map(MemberReferenceSearchResult::getMembers)
					.orElse(List.of());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<RoleReference> getProjectRolesOfMember(Integer projectId, Integer userId) {
		try {
			return Optional.ofNullable(projectApi.getProjectRolesOfMember(projectId, userId))
					.map(ProjectMemberPermissions::getRoles)
					.orElse(List.of());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<ProjectRoles> getProjectRoles(Integer projectId) {
		try {
			PaginatedProjectRole paginatedProjectRole = projectApi.getRoles1(projectId, null, Boolean.TRUE,
					null, null, null, null);
			return Optional.ofNullable(paginatedProjectRole)
					.map(PaginatedProjectRole::getRoles)
					.orElse(List.of());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
  
	protected TrackerConfiguration getTracker(TrackerId trackerId) {
		try {
			return trackerApi.getTrackerConfiguration(Integer.valueOf(trackerId.id()));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<RoleWithPermissions> getTrackerPermissions(Tracker tracker) {
		return getTrackerPermissions(tracker.id());
	}

	protected List<RoleWithPermissions> getTrackerPermissions(TrackerId trackerId) {
		try	{
			return trackerPermissionApi.getTrackerPermissionsWithRoles(trackerId.id(), null, null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<BaselineDetails> getProjectBaselines(ProjectId projectId) {
		try {
			return Optional.ofNullable(projectApi.getBaselines(Integer.valueOf(projectId.id()), Boolean.TRUE))
					.map(ProjectBaselineResponse::getBaselines)
					.orElse(List.of());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected List<AbstractReference> getTrackerBaselines(TrackerId trackerId) {
		try {
			return Optional.ofNullable(trackerApi.getTrackerBaselines(Integer.valueOf(trackerId.id())))
					.map(ReferenceSearchResult::getReferences)
					.orElse(List.of());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private ApiClient createApiClient(ApplicationConfiguration applicationConfiguration) {
		return createApiClient(applicationConfiguration.getApiUser());
	}
	
	private ApiClient createApiClient(ApiUser apiUser) {
		return new CodebeamerApiClientFactory().getApiClient(getApplicationApiUrl(), apiUser.username(), apiUser.password());
	}
	
	private String getConfigurationName() {
		String configurationName = System.getenv().getOrDefault(CB_UI_INTEGRATION_CONFIGURATION, System.getProperty(CB_UI_INTEGRATION_CONFIGURATION, "/configuration.yml"));
		logger.debug("Configuration file is {}", configurationName);
		return configurationName;
	}
	
	private Configuration loadConfigurationFrom(String configurationFileName) throws IOException, StreamReadException, DatabindException {
		return readValue(getContentFromFile(configurationFileName)
				.orElseGet(() -> getContentFromResource(configurationFileName).orElse(null)));
	}
	
	private Optional<String> getContentFromResource(String configurationFileName) {
		try {
			return Optional.of(IOUtils.toString(this.getClass().getResource(configurationFileName), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	private Optional<String> getContentFromFile(String configurationFileName) {
		try {
			return Optional.of(FileUtils.readFileToString(new File(configurationFileName), StandardCharsets.UTF_8));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	private Configuration readValue(String content) throws IOException, StreamReadException, DatabindException {
		logger.debug("Configuration file content is: \n{}", content);
		return new ObjectMapper(new YAMLFactory()).readValue(content, Configuration.class);
	}
	
}