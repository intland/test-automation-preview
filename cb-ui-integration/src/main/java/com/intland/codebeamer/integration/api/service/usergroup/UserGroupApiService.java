package com.intland.codebeamer.integration.api.service.usergroup;

import java.util.List;
import java.util.Optional;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.legacy.LegacyApiException;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.usergroup.legacy.LegacyUserGroupApi;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.UserApi;
import com.intland.swagger.client.model.ProjectGroupWithRoles;

public class UserGroupApiService extends AbstractApiService {
		
	public static final String DEFAULT_PASSWORD = "007";
		
	private UserApi userApi;
	
	private LegacyUserGroupApi legacyUserGroupApi;

	public UserGroupApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public UserGroupApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserGroupApiService.DEFAULT_PASSWORD));
	}
	
	public UserGroupApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public UserGroupApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);

		this.userApi = new UserApi(getUserApiClient());
		this.legacyUserGroupApi = new LegacyUserGroupApi(applicationConfiguration, apiUser);
	}

	public UserGroup createApiPermissionGroupIfMissing() {
		return createUserGroupIfMissing(UserGroupPermission.API_PERMISSION);
	}

	public UserGroup createProjectCategoryAdminGroupIfMissing() {
		return createUserGroupIfMissing(UserGroupPermission.PROJECT_CATEGORY_ADMIN);
	}

	public UserGroup createSystemAdminGroupIfMissing() {
		return createUserGroupIfMissing(UserGroupPermission.SYSTEM_ADMIN);
	}
	
	public UserGroup createUserGroup(String groupName, UserGroupPermission... userGroupPermission) {
		try {
			return findUserGroupByName(userApi, groupName)
				.orElseGet(() -> legacyUserGroupApi.createUserGroup(groupName, groupName, userGroupPermission));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public UserGroup findUserGroupByName(String groupName) {
		return findUserGroupByName(userApi, groupName).orElseThrow(() -> new IllegalArgumentException("Group('%s') cannot be found".formatted(groupName)));
	}
	
	public List<UserGroup> listGroups() {
		try {
			return userApi.getAllUserGroups(null, null, null).getGroups().stream()
				 	.map(ProjectGroupWithRoles::getGroup)
				 	.map(p -> new UserGroup(new UserGroupId(p.getId()), p.getName()))
				 	.distinct()
				 	.toList();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private UserGroup createUserGroupIfMissing(UserGroupPermission userGroupPermission) {
		try {
			return findUserGroupByName(userApi, userGroupPermission.name())
				.orElseGet(() -> legacyUserGroupApi.createUserGroup(userGroupPermission.name(), userGroupPermission.name(), userGroupPermission));
		} catch (LegacyApiException e) {
			if (e.isArtifactNameConflictException()) {
				return findUserGroupByName(userApi, userGroupPermission.name()).orElseThrow(() -> new IllegalStateException(e.getMessage(), e));
			}
			throw new IllegalStateException(e.getMessage(), e);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private Optional<UserGroup> findUserGroupByName(UserApi userApi, String groupName) {
		try {
			return listGroups().stream()
			 	.filter(g -> g.name().equalsIgnoreCase(groupName))
			 	.findAny();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
