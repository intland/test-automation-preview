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

package com.intland.codebeamer.integration.classic.page.reviewhub.editreview;

import java.util.Date;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.ReviewHubApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewType;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.util.HttpStatus;

import net.datafaker.Faker;

@Test(groups = "EditReviewHubTestCases")
public class EditReviewHubTestCases extends AbstractIntegrationClassicNGTests {

	private UserApiService userService;

	private ProjectApiService projectApiService;

	private ReviewHubApiService reviewHubApiService;

	private User projectAdminUser;

	private Project myProject;

	private Tracker userStoryTracker;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		DataManagerService dataManagerService = getDataManagerService();
		userService = dataManagerService.getUserApiService(apiUser);
		projectApiService = dataManagerService.getProjectApiService(apiUser);

		projectAdminUser = userService.createUser()
				.addToRegularUserGroup()
				.addToReviewAdminGroup()
				.build();

		myProject = projectApiService.createProject(getRandomText("MyProject"))
				.addUserAs(projectAdminUser, "Project Admin")
				.build();
		userStoryTracker = dataManagerService.getTrackerApiService(apiUser).createDefaultUserStoryTracker(myProject, "MyUserStory");
		dataManagerService.getTrackerItemApiService(apiUser).createTrackerItem(myProject, "MyUserStory", builder -> builder
				.name("Basic Radio")
				.description("Description for tracker item"));

		reviewHubApiService = new ReviewHubApiService(this.getApplicationConfiguration());
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (myProject != null) {
			projectApiService.deleteProject(myProject.id());
		}
		if (projectAdminUser != null) {
			userService.disableUser(projectAdminUser);
		}
	}

	@TestCase(link = "verifyEditReview", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to edit the review")
	public void verifyEditReview() {
		Review review = reviewHubApiService.createReview(getRandomText("Test review item"),
				reviewInformationBuilder -> reviewInformationBuilder
						.addProjectIdsItem(myProject.id())
						.addTrackerIdsItem(userStoryTracker.id())
						.addUserAsReviewer(projectAdminUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.addUserAsModerator("bond")
						.addUserAsModerator(projectAdminUser.getName())
						.reviewType(ReviewType.TRACKER_REVIEW)
						.approvedStatusThreshold(-1)
						.minimumSignaturesRequired(0)
						.rejectedStatusThreshold(-1)
						.description("Description for review item"));

		Faker faker = new Faker();
		getClassicCodebeamerApplication(projectAdminUser)
				.visitReviewItemPage(review)
				.getReviewActionBarComponent()
				.reviewMoreMenu()
				.editReview()
				.editReviewDialogFormComponent(editReview -> {
					editReview.setReviewName(getRandomString());
					editReview.reviewDescription(getRandomString());
					editReview.notifyReviewers();
					editReview.notifyModerators();
					editReview.sendNotificationForEvents();
					editReview.requireSignatureForModerators();
					editReview.requireSignatureForReviewers();
					editReview.setMinimumNumberOfSignature(String.valueOf(faker.number().numberBetween(1, 10)));
					editReview.setDeadLine(new Date().toString());
					editReview.firstRuleAllCheckbox();
					editReview.setFirstRuleNumberInput(String.valueOf(faker.number().numberBetween(1, 10)));
					editReview.secondRuleAllCheckbox();
					editReview.setSecondRuleNumberInput(String.valueOf(faker.number().numberBetween(1, 10)));
				})
				.save(review)
				.redirectToReviewItemPage();
	}
}
