package com.intland.codebeamer.integration.api.service.project;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.role.RoleId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.codebeamer.integration.test.ProjectCreatorExecutorService;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.ProjectApi;
import com.intland.swagger.client.internal.api.UtilityApi;
import com.intland.swagger.client.model.AddProjectToGroup;
import com.intland.swagger.client.model.CreateProjectGroup;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.ProjectAddRoles;
import com.intland.swagger.client.model.ProjectBrowserGroup;
import com.intland.swagger.client.model.ProjectMemberAndRole;
import com.intland.swagger.client.model.ProjectTemplateData;
import com.intland.swagger.client.model.ProjectTemplateData.KindOfNewProjectEnum;

public class ProjectApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();

	private static final int USER_ACCOUNT_GROUP_ID = ArtifactType.USER_ACCOUNT.getValue();

	private static final int GROUP_TYPE_GROUP_ID = ArtifactType.ARTIFACT.getValue();

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
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
		this.userGroupApiService = dataManagerService.getUserGroupApiService(apiUser);
	}

	public ProjectBuilder createProject(String name) {
		return new ProjectBuilder(name, this);
	}

	public Project createProjectFromTemplate() {
		return createProjectFromTemplate(randomAlphabetic(), ProjectTemplate.DEFAULT_PROJECT, false);
	}

	public Project createProjectFromTemplate(String projectName) {
		return createProjectFromTemplate(projectName, ProjectTemplate.DEFAULT_PROJECT, false);
	}

	public Project createProjectFromTemplate(String projectName, ProjectTemplate projectTemplate) {
		return createProjectFromTemplate(projectName, projectTemplate, true);
	}

	public Project createProjectFromTemplate(String projectName, ProjectTemplate projectTemplate, boolean createTrackerItems) {
		ProjectTemplateData projectTemplateData = new ProjectTemplateData()
				.name(projectName)
				.kindOfNewProject(KindOfNewProjectEnum.DEMO)
				.generateWikiPages(Boolean.TRUE)
				.importAllAssets(Boolean.valueOf(createTrackerItems))
				.importDashboards(Boolean.TRUE)
				.demoDataPack(projectTemplate.getTemplateName())
				.demoDataPackNames(List.of());

		return createProject(projectTemplateData);
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

			IdNamed project = createProjectWithRetry(() -> projectApi.createProjectFromTemplate(
					new ProjectTemplateData().name(randomAlphabetic())
							.kindOfNewProject(KindOfNewProjectEnum.CLEAN)
							.templateUploadConversationId(conversationId)
							.importAllAssets(Boolean.TRUE)
							.importDashboards(Boolean.TRUE)
							.generateWikiPages(Boolean.TRUE)
			));

			ProjectId projectId = createProjectId(project);
			addDefaultProjectAdmin(projectId);

			return new Project(projectId, project.getName());
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

	public Tracker findTrackerByName(Project project, String trackerName) {
		try {
			logger.debug("Find tracker by name: {} in project: {}, {}", trackerName, project.id(), project.name());
			return this.projectApi.getTrackers1(project.id().id()).stream()
					.filter(t -> t.getName().equals(trackerName))
					.map(t -> new Tracker(new TrackerId(Objects.requireNonNull(t.getId()).intValue()), t.getName()))
					.findAny()
					.orElseThrow(() -> new IllegalStateException(
							"Tracker cannot be found by name: '%s' in project: %s, %s".formatted(trackerName, project.id(),
									project.name())));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addUserWithRoles(ProjectId projectId, List<String> usernames, List<String> roleNames) {
		addUserWithRoles(projectId.id(), usernames, roleNames);
	}

	public void addUserWithRoles(int projectId, List<String> usernames, List<String> roleNames) {
		try {
			List<Integer> roleIds = convertToRoleId(roleNames);
			Map<String, List<Integer>> usersAndRoles = usernames.stream()
					.map(userApiService::findUserByName)
					.map(User::getId)
					.map(id -> "%s-%s".formatted(Integer.valueOf(USER_ACCOUNT_GROUP_ID), id))
					.collect(Collectors.toMap(Function.identity(), v -> roleIds));

			projectApi.addNewMembers(Integer.valueOf(projectId),
					new ProjectMemberAndRole().memberIdWithRoleIds(usersAndRoles));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addGroupWithRoles(ProjectId projectId, List<String> groupNames, List<String> roleNames) {
		try {
			List<Integer> roleIds = convertToRoleId(roleNames);
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
		addRoles(projectId.id(), roleName, permissionList);
	}

	public void addRoles(int projectId, String roleName, List<ProjectPermission> permissionList) {
		try {
			List<Integer> permissionOrdinals = permissionList.stream().map(ProjectPermission::bit).toList();
			projectApi.addRoles(Integer.valueOf(projectId), new ProjectAddRoles().name(roleName)
					.permissionIds(permissionOrdinals));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public List<Role> getRoles(ProjectId projectId) {
		try {
			return projectApi.getRoles1(projectId.id(), Boolean.FALSE, null, Boolean.TRUE, 0, 0, null, null)
					.getRoles().stream()
					.map(r -> new Role(new RoleId(r.getRoleModel().getId()), r.getRoleModel().getName()))
					.toList();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	Project createProject(ProjectTemplateData projectTemplateData) {
		try {
			IdNamed project = createProjectWithRetry(() -> projectApi.createProjectFromTemplate(projectTemplateData));
			ProjectId projectId = createProjectId(project);
			
			addDefaultProjectAdmin(projectId);

			return new Project(projectId, project.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public List<ProjectGroup> findAllProjectGroups() {
		try {
			return projectApi.getProjectGroups().stream()
					.map(this::convertToProjectGroup)
					.toList();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to retrieve project groups", e);
		}
	}

	public ProjectGroup findProjectGroupById(Integer id) {
		try {
			ProjectBrowserGroup group = projectApi.getProjectGroupById(id);
			return convertToProjectGroup(group);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to retrieve project group with id: " + id, e);
		}
	}

	public void createProjectGroup(String groupName) {
		Objects.requireNonNull(groupName, "Group name cannot be null");
		try {
			CreateProjectGroup createProjectGroupModel = new CreateProjectGroup();
			createProjectGroupModel.setGroupName(groupName);
			projectApi.createProjectGroup(createProjectGroupModel);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create project group: " + groupName, e);
		}
	}

	public void addProjectToGroup(Integer groupId, List<Integer> projectIds) {
		Objects.requireNonNull(groupId, "Group ID cannot be null");
		Objects.requireNonNull(projectIds, "Project IDs cannot be null");

		try {
			AddProjectToGroup model = new AddProjectToGroup();
			model.setProjectIds(projectIds);
			projectApi.addProjectToProjectGroup(groupId, model);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to add projects to group with ID: " + groupId, e);
		}
	}

	public List<Project> findProjectsByGroupId(Integer groupId) {
		try {
			return projectApi.getProjectsByGroupId(groupId).stream()
					.map(project -> new Project(new ProjectId(project.getId().intValue()), project.getName()))
					.toList();
		} catch (ApiException e) {
			throw new IllegalStateException("Failed to retrieve projects for group with ID: " + groupId, e);
		}
	}

	public void deleteProjectGroup(ProjectGroupId groupId) {
		Objects.requireNonNull(groupId, "Group ID cannot be null");
		try {
			projectApi.deleteProjectGroup(Integer.valueOf(groupId.id()));
		} catch (Exception e) {
			throw new IllegalStateException("Failed to delete project group with ID: " + groupId, e);
		}
	}

	private Map<String, List<Integer>> memberIdWithRoleIds(List<String> groupNames, List<Integer> roleIds) {
		Map<String, UserGroupId> groupMap = userGroupApiService.listGroups().stream()
				.collect(Collectors.toMap(UserGroup::name, UserGroup::id));

		return groupNames.stream()
				.map(groupMap::get)
				.map(groupId -> "%s-%s".formatted(Integer.valueOf(GROUP_TYPE_GROUP_ID), groupId.id()))
				.collect(Collectors.toMap(Function.identity(), v -> roleIds));
	}

	private IdNamed createProjectWithRetry(Callable<IdNamed> createProject) {
		try {
			return ProjectCreatorExecutorService.INSTANCE.submit(createProject).get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Project cannot be created");
		} catch (ExecutionException e) {
			throw new IllegalStateException("Project cannot be created");
		}
	}

	private List<Integer> convertToRoleId(List<String> roleNames) {
		Map<String, RoleId> roleMap = roleApiService.listRoles().stream()
				.collect(Collectors.toMap(Role::getName, Role::getRoleId));
		return roleNames.stream()
				.map(roleMap::get)
				.map(RoleId::id)
				.toList();
	}

	private String randomAlphabetic() {
		return RandomStringUtils.randomAlphabetic(10).toLowerCase();
	}

	/**
	 * Adds the configuration user as project admin to the project.
	 * This is needed in order to call endpoints inside UI components (e.g. field configs, choice field values etc.)
	 */
	private void addDefaultProjectAdmin(ProjectId projectId) {
		addUserWithRoles(projectId, List.of(applicationConfiguration.getApiUser().username()), List.of("Project Admin"));
	}

	private ProjectGroup convertToProjectGroup(ProjectBrowserGroup group) {
		return new ProjectGroup(new ProjectGroupId(group.getId().intValue()), group.getName());
	}

	private ProjectId createProjectId(IdNamed project) {
		return new ProjectId(Objects.requireNonNull(project.getId()).intValue());
	}
	
}
