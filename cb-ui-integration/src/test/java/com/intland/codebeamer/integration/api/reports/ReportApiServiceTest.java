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

package com.intland.codebeamer.integration.api.reports;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reports.ReportsApiService;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "ReportApiService")
public class ReportApiServiceTest extends AbstractBaseNGTests {

	private UserApiService userService;

	private ProjectApiService projectApiService;

	private Project project;

	private User projectAdminUser;

	private ReportsApiService reportsApiService;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		DataManagerService dataManagerService = new DataManagerService(getApplicationConfiguration());
		userService = dataManagerService.getUserApiService(apiUser);
		projectApiService = dataManagerService.getProjectApiService(apiUser);

		projectAdminUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		project = projectApiService.createProject(getRandomText("MyProject"))
				.addUserAs(projectAdminUser, "Project Admin")
				.build();

		reportsApiService = dataManagerService.getReportsApiService(projectAdminUser);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		if (project != null) {
			projectApiService.deleteProject(project.id());
		}
		if (projectAdminUser != null) {
			userService.disableUser(projectAdminUser);
		}
	}

	@Test(description = "Report can be created via API")
	public void createReportViaApi() {
		// Given
		String cbql = "project.id IN (%d)".formatted(project.id().id());
		String name = getRandomText("Test report name");

		// When
		Report report = reportsApiService.createReport(name,
				reportInformationBuilder -> reportInformationBuilder
						.cbql(cbql));

		// Then
		assertNotNull(report.id());
		assertNotNull(report.name());
		assertNotNull(report.queryString());
		assertEquals(name, report.name());
		assertEquals(cbql, report.queryString());
	}

	@Test(description = "Reports can be deleted via API")
	public void deleteReportViaApi() {
		// Given
		String cbql = "project.id IN (%d)".formatted(project.id().id());
		String name = getRandomText("Test report name");

		// When
		Report report = reportsApiService.createReport(name,
				reportInformationBuilder -> reportInformationBuilder
						.cbql(cbql));
		boolean value = reportsApiService.deleteReports(report);

		// Then
		assertTrue(value);
		assertThrows(IllegalStateException.class, () -> {
			reportsApiService.getReport(report.id().id());
		});
	}

	@Test(description = "Report can be get via API")
	public void getReportViaApi() {
		// Given
		String cbql = "project.id IN (%d)".formatted(project.id().id());
		String name = getRandomText("Test report name");

		// When
		Report report = reportsApiService.createReport(name,
				reportInformationBuilder -> reportInformationBuilder
						.cbql(cbql));
		Report reportResponse = reportsApiService.getReport(report.id().id());

		// Then
		assertNotNull(reportResponse.id());
		assertNotNull(reportResponse.name());
		assertNotNull(reportResponse.queryString());
		assertEquals(report, reportResponse);
	}
}