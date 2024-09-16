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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.utility.DocumentFileInfo;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigGeneralTab;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = { "TrackerConfigurationGeneralTabTest" })
public class TrackerConfigurationGeneralTabTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private Tracker tracker;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		tracker = trackerApiService.createDefaultTaskTracker(project, "Test Tracker");

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		trackerApiService.deleteTracker(tracker.id());
		projectApiService.deleteProject(project);
	}

	@Test(description = "User is able to change the name of a tracker")
	public void testGeneralConfigTab() {
		// GIVEN
		String newTrackerName = "MyNewTracker";
		String newTrackerKey = "MyNewKey";
		String newDescription = "New description";
		HtmlColor newColor = HtmlColor.COLOR_5F5F5F;

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigGeneralTab()
				.trackerConfigGeneralTab(c -> c
						.setName(newTrackerName)
						.setKeyName(newTrackerKey)
						.setDescription(newDescription)
						.setColor(newColor)
						.setBreadcrumbIcon(getFilePath("test-img.png"))
						.setDefaultLayoutToTable()
						.setDefaultLayoutToTableEditDocumentView()
						.setDefaultLayoutToKanbanBoard()
						.setWorkflowActive(true)
						.setOnlyWorkflowActionCreatesReferringItems(true)
						.setUseQuickTransitions(true)
						.setVisibility(true)
						.stOutlineShowAncestorItems(true)
						.setOutlineShowDescendantItems(true)
						.setAvailableAsTemplate(true)
						.setWorkingSetShared(true)
						.setItemCountVisibility(false)
						.setReferenceVisibility(false)
						.save()
				)
				.trackerConfigGeneralTab(c -> c.assertThat()
						.isTabActive()
						.nameEqualsTo(newTrackerName)
						.keyNameEqualsTo(newTrackerKey)
						.descriptionEqualsTo(newDescription)
						.assertAvailableAsTemplate(true)
						.assertWorkflowCheckbox(true)
						.assertReferringItemsCheckbox(true)
						.assertTransitionsCheckbox(true)
						.assertVisibilityCheckbox(true)
						.assertOutlineShowAncestorItemsCheckbox(true)
						.assertOutlineShowDesendantItemsCheckbox(true)
						.assertWorkingSetSharedCheckbox(true)
						.assertItemCountVisibilityCheckbox(false)
						.assertReferenceVisibilityCheckbox(false)
						.assertColor(newColor)
						.assertBreadcrumbsIconUploaded(createDocumentFileInfoFromPath(getFilePath("test-img.png")))
				)
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@Test(description = "User is able to select all options from default layout dropdown")
	public void testDefaultLayoutSelect() {
		// GIVEN
		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigGeneralTab()
				.trackerConfigGeneralTab(TrackerConfigGeneralTab::setDefaultLayoutToTable)
				.trackerConfigGeneralTab(c -> c.assertThat().assertDefaultLayoutIsTable())
				.trackerConfigGeneralTab(TrackerConfigGeneralTab::setDefaultLayoutToDocumentView)
				.trackerConfigGeneralTab(c -> c.assertThat().assertDefaultLayoutIsDocumentView())
				.trackerConfigGeneralTab(TrackerConfigGeneralTab::setDefaultLayoutToTableEditDocumentView)
				.trackerConfigGeneralTab(c -> c.assertThat().assertDefaultLayoutIsDocumentEdit())
				.trackerConfigGeneralTab(TrackerConfigGeneralTab::setDefaultLayoutToKanbanBoard)
				.trackerConfigGeneralTab(c -> c.assertThat().assertDefaultLayoutIsKanbanBoard());
	}

	@Test(description = "ReferringItemsCheckbox and TransitionsCheckbox are disabled if WorkflowCheckbox not selected")
	public void testTransitionAndReferringItemsDisabledIfWorkflowNotChecked() {
		// GIVEN
		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigGeneralTab()
				.trackerConfigGeneralTab(c -> c
						.setWorkflowActive(false)
				)
				.trackerConfigGeneralTab(c -> c.assertThat()
						.isTabActive()
						.referringItemsCheckboxIsDisabled()
						.transitionsCheckboxIsDisabled()
				);

	}

	private DocumentFileInfo createDocumentFileInfoFromPath(Path path) {
		try {
			return new DocumentFileInfo(path.getFileName().toString(), Files.probeContentType(path),
					Long.valueOf(Files.size(path)));
		} catch (IOException e) {
			throw new IllegalStateException("Failed to create DocumentFileInfo from path: %s".formatted(path), e);
		}
	}
}