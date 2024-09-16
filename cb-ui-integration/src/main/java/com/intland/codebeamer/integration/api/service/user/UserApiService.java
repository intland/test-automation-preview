package com.intland.codebeamer.integration.api.service.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.user.settings.UserSetting;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupId;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.UserApi;
import com.intland.swagger.client.internal.api.UserSettingsApi;
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

	private UserApi userApi;

	private UserSettingsApi userSettingsApi;

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
		this.userSettingsApi = new UserSettingsApi(getUserApiClient());
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
			com.intland.swagger.client.model.User u = findUserByName(this.userApi, username)
					.orElseThrow(() -> new IllegalStateException("Can't find User with UserName %s".formatted(username)));
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

		com.intland.codebeamer.integration.api.service.user.UserDetails userDetails = new com.intland.codebeamer.integration.api.service.user.UserDetails();
		userDetails.setUsername(username);
		userDetails.setFirstName(faker.name().firstName().replace("'", ""));
		userDetails.setLastName(faker.name().lastName().replace("'", ""));
		userDetails.setEmail(faker.internet().safeEmailAddress(getRandomString()));
		userDetails.setTitle(faker.name().title());
		userDetails.setPhone(faker.numerify("###-###-####"));
		userDetails.setMobile(faker.numerify("##########"));
		userDetails.setCompany(getRandomString());
		userDetails.setAddress(getRandomString());
		userDetails.setCity(getRandomString());
		userDetails.setZip(faker.numerify("#####"));
		userDetails.setState(getRandomString());
		userDetails.setCountry(Country.IN.getName());
		userDetails.setLanguage(Language.EN.toString().toLowerCase());
		userDetails.setSkills(getRandomString());

		return createUser(userDetails, StatusEnum.ACTIVATED, groups);
	}

	public User createUser(com.intland.codebeamer.integration.api.service.user.UserDetails userDetails, StatusEnum status, UserGroupId... groups) {
		return createUser(userDetails, status, Arrays.asList(groups));
	}
	
	public User createUser(com.intland.codebeamer.integration.api.service.user.UserDetails userDetails, StatusEnum status, List<UserGroupId> groups) {
		CreateNewUser createNewUser = new CreateNewUser()
					.name(userDetails.getUsername())
					.firstName(userDetails.getFirstName())
					.lastName(userDetails.getLastName())
					.password(DEFAULT_PASSWORD)
					.email(userDetails.getEmail())
					.title(userDetails.getTitle())
					.phone(userDetails.getPhone())
					.mobile(userDetails.getMobile())
					.company(userDetails.getCompany())
					.address(userDetails.getAddress())
					.city(userDetails.getCity())
					.zip(userDetails.getZip())
					.state(userDetails.getState())
					.country(userDetails.getCountry())
					.language(userDetails.getLanguage())
					.status(status)
					.skills(userDetails.getSkills());
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
			userApi.userSettingUpdate(UserSetting.PREFERRED_USER_UI_SETTINGS.getId(),
					new UserSettingUpdate().settingValue("CLASSIC"));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public void switchToNextgenUI() {
		try {
			userApi.userSettingUpdate(UserSetting.PREFERRED_USER_UI_SETTINGS.getId(),
					new UserSettingUpdate().settingValue("CBX"));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void setUserPreferences(Function<UserPreferenceBuilder, UserPreferenceBuilder> builder) {
		try {
			userSettingsApi.userSettingsUpdate(builder.apply(new UserPreferenceBuilder()).build());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Map<UserSetting, String> getUserSettings() {
		try {
			return Optional.ofNullable(userSettingsApi.userSettings().getSettings())
					.orElseGet(HashMap::new)
					.entrySet()
					.stream()
					.collect(Collectors.toMap(e -> UserSetting.valueOf(e.getKey()), Map.Entry::getValue));
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

	public com.intland.codebeamer.integration.api.service.user.UserDetails convertToUserDetails(
			com.intland.swagger.client.model.User user) {
		com.intland.codebeamer.integration.api.service.user.UserDetails userDetails = new com.intland.codebeamer.integration.api.service.user.UserDetails();

		userDetails.setId(user.getId());
		userDetails.setUsername(user.getName());
		userDetails.setTitle(user.getTitle());
		userDetails.setFirstName(user.getFirstName());
		userDetails.setLastName(user.getLastName());
		userDetails.setEmail(user.getEmail());
		userDetails.setPhone(user.getPhone());
		userDetails.setMobile(user.getMobile());
		userDetails.setCompany(user.getCompany());
		userDetails.setAddress(user.getAddress());
		userDetails.setCity(user.getCity());
		userDetails.setZip(user.getZip());
		userDetails.setState(user.getState());
		userDetails.setCountry(user.getCountry());
		userDetails.setLanguage(user.getLanguage());
		userDetails.setSkills(user.getSkills());

		return userDetails;
	}

	private String getRandomString() {
		return String.valueOf(System.currentTimeMillis());
	}

	public void updateUserDetails(String username, Function<UserAttributeBuilder, UserAttributeBuilder> builder) {
		try {
			Optional<com.intland.swagger.client.model.User> userOptional = findUserByName(this.userApi, username);
			userOptional.ifPresent(user ->
					builder.apply(new UserAttributeBuilder(user, new UpdateUserRequest(), this.userApi)).update()
			);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
