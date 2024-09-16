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

package com.intland.codebeamer.integration.classic.page.user;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.component.ProfilePhotoAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProfilePhotoTest")
public class ProfilePhotoTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
	}

	@Test(description = "User is able to visit profile photo page")
	public void profilePhotoPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage();
	}

	@Test(description = "User is able to use profile photo page")
	public void profilePhotoPageIsReady() {
		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.assertProfilePhotoComponent(ProfilePhotoAssertions::isReady);
	}

	@Test(description = "User is able to use profile photo page with delete button")
	public void profilePhotoPageIsReadyWithDeleteButton() {
		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.getProfilePhotoComponent()
				.uploadProfilePhoto(getFilePath("dummyProfilePhoto.jpg"))
				.redirectedToMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitEditProfilePhotoPage()
				.assertProfilePhotoComponent(ProfilePhotoAssertions::isReadyWithDeleteButton);
	}

	@Test(description = "User is able to cancel the profile photo page.")
	public void cancelProfilePhotoPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.getProfilePhotoComponent()
				.cancel()
				.redirectedToMyUserAccountPage();
	}
}