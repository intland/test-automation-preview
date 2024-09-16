package com.intland.codebeamer.integration.api.service;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.CodebeamerApiClientFactory;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.swagger.client.internal.ApiClient;

public class AbstractApiService {
	
	protected final ApplicationConfiguration applicationConfiguration;

	protected final DataManagerService dataManagerService;

	private final ApiUser apiUser;

	private ApiClient defaultApiClient;

	private ApiClient userApiClient;
	
	public AbstractApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public AbstractApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		this.applicationConfiguration = applicationConfiguration;
		this.apiUser = apiUser;
		this.dataManagerService = new DataManagerService(applicationConfiguration);
	}

	public ApiClient getUserApiClient() {
		if (userApiClient != null) {
			return userApiClient;
		}
		
		this.userApiClient = createApiClient(apiUser);
		return this.userApiClient;
	}
	
	public ApiClient getDefaultApiClient() {
		if (defaultApiClient != null) {
			return defaultApiClient;
		}
		
		this.defaultApiClient = createApiClient(applicationConfiguration.getApiUser());
		return this.defaultApiClient;
	}
	
	private ApiClient createApiClient(ApiUser apiUser) {
		return new CodebeamerApiClientFactory().getApiClient(getApplicationApiUrl(), apiUser.username(), apiUser.password());
	}
	
	private String getApplicationApiUrl() {
		return this.applicationConfiguration.url();
	}
	
}
