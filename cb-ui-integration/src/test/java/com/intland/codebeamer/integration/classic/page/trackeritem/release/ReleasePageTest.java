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

package com.intland.codebeamer.integration.classic.page.trackeritem.release;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ReleasePageTest")
public class ReleasePageTest extends AbstractIntegrationClassicNGTests {

	private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.FORMAT1;

	private User user;

	private ProjectApiService projectApiService;

	private TrackerItemApiService trackerItemApiService;

	@Override
	protected void generateDataBeforeClass() {
		user = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(user);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(user);
	}

	@Test(description = "User is able edit release gantt chart bars")
	public void testEditReleaseGanttChart() {
		// GIVEN

		getDataManagerService().getProjectApiService(user);
		Project project = projectApiService.createProjectFromTemplate();

		Tracker releaseTracker = getDataManagerService().getTrackerApiService(user)
				.createReleaseTracker(project, "Releases And Sprints").buildAndAdd();

		TrackerItemId release = trackerItemApiService.createTrackerItem(releaseTracker.id(), builder -> builder
				.name("Release")
				.setDateFor("Start Date", getDate(2055, 5, 1))
				.setDateFor("Planned Release Date", getDate(2055, 7, 30))
				.description("Release description"));

		trackerItemApiService.createTrackerItem(releaseTracker.id(), builder -> builder
				.name("Sprint 1")
				.setDateFor("Start Date", getDate(2055, 5, 1))
				.setDateFor("Planned Release Date", getDate(2055, 6, 30))
				.description("Sprint 1 description")
				.setTrackerItemFor("Parent", release));

		trackerItemApiService.createTrackerItem(releaseTracker.id(), builder -> builder
				.name("Sprint 2")
				.setDateFor("Start Date", getDate(2055, 6, 30))
				.setDateFor("Planned Release Date", getDate(2055, 7, 30))
				.description("Sprint 2 description")
				.setTrackerItemFor("Parent", release));

		// WHEN THEN
		try {
			getClassicCodebeamerApplication(user)
					.visitTrackerItemReleasePage(release)
					.releaseGanttChartComponent(c -> c.openReleaseGanttChart()
							.editReleaseBar(1)
							.setStartDate(2055, 4, 25, DEFAULT_DATE_FORMAT)
							.setEndDate(2055, 8, 5, DEFAULT_DATE_FORMAT)
							.save())
					.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
					.releaseGanttChartComponent(c -> c.moveStartDateOfReleaseBar(2, 13)
							.moveEndDateOfReleaseBar(2, 80))
					.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
					.releaseGanttChartComponent(c -> c.closeReleaseGanttChart());
		} finally {
			projectApiService.deleteProject(project);
		}
	}

	private Date getDate(int year, int month, int day) {
		return Date.from(LocalDate.of(year, month, day)
				.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

}