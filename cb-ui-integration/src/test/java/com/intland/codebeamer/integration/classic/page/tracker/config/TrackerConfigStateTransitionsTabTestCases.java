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

package com.intland.codebeamer.integration.classic.page.tracker.config;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.AbstractTrackerConfigTab;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "TrackerConfigStateTransitionsTabTestCases" })
public class TrackerConfigStateTransitionsTabTestCases extends AbstractIntegrationClassicNGTests {

	public static final String ALL_PERMITTED = "Assigned to, Submitted by, Owner, Developer, Product Owner, Project Admin, Scrum Master, Stakeholder, Test Engineer, Tester, Test Lead";

	public static final String DEFAULT_PERMITTED = "Submitted by, Developer, Product Owner, Project Admin, Scrum Master, Stakeholder, Test Engineer, Tester, Test Lead";

	private User activeUser;

	private Project project;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private Tracker tracker;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);

		project = projectApiService.createProjectFromTemplate();
		tracker = trackerApiService.createDefaultTaskTracker(project, "test tracker");

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
	}

	@Test(description = "user is able to navigate to state transitions tab and assert some of the elements")
	public void userIsAbleToNavigateToStateTransitionsTab() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New"))
				.trackerConfigStateTransitionsTab(AbstractTrackerConfigTab::save)
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.assertThat().is("Submit", DEFAULT_PERMITTED));
	}

	@Test(description = "user is able to add new State Transition")
	public void userIsAbleToNavigateToStateTransition() {

		String fromState = "1";
		String toState = "2";

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.openNewStateTransitionDialog()
							.setFrom(fromState)
							.setTo(toState)
							.setName("Test state")
							.save()
				)
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("New", "Suspended").assertThat().is("Test state", ALL_PERMITTED));
	}

	@Test(description = "user is able to change permitted groups")
	public void userIsAbleToChangePermittedGroups() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.openPermittedDialog()
						.deselectRole("Developer")
						.deselectRole("Scrum Master")
						.selectRole("Scrum Master")
						.deselectParticipant("Submitted by")
						.selectParticipant("Assigned to")
						.save())
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New").assertThat().is("Submit",
								"Assigned to, Product Owner, Project Admin, Scrum Master, Stakeholder, Test Engineer, Tester, Test Lead"));

	}

	@Test(description = "user is able to deselect all Permitted group and save")
	public void userIsAbleToDeselectPermittedGroupAndSave() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.openPermittedDialog()
						.selectNoOne()
						.save())
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New").assertThat().is("Submit", "Click to edit"));
	}

	@Test(description = "user is able to select all Permitted group and save")
	public void userIsAbleToSelectPermittedGroupAndSave() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.openPermittedDialog()
						.selectAll()
						.save())
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New").assertThat().is("Submit", ALL_PERMITTED));
	}

	@Test(description = "user is able to cancel Permitted selector modal")
	public void userIsAbleToCancelPermittedSelectorModal() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.openPermittedDialog()
						.cancel())
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New").getPermittedDialog().assertThat().isHidden());
	}

	@Test(description = "user is able to close Permitted selector modal")
	public void userIsAbleToClosePermittedSelectorModal() {

		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigStateTransitionsTab()
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New")
						.openPermittedDialog()
						.close())
				.trackerConfigStateTransitionsTab(tab -> tab
						.stateTransition("--", "New").getPermittedDialog().assertThat().isHidden());
	}

}
