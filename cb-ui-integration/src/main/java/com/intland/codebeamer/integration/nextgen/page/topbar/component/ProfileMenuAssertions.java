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

package com.intland.codebeamer.integration.nextgen.page.topbar.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ProfileMenuAssertions extends
		AbstractCodebeamerComponentAssert<ProfileMenuComponent, ProfileMenuAssertions> {

	public ProfileMenuAssertions(ProfileMenuComponent component) {
		super(component);
	}

	public ProfileMenuAssertions assertProfilePhotoButtonIsVisible() {
		return assertAll("Profile photo button is visible",
				() -> assertThat(getComponent().getProfilePhotoElement()).isVisible());
	}

	public ProfileMenuAssertions isReady() {
		return assertAll("Profile photo dialog is ready", () -> {
			assertThat(getComponent().getProfileOption()).isVisible();
			assertThat(getComponent().getLogoutOption()).isVisible();
		});
	}
}
