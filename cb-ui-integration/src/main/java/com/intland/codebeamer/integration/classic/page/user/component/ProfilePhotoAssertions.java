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

package com.intland.codebeamer.integration.classic.page.user.component;

import com.intland.codebeamer.integration.api.service.user.UserPhoto;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ProfilePhotoAssertions extends AbstractCodebeamerComponentAssert<ProfilePhotoComponent, ProfilePhotoAssertions> {

	protected ProfilePhotoAssertions(ProfilePhotoComponent component) {
		super(component);
	}

	public ProfilePhotoAssertions isReady() {
		return assertAll("Profile photo page is ready", () -> {
			assertThat(getComponent().getCancelButton()).isVisible();
			assertThat(getComponent().getUploadButton()).isVisible();
			assertThat(getComponent().getChooseButton()).isVisible();
			assertThat(getComponent().getUserPhoto()).isVisible();
		});
	}

	public ProfilePhotoAssertions isReadyWithDeleteButton() {
		return assertAll("Profile photo page is ready with a delete button", () -> {
			assertThat(getComponent().getCancelButton()).isVisible();
			assertThat(getComponent().getUploadButton()).isVisible();
			assertThat(getComponent().getChooseButton()).isVisible();
			assertThat(getComponent().getDeleteButton()).isVisible();
			assertThat(getComponent().getUserPhoto()).isVisible();
		});
	}

	public ProfilePhotoAssertions hasFileUploadError(String errorMessage) {
		return assertAll("Uploaded invalid profile photo", () -> {
			assertThat(getComponent().getPhotoUploadError()).isVisible();
			assertThat(getComponent().getPhotoUploadError()).hasText(errorMessage);
		});
	}

	public ProfilePhotoAssertions hasProfilePhoto(UserPhoto userPhoto) {
		return assertAll("Profile photo uploaded", () -> {
			assertThat(getComponent().getUserPhoto()).not().hasAttribute("src", userPhoto.url());
		});
	}

	public ProfilePhotoAssertions hasNoProfilePhoto(UserPhoto userPhoto) {
		return assertAll("Profile photo deleted", () -> {
			assertThat(getComponent().getUserPhoto()).hasAttribute("src", userPhoto.url());
		});
	}
}