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

package com.intland.codebeamer.integration.api.traceability;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.builder.RenderingMethod;
import com.intland.codebeamer.integration.api.service.filter.model.FieldAndTrackerId;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reports.TraceabilityApiService;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerField;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "TraceabilityApiService")
public class TraceabilityApiServiceTest extends AbstractBaseNGTests {

	private TraceabilityApiService traceabilityApiService;

	private DataManagerService dataManagerService;

	private UserApiService userService;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private TrackerFieldApiService trackerFieldApiService;

	private User user;

	private Project project;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		dataManagerService = new DataManagerService(getApplicationConfiguration());
		userService = dataManagerService.getUserApiService(apiUser);
		projectApiService = dataManagerService.getProjectApiService(apiUser);
		trackerApiService = dataManagerService.getTrackerApiService(apiUser);
		trackerItemApiService = dataManagerService.getTrackerItemApiService(apiUser);
		trackerFieldApiService = dataManagerService.getTrackerFieldApiService(apiUser);

		user = userService.createUser()
				.addToRegularUserGroup()
				.build();

		traceabilityApiService = dataManagerService.getTraceabilityApiService(user);

		project = projectApiService.createProject(getRandomText("Test Project"))
				.addUserAs(user, "Project Admin")
				.build();
	}

	@AfterClass(alwaysRun = true)
	public void afterMethod() {
		// Clean up after each test method
		if (project != null) {
			projectApiService.deleteProject(project.id());
		}
		if (userService.getDefaultApiClient() != null) {
			userService.disableUser(user);
		}
	}

	@Test(description = "Test create traceability report")
	public void createTraceabilityReportViaApi() {
		// Given
		String traceabilityReportName = "Demo Report";
		String trackerName = "User Story Tracker";
		Tracker tracker = trackerApiService.createDefaultUserStoryTracker(project, trackerName);

		trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name("Demo US 1")
				.description("Demo user story"));

		TrackerField summaryField = trackerFieldApiService.findFieldReference(tracker.id(), "Summary");

		// When
		Report report = traceabilityApiService.savePresetReport(traceabilityReportName,
				traceabilitySaveReportRequestBuilder -> traceabilitySaveReportRequestBuilder
						.cbQL("project.id IN (%d) AND tracker.id IN (%d)".formatted(project.id().id(), tracker.id().id()))
						.intelligentTableViewConfig(
								cbQLIntelligentTableViewConfigurationBuilder -> cbQLIntelligentTableViewConfigurationBuilder
										.addInitialFieldIdsItem(summaryField)
										.addInitialFieldTrackerIdsItem(tracker.id())
										.addLevelSettingsItem("1", levelSettingbuilder -> levelSettingbuilder
												.cbQL("project.id IN (%d)".formatted(project.id().id()))
												.children(false)
												.downstream()
												.downstreamAssociations()
												.addFieldIdsItem(new FieldAndTrackerId(summaryField.getId(), 0))
												.foldersAndInformation()
												.upstream()
												.upstreamAssociations())
										.renderingMethod(RenderingMethod.HORIZONTALLY))
						.isPublic(Boolean.FALSE)
						.name(traceabilityReportName)
						.projectId(project)
						.traceabilityPresetProjectId(project)
		);

		// Then
		assertNotNull(report.id());
		assertEquals(report.name(), traceabilityReportName);
	}
}
