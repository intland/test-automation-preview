package com.intland.codebeamer.integration.api.service.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.UserApi;
import com.intland.swagger.client.model.CreateNewUser;
import com.intland.swagger.client.model.CreateNewUser.StatusEnum;
import com.intland.swagger.client.model.UpdateUser;
import com.intland.swagger.client.model.UpdateUserRequest;
import com.intland.swagger.client.model.UserDetails;
import com.intland.swagger.client.model.UserSettingUpdate;
import com.intland.swagger.client.model.UserWithGroupsRequest;

import net.datafaker.Faker;

public class UserApiService extends AbstractApiService {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static final String DEFAULT_PASSWORD = "007";
	
	private static final int PREFERRED_USER_UI_SETTINGS = 124;
	
	private UserApi userApi;

	private UserGroupApiService userGroupApiService;
	
	public UserApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public UserApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}
	
	public UserApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public UserApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);

		this.userApi = new UserApi(getUserApiClient());
		this.userGroupApiService = dataManagerService.getUserGroupApiService(apiUser);
	}

	public UserBuilder createUser(String username) {
		return new UserBuilder(username, this, this.userGroupApiService);
	}
	
	public UserBuilder createUser() {
		return new UserBuilder(new Faker().internet().username(), this, this.userGroupApiService);
	}
	
	public User findUserByName(String username) {
		try {
			com.intland.swagger.client.model.User u = findUserByName(this.userApi, username).get();
			return new User(new UserId(u.getId()), u.getName(), u.getFirstName(), u.getLastName(), u.getEmail());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public User createUser(UserGroupId... groups) {
		Faker faker = new Faker();
		return createUser(faker.internet().username() + getRandomString(), groups);
	}

	public User createUser(String username, UserGroupId... groups) {
		return createUser(username, Arrays.asList(groups));
	}
	
	public User createUser(String username, List<UserGroupId> groups) {
		Faker faker = new Faker();
		return createUser(
				username,
				faker.name().firstName().replace("'", ""),
				faker.name().lastName().replace("'", ""),
				DEFAULT_PASSWORD, 
				faker.internet().safeEmailAddress(getRandomString()), 
				StatusEnum.ACTIVATED, 
				groups);
	}
	
	public User createUser(String name, String firstName, String lastName, String password, String email, StatusEnum status, UserGroupId... groups) {
		return createUser(name, firstName, lastName, password, email, status, Arrays.asList(groups));
	}
	
	public User createUser(String name, String firstName, String lastName, String password, String email, StatusEnum status, List<UserGroupId> groups) {
		CreateNewUser createNewUser = new CreateNewUser()
					.name(name)
					.firstName(firstName)
					.lastName(lastName)
					.password(password)
					.email(email)
					.status(status);
		try {
			UserWithGroupsRequest createUserWithGroupsRequest = new UserWithGroupsRequest()
				.groups(groups.stream().map(UserGroupId::id).toList())
				.user(createNewUser);
			return convertToUser(userApi.createUser(createUserWithGroupsRequest));
		} catch (Exception e) {
			logger.error("New user cannot be create with {}, groups: {}", createNewUser.toJson(), groups);
			throw new IllegalStateException("Exception: %s, Request: %s".formatted(e.getMessage(), createNewUser.toJson()), e);
		}
	}

	public void disableUser(User user) {
		try {
			this.userApi.updateUserDetails(user.getId(), new UpdateUserRequest().user(new UpdateUser()
					.name(user.getName())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.email(user.getEmail())
					.status(StatusEnum.DISABLED.getValue())));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public void switchToClassicUI() {
		try {
			userApi.userSettingUpdate(PREFERRED_USER_UI_SETTINGS, new UserSettingUpdate().settingValue("CLASSIC"));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public void switchToNextgenUI() {
		try {
			userApi.userSettingUpdate(PREFERRED_USER_UI_SETTINGS, new UserSettingUpdate().settingValue("CBX"));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private Optional<com.intland.swagger.client.model.User> findUserByName(UserApi userApi, String userName) {
		try {
			return Optional.ofNullable(userApi.getUserByName(userName));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private User convertToUser(UserDetails user) {
		return new User(new UserId(user.getId()), user.getName(), user.getFirstName(), user.getLastName(), user.getEmail());
	}
	
	private String getRandomString() {
		return String.valueOf(System.currentTimeMillis());
	}

}
