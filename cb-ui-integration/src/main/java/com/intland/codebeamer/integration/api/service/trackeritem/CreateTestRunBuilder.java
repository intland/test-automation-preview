package com.intland.codebeamer.integration.api.service.trackeritem;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.utils.SafeStopWatch;
import com.intland.swagger.client.internal.api.TestRunApi;
import com.intland.swagger.client.model.CreateTestRunRequest;
import com.intland.swagger.client.model.TrackerItem;
import com.intland.swagger.client.model.TrackerItemReference;

public class CreateTestRunBuilder {

	private static final Logger logger = LogManager.getLogger();

	private final TestRunApi testRunApi;

	private final CreateTestRunRequest createTestRunRequest;

	private final TrackerId trackerId;

	public CreateTestRunBuilder(TrackerId trackerId, TestRunApi testRunApi) {
		this.trackerId = trackerId;
		this.testRunApi = testRunApi;
		this.createTestRunRequest = new CreateTestRunRequest();
	}

	public TrackerItemId create() {
		try {
			return SafeStopWatch.measureAndReturn(() -> callApi(), (taskTimeMillis) -> logger
					.debug("Creating a new tracker item Took {}ms", Long.valueOf(taskTimeMillis)));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public CreateTestRunBuilder runOnlyAcceptedTestCases(boolean runOnlyAcceptedTestCases) {
		this.createTestRunRequest.runOnlyAcceptedTestCases(runOnlyAcceptedTestCases);
		return this;
	}

	public CreateTestRunBuilder runOnlyAcceptedTestCases() {
		runOnlyAcceptedTestCases(true);
		return this;
	}

	public CreateTestRunBuilder testCaseIds(List<TrackerItemReference> testCaseIds) {
		this.createTestRunRequest.testCaseIds(testCaseIds);
		return this;
	}

	public CreateTestRunBuilder testCaseIds(TrackerItemId... trackerItemIds) {
		return testCaseIds(getTrackerItemReferences(List.of(trackerItemIds)));
	}

	public CreateTestRunBuilder testCaseRefs(List<TrackerItemReference> testCaseIds) {
		this.createTestRunRequest.testCaseRefs(testCaseIds);
		return this;
	}

	public CreateTestRunBuilder testCaseRefs(TrackerItemId... trackerItemIds) {
		return testCaseRefs(getTrackerItemReferences(List.of(trackerItemIds)));
	}

	public CreateTestRunBuilder testSetIds(List<TrackerItemReference> testSetIds) {
		this.createTestRunRequest.testSetIds(testSetIds);
		return this;
	}

	public CreateTestRunBuilder testSetIds(TrackerItemId... trackerItemIds) {
		return testSetIds(getTrackerItemReferences(List.of(trackerItemIds)));
	}

	public CreateTestRunBuilder testSetRefs(List<TrackerItemReference> testSetRefs) {
		this.createTestRunRequest.testSetRefs(testSetRefs);
		return this;
	}

	public CreateTestRunBuilder testSetRefs(TrackerItemId... trackerItemIds) {
		return testSetRefs(getTrackerItemReferences(List.of(trackerItemIds)));
	}

	public CreateTestRunBuilder testRunModel(TrackerItem testRunModel) {
		this.createTestRunRequest.testRunModel(testRunModel);
		return this;
	}

	private List<TrackerItemReference> getTrackerItemReferences(List<TrackerItemId> testCaseIds) {
		if (CollectionUtils.isEmpty(testCaseIds)) {
			return Collections.emptyList();
		}

		return testCaseIds.stream()
				.map(trackerItem -> (TrackerItemReference) new TrackerItemReference().id(trackerItem.id()))
				.toList();
	}

	private TrackerItemId callApi() {
		try {
			return new TrackerItemId(this.testRunApi.createTestRunForTestCase(trackerId.id(), createTestRunRequest).getId());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
