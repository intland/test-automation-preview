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

package com.intland.codebeamer.integration.api.baseline;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.BaselineDetails;
import com.intland.swagger.client.model.TrackerBaselineReference;

@Test(groups = "BaselineApiService")
public class BaselineApiServiceTest extends AbstractBaseNGTests {

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private BaselineApiService baselineApiService;

	private Project project;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		projectApiService = new ProjectApiService(getApplicationConfiguration());
		trackerApiService = new TrackerApiService(getApplicationConfiguration());
		baselineApiService = new BaselineApiService(getApplicationConfiguration());

		project = projectApiService.createProjectFromTemplate();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		projectApiService.deleteProject(project);
	}

	@Test(description = "Project baseline can be created via API")
	public void createProjectBaselineViaApi() {
		// Given

		// When
		Baseline baseline = baselineApiService.createProjectBaseline(getRandomText("MyTestBaseline"), project);

		// Then
		assertNotNull(baseline.id());
		assertTrue(isProjectBaselineExists(Integer.valueOf(baseline.id().id()), project.id()));
	}

	@Test(description = "Tracker baseline can be created via API")
	public void createTrackerBaselineViaApi() {
		// Given
		Tracker taskTracker = trackerApiService.createDefaultTaskTracker(project, "MyTaskCustom");

		// When
		String baselineName = getRandomText("MyTestBaseline");
		Baseline baseline = baselineApiService.createTrackerBaseline(baselineName, project, taskTracker);

		// Then
		assertNotNull(baseline.id());
		List<AbstractReference> taskTrackerBaselines = getTrackerBaselines(taskTracker.id());
		Optional<AbstractReference> createdBaseline = taskTrackerBaselines.stream()
				.filter(b -> Integer.valueOf(baseline.id().id()).equals(b.getId()))
				.findFirst();

		assertTrue(createdBaseline.isPresent());
		assertEquals(createdBaseline.get().getName(), baselineName);
		assertTrue(createdBaseline
				.filter(TrackerBaselineReference.class::isInstance)
				.isPresent());
	}

	@Test(description = "Baseline can be deleted via API")
	public void deleteBaselineViaApi() {
		// Given
		// When
		Baseline baseline = baselineApiService.createProjectBaseline(getRandomText("MyTestBaseline"), project);
		assertNotNull(baseline.id());
		assertTrue(isProjectBaselineExists(Integer.valueOf(baseline.id().id()), project.id()));

		// Then
		baselineApiService.deleteBaseline(baseline.id());
		assertFalse(isProjectBaselineExists(Integer.valueOf(baseline.id().id()), project.id()));
	}

	private boolean isProjectBaselineExists(Integer baselineId, ProjectId projectId) {
		return getProjectBaselines(projectId).stream()
				.map(BaselineDetails::getId)
				.anyMatch(baselineId::equals);
	}
}
