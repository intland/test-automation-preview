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

package com.intland.codebeamer.integration.classic.page.baseline;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "BaselineTestCases")
public class BaselineTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private Baseline baseline;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		project = getProjectApiService().createProjectFromTemplate();
		baseline = getBaselineApiService().createProjectBaseline("Default", project);
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getProjectApiService().deleteProject(project);
	}

	@Test(description = "User is able to see the Project Baseline page and controls")
	public void userIsAbleToSeeBaselinePageAndControls() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitBaselineLandingPage(project)
				.baselineActionBarComponent(c -> c.assertThat().isNewBaselineButtonVisible())
				.baselinesAccordionContentComponent(c -> {
					c.assertThat()
							.isAccordionContentOpened()
							.isLeftBaselineSelectorButtonVisible()
							.isRightBaselineSelectorButtonVisible();
				});
	}

	@Test(description = "User is able to select the Left Baseline")
	public void userIsAbleToSelectLeftBaseline() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitBaselineLandingPage(project)
				.baselinesAccordionContentComponent(c -> {
					c.assertThat()
							.isAccordionContentOpened()
							.isLeftBaselineSelectorButtonVisible();
				})
				.getBaselinesAccordionContentComponent()
				.openLeftBaselineSelectDialog()
				.select(baseline.id())
				.redirectedToBaselinePage(project)
				.baselinesAccordionContentComponent(c -> c.assertThat().isCorrectLeftBaselineSelected(baseline));
	}

	@Test(description = "User is able to select the Right Baseline")
	public void userIsAbleToSelectRightBaseline() {
		// When / Then
		getClassicCodebeamerApplication(activeUser).visitBaselineLandingPage(project)
				.baselinesAccordionContentComponent(c -> {
					c.assertThat()
							.isAccordionContentOpened()
							.isRightBaselineSelectorButtonVisible();
				})
				.getBaselinesAccordionContentComponent()
				.openRightBaselineSelectDialog()
				.select(baseline.id())
				.redirectedToBaselinePage(project)
				.baselinesAccordionContentComponent(c -> c.assertThat().isCorrectRightBaselineSelected(baseline));
	}

	@Test(description = "No difference warning message test for identical Baselines")
	public void noDifferenceBetweenTwoIdenticalBaselines() {
		// Given
		Baseline baselineIdentical = getBaselineApiService().createProjectBaseline("Identical", project);

		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitBaselineLandingPage(project)
				.baselinesAccordionContentComponent(c -> {
					c.assertThat()
							.isAccordionContentOpened()
							.isLeftBaselineSelectorButtonVisible()
							.isRightBaselineSelectorButtonVisible();
				})
				.getBaselinesAccordionContentComponent()
				.selectLeftBaseline(baseline)
				.baselinesAccordionContentComponent(c -> c.assertThat().isCorrectLeftBaselineSelected(baseline))
				.getBaselinesAccordionContentComponent()
				.selectRightBaseline(baselineIdentical)
				.baselinesAccordionContentComponent(c ->
						c.assertThat()
								.isCorrectRightBaselineSelected(baselineIdentical)
								.isCompareBaselinesButtonEnabled())
				.getBaselinesAccordionContentComponent()
				.compare()
				.redirectedToBaselineComparePage()
				.baselineCompareResultsAccordionContentComponent(
						c -> c.assertThat().isAccordionContentOpened().isIdenticalBaselinesWarningVisible());
	}

	private ProjectApiService getProjectApiService() {
		return getDataManagerService().getProjectApiService(activeUser);
	}

	private BaselineApiService getBaselineApiService() {
		return getDataManagerService().getBaselineApiService(activeUser);
	}

}
