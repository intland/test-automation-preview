package com.intland.codebeamer.integration.api.service.testmanagement.testrun;

import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.CreateTestRunBuilder;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TestManagementApi;
import com.intland.swagger.client.internal.api.TestRunApi;
import com.intland.swagger.client.model.AddTestCaseToTestSetRequest;

public class TestRunApiService extends AbstractApiService {

	private final TestRunApi testRunApi;

	private final TestManagementApi testManagementApi;

	public TestRunApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public TestRunApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public TestRunApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public TestRunApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.testRunApi = new TestRunApi(getUserApiClient());
		this.testManagementApi = new TestManagementApi(getDefaultApiClient());

	}

	public TrackerItemId createTestRunForTestCasesOrTestSets(TrackerId testRunTrackerId,
			Function<CreateTestRunBuilder, CreateTestRunBuilder> builder) {
		try {
			return builder.apply(new CreateTestRunBuilder(testRunTrackerId, testRunApi)).create();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void addTestCasesToSets(List<Integer> testCaseIds, List<Integer> testSetIds) throws ApiException {
		AddTestCaseToTestSetRequest addTestCaseToTestSetRequest = new AddTestCaseToTestSetRequest()
				.testCaseIds(testCaseIds)
				.testSetIds(testSetIds);

		testManagementApi.addTestCasesToSets(addTestCaseToTestSetRequest);
	}
}
