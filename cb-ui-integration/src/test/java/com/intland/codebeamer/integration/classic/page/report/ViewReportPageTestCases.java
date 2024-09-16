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

package com.intland.codebeamer.integration.classic.page.report;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportActionBarAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ViewReportPageTestCases")
public class ViewReportPageTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private Report report;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		report = createReport();
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
		getDataManagerService().getReportsApiService(activeUser).deleteReports(report);
	}

	@Test(description = "User is able to view on edit report icon")
	public void userIsAbleToViewEditIcon() {
		getClassicCodebeamerApplication(activeUser)
				.visitReportPage(report)
				.assertReportActionBarComponent(ReportActionBarAssertions::isEditReportIconReady);
	}

	@Test(description = "User is able to navigate to edit report page")
	public void userIsAbleToNavigateToEditReportPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitReportPage(report)
				.getReportActionBarComponent()
				.editReportPage()
				.redirectedToEditReportPage(report);
	}

	private Report createReport() {
		String queryString = "project.id IN (%d)".formatted(project.id().id());
		String reportName = "Test Report Name %d".formatted(project.id().id());
		return getDataManagerService().getReportsApiService(activeUser).createReport(reportName,
				reportInformationBuilder -> reportInformationBuilder.cbql(queryString));
	}
}
