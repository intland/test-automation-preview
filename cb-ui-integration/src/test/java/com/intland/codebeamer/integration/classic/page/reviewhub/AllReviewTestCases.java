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

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewBreadcrumbAssertions;
import com.intland.codebeamer.integration.classic.page.reviewhub.component.AllReviewListAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "AllReviewTestCases")
public class AllReviewTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser()
				.createUser()
				.addToRegularUserGroup()
				.build();
		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "userIsAbleToValidateReviewBreadCrumb", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to Validate Review Bread Crumb")
	public void userIsAbleToValidateReviewBreadCrumb() {
		//When /Then
		getClassicCodebeamerApplication(activeUser)
				.visitAllReviewPage()
				.assertAllReviewBreadcrumbComponent(AllReviewBreadcrumbAssertions::isReady);
	}

	@TestCase(link = "userIsAbleToViewAllReview", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to view All Review Page")
	public void userIsAbleToViewAllReview() {
		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitAllReviewPage()
				.assertAllReviewListComponent(assertions -> assertions
						.isHeaderReady()
						.isButtonsReady());
	}

	@TestCase(link = "userIsAbleToViewOpenReviews", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to view open reviews")
	public void userIsAbleToViewOpenReviews() {
		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitAllReviewPage()
				.changeToOpenReviewTab()
				.assertAllReviewListComponent(AllReviewListAssertions::isButtonsReady);
	}

	@TestCase(link = "userIsAbleToViewClosedReviews", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "User is able to view close reviews")
	public void userIsAbleToViewClosedReviews() {
		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitAllReviewPage()
				.changeToCloseReviewTab()
				.assertAllReviewListComponent(AllReviewListAssertions::isButtonsReady);
	}

	@TestCase(link = "activatingAllReviewsTab", expectedHttpErrors = { HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN })
	@Test(description = "user is able to check open and close review tab")
	public void activatingAllReviewsTab() {
		// When / Then
		getClassicCodebeamerApplication(activeUser)
				.visitAllReviewPage()
				.changeToOpenReviewTab()
				.assertAllReviewListComponent(AllReviewListAssertions::validateOpenReviewTab)
				.changeToCloseReviewTab()
				.assertAllReviewListComponent(AllReviewListAssertions::validateCloseReviewTab);
	}
}
