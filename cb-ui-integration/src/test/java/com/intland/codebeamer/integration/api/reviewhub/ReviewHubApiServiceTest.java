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

package com.intland.codebeamer.integration.api.reviewhub;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.ReviewHubApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewTemplate;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewType;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ThresholdChangeOption;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "ReviewHubApiService")
public class ReviewHubApiServiceTest extends AbstractBaseNGTests {

	private UserApiService userService;

	private UserGroupApiService userGroupApiService;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private RoleApiService roleApiService;

	private TrackerItemApiService trackerItemApiService;

	private ReviewHubApiService reviewHubApiService;

	private DataManagerService dataManagerService;

	private User projectAdminUser;

	private Project myProject;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		ApiUser apiUser = getApplicationConfiguration().getApiUser();
		dataManagerService = new DataManagerService(getApplicationConfiguration());
		userService = dataManagerService.getUserApiService(apiUser);
		userGroupApiService = dataManagerService.getUserGroupApiService(apiUser);
		projectApiService = dataManagerService.getProjectApiService(apiUser);
		roleApiService = dataManagerService.getRoleApiService(apiUser);
		trackerItemApiService = dataManagerService.getTrackerItemApiService(apiUser);
		trackerApiService = dataManagerService.getTrackerApiService(apiUser);
		reviewHubApiService = dataManagerService.getReviewHubApiService(apiUser);

		projectAdminUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		myProject = projectApiService.createProject(getRandomText("MyProject"))
				.addUserAs(projectAdminUser, "Project Admin")
				.build();
	}

	@AfterClass(alwaysRun = true)
	public void afterMethod() {
		// Clean up after each test method
		if (myProject != null) {
			projectApiService.deleteProject(myProject.id());
		}
		if (projectAdminUser != null) {
			userService.disableUser(projectAdminUser);
		}
	}

	@Test(description = "Test create review template scenario")
	public void createReviewTemplateViaApi() {
		// Given
		String reviewTemplateName = getRandomText("Review template for MyUserStory");

		// When
		ReviewTemplate reviewTemplate = reviewHubApiService.createReviewTemplate(myProject.id(),
				reviewTemplateName,
				createReviewTemplateBuilder -> createReviewTemplateBuilder.addRoleAsModerator("Developer")
						.addUserAsModerator(projectAdminUser.getName())
						.addGroupAsModerator("Regular User")
						.addUserAsReviewer(projectAdminUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.approvedStatusThreshold(-1)
						.description(getRandomText("Review Template description"))
						.deadline(new Date())
						.createdBy(projectAdminUser)
						.rejectedStatusThreshold(-1)
						.notifyOnItemUpdate(true)
						.notifyReviewers(true)
						.minimumSignaturesRequired(1)
		);

		//Then
		assertNotNull(reviewTemplate);
		assertNotNull(reviewTemplate.id());
		assertEquals(reviewTemplate.name(), reviewTemplateName);
	}

	@Test(description = "Review can be created via API")
	public void createReviewViaApi() {
		// Given
		String testReviewItem = getRandomText("Test review item");

		// When
		Review review = createReviewByName(testReviewItem);

		// Then
		assertNotNull(review.id());
		assertEquals(review.name(), testReviewItem);
	}

	@Test(description = "Review template can be deleted via API")
	public void deleteReviewTemplateViaApi() {
		ReviewTemplate reviewTemplate = reviewHubApiService.createReviewTemplate(myProject.id(),
				getRandomText("Review template for MyUserStory"),
				createReviewTemplateBuilder -> createReviewTemplateBuilder.addRoleAsModerator("Developer")
						.addUserAsModerator(projectAdminUser.getName())
						.addGroupAsModerator("Regular User")
						.addUserAsReviewer(projectAdminUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.approvedStatusThreshold(-1)
						.description(getRandomText("Review Template description"))
						.deadline(new Date())
						.createdBy(projectAdminUser)
						.rejectedStatusThreshold(-1)
						.notifyOnItemUpdate(true)
						.notifyReviewers(true)
						.minimumSignaturesRequired(1)
		);
		assertTrue(reviewHubApiService.deleteReviewTemplate(reviewTemplate));
		assertFalse(reviewHubApiService.deleteReviewTemplate(reviewTemplate));
	}

	@Test(description = "Test cancel review via API")
	public void cancelReviewViaApi() {
		// When
		Review review = createReviewByName("Test review");

		// Then
		assertNotNull(review.id());
		assertTrue(reviewHubApiService.cancelReview(review));
		assertFalse(reviewHubApiService.cancelReview(review));
	}

	private Review createReviewByName(String reviewName) {
		String trackerName = getRandomText("MyUserStory");
		Tracker userStoryTracker = trackerApiService.createDefaultUserStoryTracker(myProject, trackerName);

		trackerItemApiService.createTrackerItem(myProject, trackerName, builder -> builder
				.name("Basic Radio")
				.description("Description for tracker item"));

		return reviewHubApiService.createReview(reviewName,
				reviewInformationBuilder -> reviewInformationBuilder
						.addProjectIdsItem(myProject.id())
						.addTrackerIdsItem(userStoryTracker.id())
						.addUserAsReviewer(projectAdminUser.getName())
						.addGroupAsReviewer("Regular User")
						.addRoleAsReviewer("Developer")
						.addUserAsModerator(projectAdminUser.getName())
						.addUserAsModerator("bond")
						.reviewType(ReviewType.TRACKER_REVIEW)
						.approvedStatusThreshold(-1)
						.approvedThresholdChangeOption(ThresholdChangeOption.SET_TO_APPROVED)
						.minimumSignaturesRequired(0)
						.rejectedStatusThreshold(-1)
						.rejectedThresholdChangeOption(ThresholdChangeOption.SET_TO_REJECTED)
						.description("Description for review item"));
	}
}
