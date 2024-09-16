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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProfilePhotoComponent extends AbstractCodebeamerComponent<ProfilePhotoComponent, ProfilePhotoAssertions> {

	private User user;

	public ProfilePhotoComponent(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage, "#updateUserPhoto");
		this.user = user;
	}

	@Override
	public ProfilePhotoAssertions assertThat() {
		return new ProfilePhotoAssertions(this);
	}

	public ProfilePhotoComponent setFile(Path path) {
		getCodebeamerPage().uploadFiles(() -> getFileInputLocator().click(), path);
		return this;
	}

	@Action("Upload")
	public ProfilePhotoNavigation uploadProfilePhoto(Path path) {
		setFile(path);
		getUploadButton().click();
		return new ProfilePhotoNavigation(getCodebeamerPage(), this.user);
	}

	@Action("Delete")
	public ProfilePhotoNavigation delete() {
		getDeleteButton().click();
		return new ProfilePhotoNavigation(getCodebeamerPage(), user);
	}

	@Action("Cancel")
	public ProfilePhotoNavigation cancel() {
		getCancelButton().click();
		return new ProfilePhotoNavigation(getCodebeamerPage(), user);
	}

	private CodebeamerLocator getFileInputLocator() {
		return this.locator("input[type='file'][name='file']");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input[name='cancel']");
	}

	public CodebeamerLocator getChooseButton() {
		return this.locator("input[name='file']");
	}

	public CodebeamerLocator getUserPhoto() {
		return this.locator("img.largePhoto");
	}

	public CodebeamerLocator getDeleteButton() {
		return this.locator("input[name='delete']");
	}

	public CodebeamerLocator getUploadButton() {
		return this.locator("input[name='upload']");
	}

	public CodebeamerLocator getPhotoUploadError() {
		return this.locator("div.error.onlyOneMessage");
	}
}