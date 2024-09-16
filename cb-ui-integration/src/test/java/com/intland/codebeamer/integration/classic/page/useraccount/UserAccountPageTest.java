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

package com.intland.codebeamer.integration.classic.page.useraccount;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.user.UserDetails;
import com.intland.codebeamer.integration.classic.page.useraccount.component.UserAccountPageAssertions;
import com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar.UserAccountActionBarAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "UserAccountPageTest")
public class UserAccountPageTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private User regularUser;

	private UserApiService userApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
		activeUser = userApiService.createUser()
				.addToRegularUserGroup()
				.addToAccountAdminGroup()
				.build();

		regularUser = userApiService.createUser()
				.addToRegularUserGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (activeUser != null) {
			userApiService.disableUser(activeUser);
		}
		if (regularUser != null) {
			userApiService.disableUser(regularUser);
		}
	}

	@Test(description = "ActiveUser is able to navigate to UserAccountPage")
	public void testUserAccountPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser);
	}

	@Test(description = "User is able to navigate to MyUserAccountPage")
	public void testMyUserAccountPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage();
	}

	@Test(description = "All fields and field labels should visible on UserAccountPage")
	public void testIsUserAccountPageFieldsVisible() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser)
				.assertUserAccountFormComponent(UserAccountPageAssertions::isReady);
	}

	@Test(description = "All actionbar links should be visible on MyUserAccountPage")
	public void testIsMyUserAccountPageActionBarOptionsVisible() {
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage()
				.assertUserAccountActionBarPageComponent(
						UserAccountActionBarAssertions::isReady);
	}

	@Test(description = "User is able to navigate to the UserAccountEditPage from UserAccountPage")
	public void testRedirectToUserAccountEditPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser)
				.getUserAccountActionBarComponent()
				.visitUserAccountEditPage();
	}

	@Test(description = "User is able to navigate to the PersonalWikiPage from MyUserAccountPage")
	public void testRedirectToViewPersonalWikiPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitPersonalWikiPage();
	}

	@Test(description = "User is able to navigate to the ChangePasswordPage from MyUserAccountPage")
	public void testRedirectToChangePasswordPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitChangePasswordPage();
	}

	@Test(description = "User is able to navigate to the EditProfilePhotoPage from UserAccountPage")
	public void testRedirectToEditProfilePhotoPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser)
				.getUserAccountActionBarComponent()
				.visitEditProfilePhotoPage();
	}

	@Test(description = "User is able to navigate to the EditAccountSshKeyPage from UserAccountPage")
	public void testRedirectToEditAccountSshKeyPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser)
				.getUserAccountActionBarComponent()
				.visitEditAccountSshKeyPage();
	}

	@Test(description = "Validate user details on UserAccountPage")
	public void testUserDataOnUserAccountPage() {
		UserDetails userDetails = userApiService.convertToUserDetails(findUserById(regularUser.getId())
				.orElseThrow(() -> new IllegalStateException("Can't find User with id %s".formatted(activeUser.getId()))));

		getClassicCodebeamerApplication(activeUser)
				.visitUserAccountPage(regularUser)
				.userAccountComponent(c -> c.assertThat()
						.hasUserName(userDetails.getUsername())
						.hasAccountId(userDetails.getId())
						.hasTitle(userDetails.getTitle())
						.hasFirstName(userDetails.getFirstName())
						.hasLastName(userDetails.getLastName())
						.hasEmail(userDetails.getEmail())
						.hasPhone(userDetails.getPhone())
						.hasMobile(userDetails.getMobile())
						.hasCompany(userDetails.getCompany())
						.hasAddress(userDetails.getAddress())
						.hasCity(userDetails.getCity())
						.hasZipCode(userDetails.getZip())
						.hasState(userDetails.getState())
						.hasCountry(userDetails.getCountry())
						.hasLanguage(userDetails.getLanguage())
						.hasSkills(userDetails.getSkills())
				);
	}
}
