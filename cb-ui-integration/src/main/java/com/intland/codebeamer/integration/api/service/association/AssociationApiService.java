package com.intland.codebeamer.integration.api.service.association;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.builder.association.AssociationBuilder;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.artifact.AssociationId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.AssociationApi;
import com.intland.swagger.client.model.Association;

public class AssociationApiService extends AbstractApiService {

	private final AssociationApi associationApi;

	public AssociationApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public AssociationApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public AssociationApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public AssociationApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.associationApi = new AssociationApi(getUserApiClient());
	}

	public AssociationId createAssociation(Function<AssociationBuilder, AssociationBuilder> associationBuilder) {
		try {
			Association association = associationBuilder.apply(new AssociationBuilder()).build();
			association = associationApi.createAssociation(association);
			return new AssociationId(association.getId().intValue());
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteAssociation(AssociationId associationId) {
		try {
			associationApi.deleteAssociation(associationId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
