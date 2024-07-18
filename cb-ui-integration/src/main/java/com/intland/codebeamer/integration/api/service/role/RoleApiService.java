package com.intland.codebeamer.integration.api.service.role;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.RoleApi;

public class RoleApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();

	private RoleApi roleApi;

	public RoleApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public RoleApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}
	
	public RoleApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public RoleApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);

		this.roleApi = new RoleApi(getUserApiClient());
	}

	public Role findRoleByName(String roleName) {
		try {
			logger.debug("Find role by name: {}", roleName);
			return roleApi.getRoles().stream()
					.filter(r -> r.getName().equals(roleName))
					.map(r -> new Role(new RoleId(r.getId()), r.getName()))
					.findAny()
					.orElseThrow(() -> new IllegalStateException("Role cannot be found by name: %s".formatted(roleName)));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public List<Role> listRoles() {
		try {
			return roleApi.getRoles().stream()
					.map(r -> new Role(new RoleId(r.getId()), r.getName()))
					.toList();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
}
