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

package com.intland.codebeamer.integration.classic.page.projectadmin;

import java.time.LocalDate;
import java.util.Date;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.projectadmin.component.audittab.ProjectAdminAuditTrailTabComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectAdminAuditTabTestCases")
public class ProjectAdminAuditTabTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private Tracker tracker;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		project = getDataManagerService()
				.getProjectApiService(activeUser)
				.createProjectFromTemplate();

		tracker = getDataManagerService().getTrackerApiService(activeUser).createDefaultBugTracker(project, "test tracker");

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(activeUser).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "Validate audit trail tab is active")
	public void auditTrailTabCanBeActivate() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminAuditTrailTab()
				.projectAdminAuditTab(formComponent -> formComponent.assertThat().isTabActive());
	}

	@Test(description = "Able to load search result")
	public void auditTrailTabLoadSearchResultTest() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminAuditTrailTab()
				.projectAdminAuditTab(tab -> tab.selectTrackers(tracker)
						.deselectEntity(AuditEntity.BASELINE_CHANGES)
						.userSpecifiedAfterDate(createDate(2024, 8, 7))
						.userSpecifiedBeforeDate(createDate(2024, 8, 24))
						.submit())
				.projectAdminAuditTab(ProjectAdminAuditTrailTabComponent::getSearchResult);
	}

	@Test(description = "User is able to use audit trail tab form")
	public void auditTrailTabPageIsReady() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminAuditTrailTab()
				.projectAdminAuditTab(c -> c.assertThat().isReady());
	}

	private Date createDate(int year, int month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return new Date(localDate.toEpochDay());
	}
}