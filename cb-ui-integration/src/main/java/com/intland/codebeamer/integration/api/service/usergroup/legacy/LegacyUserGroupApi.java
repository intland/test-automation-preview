package com.intland.codebeamer.integration.api.service.usergroup.legacy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.legacy.AbstractLegacyApi;
import com.intland.codebeamer.integration.api.legacy.LegacyApiException;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;

public class LegacyUserGroupApi extends AbstractLegacyApi {

	public LegacyUserGroupApi(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public LegacyUserGroupApi(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		this(applicationConfiguration.url(), apiUser);
	}
	
	public LegacyUserGroupApi(String applicationUrl, ApiUser apiUser) {
		super(applicationUrl, apiUser);
	}

	public UserGroup createUserGroup(String name, String description, UserGroupPermission... groupPermissions) {
		try {
			CreateUserGroupRequest createUserGroupRequest = new CreateUserGroupRequest(name, description, Arrays.stream(groupPermissions).map(UserGroupPermission::getBitMaks).toList());
			CreateUserGroupResponse response = sendPost("user/group", createUserGroupRequest, CreateUserGroupResponse.class);
			return new UserGroup(new UserGroupId(extractId(response)), response.name);
		} catch (LegacyApiException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Integer extractId(CreateUserGroupResponse response) {
		return Integer.valueOf(StringUtils.substringAfterLast(response.uri(), '/'));
	}
	
	record CreateUserGroupResponse(String uri, String name) implements Serializable {}

	record CreateUserGroupRequest(String name, String description, List<Integer> permissions) implements Serializable {}
	
}
