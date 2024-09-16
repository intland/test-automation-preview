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

package com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class UserAccountActionBarAssertions extends
		AbstractCodebeamerComponentAssert<UserAccountActionBarComponent, UserAccountActionBarAssertions> {

	public UserAccountActionBarAssertions(UserAccountActionBarComponent component) {
		super(component);
	}

	public UserAccountActionBarAssertions isReady(){
		return assertAll("All actionbar links should be visible on UserAccountPage", () -> {
			assertThat(getComponent().getEditProfileLink()).isVisible();
			assertThat(getComponent().getPersonalWikiPageLink()).isVisible();
			assertThat(getComponent().getChangePasswordPagePageLink()).isVisible();
			assertThat(getComponent().getProfilePhotoPageLink()).isVisible();
			assertThat(getComponent().getEditAccountSshKeyPageLink()).isVisible();
		});
	}
}
