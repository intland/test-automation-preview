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

package com.intland.codebeamer.integration.api.service.utility;

import org.apache.commons.lang.StringUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.ApiResponse;
import com.intland.swagger.client.internal.api.UtilityApi;

public class UtilityApiService extends AbstractApiService {

	private final UtilityApi utilityApi;

	public UtilityApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public UtilityApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public UtilityApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public UtilityApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		utilityApi = new UtilityApi(getUserApiClient());
	}

	public DocumentFileInfo downloadDocumentFile(Integer artifactId) {
		return downloadDocumentFile(artifactId, null, null, null, null, null);
	}

	public DocumentFileInfo downloadDocumentFile(Integer artifactId, Integer taskId, Integer revision, Integer version, Boolean history,
			Boolean notification) {
		try {
			ApiResponse<Void> response = utilityApi.downloadDocumentFileWithHttpInfo(
					artifactId, taskId, revision, version, history, notification);
			return createDocumentFileInfo(response);
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private DocumentFileInfo createDocumentFileInfo(ApiResponse<Void> response) {
		return new DocumentFileInfo(
				StringUtils.strip(
						StringUtils.substringAfter(response.getHeaders().get("content-disposition").getFirst(), "filename=")
								.strip(), "\""),
				StringUtils.substringBefore(response.getHeaders().get("content-type").getFirst(), ";"),
				Long.valueOf(response.getHeaders().get("content-length").getFirst()));
	}

}
