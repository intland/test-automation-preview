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

package com.intland.codebeamer.integration.classic.page.reviewhub;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewType;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ThresholdChangeOption;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewItemReferencesAssertions;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.ReviewsDropdownAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "ReviewHubTestCases")
public class ReviewHubTestCases extends AbstractIntegrationClassicNGTests {

	private User reviewAdminUser;

	private Project project;

	private TrackerItemId basicStory;

	private TrackerItemId bug1;

	private Tracker userStoryTracker;

	private Review review;

	private TrackerItemApiService trackerItemApiService;

	private DataManagerService dataManagerService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		// Given
		dataManagerService = getDataManagerService();
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		// @formatter:off
		reviewAdminUser = dataManagerService.getUserApiServiceWithConfigUser()
				.createUser()
				.addToRegularUserGroup()
				.addToReviewAdminGroup()
				.build();

		project = dataManagerService.getProjectApiService(apiUser)
				.createProject(getRandomText("MyProject"))
				.addUserAs(reviewAdminUser, "Project Admin")
				.build();

		trackerItemApiService = dataManagerService.getTrackerItemApiService(reviewAdminUser);
		Tracker myBug = dataManagerService.getTrackerApiService(reviewAdminUser)
				.createDefaultBugTracker(project, "MyBug");

		userStoryTracker = dataManagerService.getTrackerApiService(reviewAdminUser)
				.createUserStoryTracker(project, "MyStory")
				.createReferenceFieldOfTrackers("Downstream Reference", referenceFilter -> referenceFilter
						.addTrackerFilter(project, "MyBug"), fieldProp -> fieldProp.multipleSelection(true))
				.buildAndAdd();

		bug1 = trackerItemApiService.createTrackerItem(myBug.id(), builder -> builder
				.name("Bug 1")
				.description("bug 1 description"));
		basicStory = trackerItemApiService.createTrackerItem(userStoryTracker, builder -> builder
				.name("Basic Story")
				.description("Story description")
				.setTrackerItemFor("Downstream Reference", bug1));

		review = dataManagerService.getReviewHubApiService(apiUser)
				.createReview(getRandomText("Test review item"), reviewInformationBuilder -> reviewInformationBuilder
						.addProjectIdsItem(project.id())
						.addTrackerIdsItem(userStoryTracker.id())
						.addUserAsReviewer(reviewAdminUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.addUserAsModerator(reviewAdminUser.getName())
						.addUserAsModerator("bond")
						.reviewType(ReviewType.TRACKER_REVIEW)
						.approvedStatusThreshold(-1)
						.approvedThresholdChangeOption(ThresholdChangeOption.SET_TO_APPROVED)
						.minimumSignaturesRequired(0)
						.rejectedStatusThreshold(-1)
						.rejectedThresholdChangeOption(ThresholdChangeOption.SET_TO_REJECTED)
						.description("Description for the review item"));

		switchToClassicUI(reviewAdminUser);
		// @formatter:on
	}

	@TestCase(link = "reviewsDropdownIsVisible", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "Reviews dropdown is available")
	public void reviewsDropdownIsVisible() {
		// @formatter:off
		getClassicCodebeamerApplication(reviewAdminUser).visitReviewItemPage(review)
				.assertReviewsDropdown(ReviewsDropdownAssertions::assertDropdownIsVisible);
		// @formatter:on
	}

	@TestCase(link = "reviewsDropdownHasAllOptions", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "Reviews dropdown contains all the expected options")
	public void reviewsDropdownHasAllOptions() {
		// @formatter:off
		getClassicCodebeamerApplication(reviewAdminUser).visitReviewItemPage(review)
				.getReviewsDropdownComponent()
				.reviews()
				.redirectedToReviewItemPage(review)
				.assertReviewsDropdown(ReviewsDropdownAssertions::isReadyForRegularUser);
		// @formatter:on
	}

	@TestCase(link = "isReviewAdministrationOptionVisible", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "Review administration option should be visible under reviews dropdown")
	public void isReviewAdministrationOptionVisible() {
		// @formatter:off
		getClassicCodebeamerApplication(reviewAdminUser).visitAllReviewPage()
				.getReviewsDropdownComponent()
				.reviewsMenu()
				.assertReviewsDropdown(ReviewsDropdownAssertions::isReviewAministrationOptionVisible);
		// @formatter:on
	}

	@TestCase(link = "isReviewAdminPageTitleVisible", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User should able to visit review administration page and review admin page title should be visible")
	public void isReviewAdminPageTitleVisible() {
		// @formatter:off
		getClassicCodebeamerApplication(reviewAdminUser).visitAllReviewPage()
				.getReviewsDropdownComponent()
				.reviewAdministration()
				.redirectedToReviewAdminPage()
				.getReviewAdminFormComponent(c -> c.assertThat()
						.isReviewAdminPageTitleVisible());
		// @formatter:on
	}

	@TestCase(link = "testReviewAdminPageCanBeLoaded", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "Review admin user is able to navigate to review administration page")
	public void testReviewAdminPageCanBeLoaded() {
		getClassicCodebeamerApplication(reviewAdminUser)
				.visitReviewAdminPage();
	}

	@TestCase(link = "testActivatingReviewTabs", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to navigate through the review tabs")
	public void testActivatingReviewTabs() {
		// @formatter:off
		getClassicCodebeamerApplication(reviewAdminUser)
				.visitReviewItemPage(review)
				.changeToCommentsTab()
				.reviewCommentsTab(c -> c.assertThat().isTabActive())
				.changeToStatisticsTab()
				.reviewStatisticsTab(c -> c.assertThat().isTabActive())
				.changeToHistoryTab()
				.reviewHistoryTab(c -> c.assertThat().isTabActive());
		// @formatter:on
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13479685",
			expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "Verify New Version Available Badge Under References Accordion After Change")
	public void verifyNewVersionAvailableBadgeUnderReferencesAccordionAfterChange() {
		// @formatter:off
		// When
		trackerItemApiService.updateTrackerItem(bug1, builder -> builder
				.name("Bug 1 updated")
				.description("Bug 1 description updated"));

		// Then
		getClassicCodebeamerApplication(reviewAdminUser).visitReviewItemPage(review)
				.visit()
				.getReviewItemReferencesComponent()
				.reviewItemReferences(String.valueOf(basicStory.id()))
				.redirectedToReviewItemPage(review)
				.assertReviewReferencesComponent(ReviewItemReferencesAssertions::assertNewVersionAvailableBadgeForReferences);
		// @formatter:on
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (project != null) {
			dataManagerService.getProjectApiService(reviewAdminUser).deleteProject(project.id());
		}
		if (reviewAdminUser != null) {
			dataManagerService.getUserApiServiceWithConfigUser().disableUser(reviewAdminUser);
		}
	}
}
