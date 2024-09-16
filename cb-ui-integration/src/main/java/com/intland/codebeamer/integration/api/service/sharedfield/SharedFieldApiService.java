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

package com.intland.codebeamer.integration.api.service.sharedfield;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.MemberType;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.SharedFieldApi;

public class SharedFieldApiService extends AbstractApiService {

	private final SharedFieldApi sharedFieldApi;

	public SharedFieldApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public SharedFieldApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public SharedFieldApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public SharedFieldApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		sharedFieldApi = new SharedFieldApi(getUserApiClient());
	}

	public void createSharedField(String name, Function<SharedFieldBuilder, SharedFieldBuilder> builder) {
		try {
			com.intland.swagger.client.model.SharedField sharedField = builder.apply(new SharedFieldBuilder(name))
					.build();
			sharedFieldApi.createSharedField(sharedField);
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteSharedFieldByName(String sharedFieldName) {
		try {
			sharedFieldApi.deleteSharedField(sharedFieldName);
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public SharedField getSharedFieldByName(String sharedFieldName) {
		try {
			SharedField found = mapToSharedField(sharedFieldApi.getSharedField(sharedFieldName));

			if (found == null) {
				throw new IllegalStateException("Shared field with name: (%s) not found".formatted(sharedFieldName));
			}

			return found;
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private SharedField mapToSharedField(com.intland.swagger.client.model.SharedField sharedField) {
		if (sharedField == null) {
			return null;
		}

		return new SharedField(new SharedFieldId(sharedField.getId().intValue()), sharedField.getName(),
				FieldType.findById(sharedField.getFieldType().getKey().intValue()),
				sharedField.getDisplayName(),
				sharedField.getDescription(),
				sharedField.getMemberType() != null ?
						MemberType.findById(sharedField.getMemberType().getKey().intValue()) :
						null,
				sharedField.getReferenceType() != null ?
						ArtifactType.findById(sharedField.getReferenceType().getKey().intValue()) :
						null);
	}
}
