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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProfileMenuComponent extends AbstractCodebeamerComponent<ProfileMenuComponent, ProfileMenuAssertions> {

	private ProfileMenuNavigation profileMenuNavigation;

	public ProfileMenuComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.layout-topbar");
		this.profileMenuNavigation = new ProfileMenuNavigation(getCodebeamerPage());
	}

	public ProfileMenuComponent selectProfilePhoto() {
		getProfilePhotoElement().click();
		return this;
	}

	public ProfileMenuNavigation visitProfilePage() {
		getProfileOption().click();
		return profileMenuNavigation;
	}

	public ProfileMenuNavigation logout() {
		getLogoutOption().click();
		return profileMenuNavigation;
	}

	public CodebeamerLocator getProfilePhotoElement() {
		return this.locator("[class='rounded-circle ng-tns-c2161915776-21']");
	}

	public CodebeamerLocator getProfileOption() {
		return this.locator("[data-cy='topbar-profile-menu-profile-list-item']");
	}

	public CodebeamerLocator getLogoutOption() {
		return this.locator("[data-cy='topbar-profile-menu-logout-list-item']");
	}

	public ProfileMenuComponent assertProfileMenuComponent(Consumer<ProfileMenuAssertions> assertion) {
		assertion.accept(assertThat());
		return this;
	}

	@Override
	public ProfileMenuAssertions assertThat() {
		return new ProfileMenuAssertions(this);
	}
}
