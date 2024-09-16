/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.api.service.applicationconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.ApplicationConfigurationApi;
import com.intland.swagger.client.model.ApplicationConfigurationResponse;

public class ApplicationConfigurationRestApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger(ApplicationConfigurationRestApiService.class);

	private final ApplicationConfigurationApi applicationConfigApi;

	public ApplicationConfigurationRestApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.applicationConfigApi = new ApplicationConfigurationApi(getUserApiClient());
	}

	public ApplicationConfigurationRestApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public ApplicationConfigurationRestApiService(ApplicationConfiguration applicationConfiguration) {
		super(applicationConfiguration);
		this.applicationConfigApi = new ApplicationConfigurationApi(getUserApiClient());
	}

	public ApplicationConfigurationResponse getApplicationConfiguration(Integer version) {
		try {
			logger.debug("get Application Configuration by version id : {}", version);
			return applicationConfigApi.getApplicationConfiguration(version);
		} catch (Exception e) {
			throw new IllegalStateException("Application Configuration not found");
		}
	}

	public ApplicationConfigurationResponse getApplicationConfiguration() {
		try {
			return applicationConfigApi.getApplicationConfiguration(null);
		} catch (ApiException e) {
			throw new IllegalStateException("Application Configuration not found");
		}
	}

	public void saveApplicationConfiguration(String request) {
		try {
			applicationConfigApi.saveApplicationConfiguration(request);
		} catch (ApiException e) {
			throw new IllegalStateException("Application Configuration not saved");
		}
	}

	public String getApplicationConfigParameter(String tag) {//todo helpers as Integer, as Boolean, as Double
		if (tag == null || tag.isEmpty() || tag.isBlank()) {
			throw new IllegalArgumentException("Failed to get Application Configuration Parameter : Invalid tag");
		} 
		
		ApplicationConfigurationResponse configurationResponse = this.getApplicationConfiguration();
		assert configurationResponse.getConfigurationPageModel() != null;
		assert configurationResponse.getConfigurationPageModel().getConfiguration() != null;
		String configuration = configurationResponse.getConfigurationPageModel().getConfiguration().getConfiguration();
		JSONObject jsonObject = new JSONObject(configuration);

		String[] tagList = tag.split("\\.");
		for (String header : tagList) {
			jsonObject = new JSONObject(jsonObject.get(header));
		}
		return jsonObject.toString();
	}

	public String setApplicationConfigParameter(String inputTag, Object newValue) {
		if (inputTag == null || inputTag.isEmpty() || inputTag.isBlank()) {
			throw new IllegalArgumentException("Failed to get Application Configuration Parameter : Invalid inputTag");
		}
		
		ApplicationConfigurationResponse configurationResponse = this.getApplicationConfiguration();
		assert configurationResponse.getConfigurationPageModel() != null;
		assert configurationResponse.getConfigurationPageModel().getConfiguration() != null;

		String configuration = configurationResponse.getConfigurationPageModel().getConfiguration().getConfiguration();
		JSONObject jsonObject = new JSONObject(configuration);

		String[] tagList = inputTag.split("\\.");
		List<JSONObject> jsonList = new ArrayList<>();
		jsonList.add(new JSONObject(configuration));

		for (int i = 0; i < tagList.length - 1; i++) {
			Object jsonBody = jsonObject.get(tagList[i]);
			jsonObject = new JSONObject(jsonBody.toString());
			jsonList.add(jsonObject);
		}

		jsonObject.put(tagList[tagList.length-1], newValue);

		for (int index = tagList.length - 2; index >= 0; index--) {
			newValue = jsonObject;
			jsonObject = jsonList.get(index);
			jsonObject.put(tagList[index], newValue);
		}

		this.saveApplicationConfiguration(jsonObject.toString());
		return jsonObject.toString();
	}

	public String clearApplicationConfigParameter(String tag) {
		return setApplicationConfigParameter(tag, null);
	}

	public String getConfigurationString(String tag, Object value) {
		return getConfigurationString(null, tag, value);
	}

	public String getConfigurationString(Integer version, String tag, Object value) {
		ApplicationConfigurationResponse configurationResponse;
		if (version != null) {
			configurationResponse = this.getApplicationConfiguration(version);
		} else {
			configurationResponse = this.getApplicationConfiguration();
		}
		assert configurationResponse.getConfigurationPageModel() != null;
		assert configurationResponse.getConfigurationPageModel().getConfiguration() != null;
		String configuration = configurationResponse.getConfigurationPageModel().getConfiguration().getConfiguration();
		JSONObject jsonObject = new JSONObject(configuration);
		jsonObject.accumulate(tag, value);
		return jsonObject.toString();
	}
}
