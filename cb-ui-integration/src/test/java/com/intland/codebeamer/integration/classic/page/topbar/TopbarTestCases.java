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

package com.intland.codebeamer.integration.classic.page.topbar;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.nextgen.page.topbar.component.ProfileMenuAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "TopbarTestCases")
public class TopbarTestCases extends AbstractIntegrationClassicNGTests {

	private User regularUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		regularUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		switchToClassicUI(regularUser);
	}

	@TestCase(link = "userIsAbleToNavigateToUserAccountPage", expectedHttpErrors = { HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND })
	@Test(description = "User is able to navigate to user account page using profile option")
	public void userIsAbleToNavigateToUserAccountPage() {
		getClassicCodebeamerApplication(regularUser)
				.visitAllReviewPage()
				.getTopbarComponent()
				.getProfileMenuComponent()
				.selectProfilePhoto()
				.visitProfilePage()
				.redirectedToUserAccountPage(regularUser);
	}

	@TestCase(link = "userIsAbleToNavigateToLoginPage", expectedHttpErrors = { HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND })
	@Test(description = "User is able to navigate to login page using logout option")
	public void userIsAbleToNavigateToLoginPage() {
		getClassicCodebeamerApplication(regularUser)
				.visitAllReviewPage()
				.getTopbarComponent()
				.getProfileMenuComponent()
				.selectProfilePhoto()
				.logout()
				.redirectedToLoginPage();
	}

	@TestCase(link = "isUserProfilePhotoButtonVisible", expectedHttpErrors = { HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND })
	@Test(description = "Profile photo button is available")
	public void isUserProfilePhotoButtonVisible() {
		getClassicCodebeamerApplication(regularUser)
				.visitAllReviewPage()
				.getTopbarComponent()
				.getProfileMenuComponent()
				.assertProfileMenuComponent(ProfileMenuAssertions::assertProfilePhotoButtonIsVisible);
	}

	@TestCase(link = "profileDialogHasAllOptions", expectedHttpErrors = { HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND })
	@Test(description = "Profile photo dialog has all options")
	public void profileDialogHasAllOptions() {
		getClassicCodebeamerApplication(regularUser)
				.visitAllReviewPage()
				.getTopbarComponent()
				.getProfileMenuComponent()
				.selectProfilePhoto()
				.assertProfileMenuComponent(ProfileMenuAssertions::isReady);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(regularUser);
	}
}
