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

package com.intland.codebeamer.integration.api.service.workingset;

import static com.intland.codebeamer.integration.common.tracker.TrackerLayoutLabel.DESCRIPTION;
import static com.intland.codebeamer.integration.common.tracker.TrackerLayoutLabel.NAME;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;

@Test(groups = "WorkingSetApiServiceTest")
public class WorkingSetApiServiceTest extends AbstractBaseNGTests {

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private WorkingSetApiService workingSetApiService;

	private Project project;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		projectApiService = new ProjectApiService(getApplicationConfiguration());
		trackerApiService = new TrackerApiService(getApplicationConfiguration());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration());
		workingSetApiService = new WorkingSetApiService(getApplicationConfiguration());
		project = projectApiService.createProjectFromTemplate();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		projectApiService.deleteProject(project.id());
	}

	@Test(description = "Working-Set can be created via API")
	public void createWorkingSetViaApi() {
		//GIVEN
		Tracker tracker = trackerApiService.createDefaultRequirementTracker(project, "Requirement 1");

		//WHEN
		WorkingSetId workingSetId = workingSetApiService.createWorkingSet("test-ws-1", project.id(), builder -> builder
				.color(HtmlColor.COLOR_858585)
				.postfix("test-postfix")
				.description("test-description")
				.addTracker(tracker, WorkingSetTrackerBuilder::shared));

		//THEN
		assertNotNull(workingSetId);
		Tracker wsTracker = workingSetApiService.findTrackerInWorkingSetByName(workingSetId, "Requirement 1");
		assertEquals(wsTracker.name(), tracker.name());
	}

	@Test(description = "Working-set trackers can be merged via API")
	public void mergeWorkingSetTrackerViaApi() {
		//GIVEN
		Tracker tracker = trackerApiService.createDefaultRequirementTracker(project, "Requirement 2");
		TrackerItemId itemToUpdate = trackerItemApiService.createTrackerItem(tracker, builder -> builder
				.name("item")
				.description("description"));
		TrackerItemId itemToDelete = trackerItemApiService.createTrackerItem(tracker, builder -> builder
				.name("item to delete")
				.description("description"));

		WorkingSetId workingSetId = workingSetApiService.createWorkingSet("test-ws-2", project.id(), builder -> builder
				.addTracker(tracker));

		trackerItemApiService.updateTrackerItem(itemToUpdate, builder -> builder
				.name("item v2"));
		trackerItemApiService.deleteTrackerItem(itemToDelete);
		TrackerItemId newItem = trackerItemApiService.createTrackerItem(tracker, builder -> builder
				.name("new item")
				.description("description"));

		//WHEN
		Tracker wsTracker = workingSetApiService.findTrackerInWorkingSetByName(workingSetId, "Requirement 2");
		workingSetApiService.mergeTrackers(tracker.id(), wsTracker.id(), builder -> builder
				.addUpdatedFieldAndMarkAsMerged(itemToUpdate, DESCRIPTION.getFieldId(), NAME.getFieldId())
				.addCopyToTarget(newItem)
				.addDeleteOnTarget(itemToDelete));

		//THEN
		List<TrackerItem> itemsOnWorkingSet = trackerItemApiService.findTrackerItemByNames(wsTracker.id(), "item v2", "new item",
				"item", "item to delete");
		assertEquals(itemsOnWorkingSet.size(), 2);
		assertTrue(itemsOnWorkingSet.stream().anyMatch(item -> item.name().equals("new item")));
		assertTrue(itemsOnWorkingSet.stream().anyMatch(item -> item.name().equals("item v2")));
	}

}