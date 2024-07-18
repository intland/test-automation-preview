package com.intland.codebeamer.integration.api;

import com.intland.swagger.client.internal.api.IntegrationApi;

public class DataFactoryImpl implements DataFactory {

	private IntegrationApi integrationApi;

	public DataFactoryImpl(IntegrationApi integrationApi) {
		this.integrationApi = integrationApi;
	}

	@Override
	public Integer createProject(String projectName) {
		try {
			return integrationApi.createProject(projectName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Integer createTracker(Integer projectId, String projectName) {
		// TODO Auto-generated method stub
		return null;
	}

}
