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
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewStatisticsAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "ReviewStatisticsPageTest")
public class ReviewStatisticsPageTest extends AbstractIntegrationClassicNGTests {

	private User user;

	private Review review;

	private Project project;

	private Tracker userStoryTracker;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		// Given
		ApiUser apiUser = getApplicationConfiguration().getApiUser();

		DataManagerService dataManagerService = getDataManagerService();

		user = dataManagerService.getUserApiService(apiUser)
				.createUser()
				.addToRegularUserGroup()
				.build();

		project = dataManagerService.getProjectApiService(apiUser)
				.createProject(getRandomText("MyProject"))
				.addUserAs(user, "Project Admin")
				.build();

		userStoryTracker = dataManagerService.getTrackerApiService(apiUser)
				.createDefaultUserStoryTracker(project, "MyUserStory");

		dataManagerService.getTrackerItemApiService(apiUser)
				.createTrackerItem(project, "MyUserStory", builder -> builder
						.name("Basic Radio")
						.description("Description for tracker item"));

		review = dataManagerService.getReviewHubApiService(apiUser)
				.createReview(getRandomText("Test review item"), reviewInformationBuilder -> reviewInformationBuilder
						.addProjectIdsItem(project.id())
						.addTrackerIdsItem(userStoryTracker.id())
						.addUserAsReviewer(user.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.addUserAsModerator(user.getName())
						.addUserAsModerator("bond")
						.reviewType(ReviewType.TRACKER_REVIEW)
						.approvedStatusThreshold(-1)
						.approvedThresholdChangeOption(ThresholdChangeOption.SET_TO_APPROVED)
						.minimumSignaturesRequired(0)
						.rejectedStatusThreshold(-1)
						.rejectedThresholdChangeOption(ThresholdChangeOption.SET_TO_REJECTED)
						.description("Description for the review item"));
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(user).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(user);
	}

	@TestCase(link = "reviewStatisticsPageCanBeLoaded", expectedHttpErrors = HttpStatus.FORBIDDEN)
	@Test(description = "Review statistics page is ready to visit")
	public void reviewStatisticsPageCanBeLoaded() {
		getClassicCodebeamerApplication(user)
				.visitReviewStatisticsPage(review);
	}

	@TestCase(link = "reviewHubStatisticsPageIsReady", expectedHttpErrors = HttpStatus.FORBIDDEN)
	@Test(description = "Review statistics fields are ready")
	public void reviewHubStatisticsPageIsReady() {
		getClassicCodebeamerApplication(user)
				.visitReviewStatisticsPage(review)
				.assertReviewStatisticsComponent(ReviewStatisticsAssertions::isReady);
	}
}