package com.intland.codebeamer.integration.api.service.project;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.role.RoleId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.ProjectApi;
import com.intland.swagger.client.internal.api.UtilityApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.ProjectAddRoles;
import com.intland.swagger.client.model.ProjectMemberAndRole;
import com.intland.swagger.client.model.ProjectTemplateData;
import com.intland.swagger.client.model.ProjectTemplateData.KindOfNewProjectEnum;

public class ProjectApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();

	private static final int USER_ACCOUNT_GROUP_ID = 1;

	private static final int GROUP_TYPE_GROUP_ID = 5;

	private final ProjectApi projectApi;

	private final UtilityApi utilityApi;

	private final UserApiService userApiService;

	private final RoleApiService roleApiService;

	private final UserGroupApiService userGroupApiService;

	public ProjectApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public ProjectApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}
	
	public ProjectApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public ProjectApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.projectApi = new ProjectApi(getUserApiClient());
		this.utilityApi = new UtilityApi(getUserApiClient());
		this.userApiService = dataManagerService.getUserApiService(apiUser);
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);;
		this.userGroupApiService = dataManagerService.getUserGroupApiService(apiUser);
	}

	public ProjectBuilder createProject(String name) {
		return new ProjectBuilder(name, this);
	}
	
	public Project createProjectFromTemplate() {
		return createProjectFromTemplate(randomAlphabetic());
	}
	
	public Project createProjectFromTemplate(String projectName) {
		try {
			IdNamed project = projectApi.createProjectFromTemplate(
					new ProjectTemplateData().name(projectName).kindOfNewProject(KindOfNewProjectEnum.CLEAN));

			return new Project(new ProjectId(project.getId()), project.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Project createProjectFromZip(File projectTemplateFile) {
		Objects.requireNonNull(projectTemplateFile);
		if (!projectTemplateFile.exists()) {
			throw new IllegalArgumentException(
					"Project template file %s does not exist".formatted(projectTemplateFile.getAbsolutePath()));
		}
		try {
			String conversationId = UUID.randomUUID().toString();
			utilityApi.uploadFile(conversationId, projectTemplateFile);

			IdNamed project = projectApi.createProjectFromTemplate(
					new ProjectTemplateData().name(randomAlphabetic())
							.kindOfNewProject(KindOfNewProjectEnum.CLEAN)
							.templateUploadConversationId(conversationId)
							.importAllAssets(Boolean.TRUE)
							.importDashboards(Boolean.TRUE)
							.generateWikiPages(Boolean.TRUE)
			);
			
			return new Project(new ProjectId(project.getId()), project.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void markRemoved(ProjectId projectId) {
		try {
			projectApi.markRemoved(projectId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteProject(Project project) {
		deleteProject(project.id());
	}
	
	public void deleteProject(ProjectId projectId) {
		try {
			markRemoved(projectId);
			projectApi.deleteProject(projectId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerId findTrackerByName(Project project, String trackerName) {
		try {
			logger.debug("Find tracker by name: {} in project: {}, {}", trackerName, project.id(), project.name());
			return this.projectApi.getTrackers1(project.id().id()).stream()
					.filter(t -> t.getName().equals(trackerName))
					.map(t -> new TrackerId(t.getId()))
					.findAny()
					.orElseThrow(() -> new IllegalStateException("Tracker cannot be found by name: '%s' in project: %s, %s".formatted(trackerName, project.id(), project.name())));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addUserWithRoles(ProjectId projectId, List<String> usernames, List<String> roleNames) {
		try {
			List<Integer> roleIds = covertToRoleId(roleNames);
			Map<String, List<Integer>> usersAndRoles = usernames.stream()
					.map(userApiService::findUserByName)
					.map(User::getId)
					.map(id -> "%s-%s".formatted(Integer.valueOf(USER_ACCOUNT_GROUP_ID), id))
					.collect(Collectors.toMap(Function.identity(), v -> roleIds));

			projectApi.addNewMembers(Integer.valueOf(projectId.id()),
					new ProjectMemberAndRole().memberIdWithRoleIds(usersAndRoles));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addGroupWithRoles(ProjectId projectId, List<String> groupNames, List<String> roleNames) {
		try {
			List<Integer> roleIds = covertToRoleId(roleNames);
			Map<String, List<Integer>> groupsAndRoles = memberIdWithRoleIds(groupNames, roleIds);
			
			projectApi.addNewMembers(Integer.valueOf(projectId.id()),
					new ProjectMemberAndRole().memberIdWithRoleIds(groupsAndRoles));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addRoles(ProjectId projectId, String roleName, ProjectPermission... permissionList) {
		addRoles(projectId, roleName, Arrays.asList(permissionList));
	}
	
	public void addRoles(ProjectId projectId, String roleName, List<ProjectPermission> permissionList) {
		try {
			List<Integer> permissionOrdinals = permissionList.stream().map(ProjectPermission::bit).toList();
			projectApi.addRoles(Integer.valueOf(projectId.id()), new ProjectAddRoles().name(roleName)
					.permissionIds(permissionOrdinals));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Map<String, List<Integer>> memberIdWithRoleIds(List<String> groupNames, List<Integer> roleIds) {
		Map<String, UserGroupId> groupMap = userGroupApiService.listGroups().stream().collect(Collectors.toMap(UserGroup::name, UserGroup::id));
		
		return groupNames.stream()
				.map(groupMap::get)
				.map(groupId -> "%s-%s".formatted(Integer.valueOf(GROUP_TYPE_GROUP_ID), groupId.id()))
				.collect(Collectors.toMap(Function.identity(), v -> roleIds));
	}
	
	private List<Integer> covertToRoleId(List<String> roleNames) {
		Map<String, RoleId> roleMap = roleApiService.listRoles().stream().collect(Collectors.toMap(Role::getName, Role::getRoleId));
		return roleNames.stream()
				.map(roleMap::get)
				.map(RoleId::id)
				.toList();
	}
	
	private String randomAlphabetic() {
		return RandomStringUtils.randomAlphabetic(10).toLowerCase();
	}
	
}
