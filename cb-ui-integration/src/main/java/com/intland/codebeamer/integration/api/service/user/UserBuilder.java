package com.intland.codebeamer.integration.api.service.user;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupPermission;

public class UserBuilder {

	private static final String REGULAR_USER_GROUP = "Regular User";

	private static final String SYSTEM_ADMINISTRATOR_GROUP = "System Administrator";

	private String username;
	
	private UserApiService userApiService;
	
	private UserGroupApiService userGroupApiService;

	private List<String> groups;
	
	public UserBuilder(String username, UserApiService userApiService, UserGroupApiService userGroupApiService) {
		this.username = username;
		this.userApiService = userApiService;
		this.userGroupApiService = userGroupApiService;
		this.groups = new LinkedList<>();
	}

	public UserBuilder addToRegularUserGroup() {
		this.groups.add(REGULAR_USER_GROUP);
		return this;
	}

	public UserBuilder addToDefaultSystemAdministratorGroup() {
		this.groups.add(SYSTEM_ADMINISTRATOR_GROUP);
		return this;
	}

	public UserBuilder addToNewGroupWithPermissions(UserGroupPermission... permissions) {
		this.groups.add(this.userGroupApiService.createUserGroup(getRandomGroupName("Custom"), permissions).name());
		return this;
	}

	public UserBuilder addToNewRegularUserGroup() {
		return addToNewRegularUserGroup(getRandomGroupName("RegularUser"));
	}

	public UserBuilder addToNewRegularUserGroup(String groupName) {
		this.groups.add(this.userGroupApiService.createUserGroup(groupName, UserGroupPermission.getRegularUserGroup()).name());
		return this;
	}

	public UserBuilder addToNewSystemAdminGroup() {
		return addToNewSystemAdminGroup(getRandomGroupName("SystemAdmin"));
	}

	public UserBuilder addToNewSystemAdminGroup(String groupName) {
		this.groups.add(this.userGroupApiService.createUserGroup(groupName, UserGroupPermission.getSystemAdminGroup()).name());
		return this;
	}

	public UserBuilder addToApiPermissionGroup() {
		this.groups.add(this.userGroupApiService.createApiPermissionGroupIfMissing().name());
		return this;
	}
	
	public UserBuilder addToProjectCategoryAdminGroup() {
		this.groups.add(this.userGroupApiService.createProjectCategoryAdminGroupIfMissing().name());
		return this;
	}

	public UserBuilder addToSystemAdminGroup() {
		this.groups.add(this.userGroupApiService.createSystemAdminGroupIfMissing().name());
		return this;
	}
	
	public UserBuilder addToGroup(String groupName) {
		this.groups.add(groupName);
		return this;
	}
	
	public User build() {
		addToApiPermissionGroup();
		
		List<UserGroupId> userGroups = this.groups.stream()
				.map(g -> this.userGroupApiService.findUserGroupByName(g))
				.map(UserGroup::id)
				.toList();

		return this.userApiService.createUser(username, userGroups);
	}

	private String getRandomGroupName(String prefix) {
		return "%s - %s".formatted(prefix, RandomStringUtils.randomAlphabetic(10));
	}

}
