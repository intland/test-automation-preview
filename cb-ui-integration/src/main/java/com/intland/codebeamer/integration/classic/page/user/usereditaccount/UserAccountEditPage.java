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

package com.intland.codebeamer.integration.classic.page.user.usereditaccount;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.component.EditUserProfileFormAssertion;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.component.EditUserProfileFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("UserAccountEditPage")
public class UserAccountEditPage extends AbstractCodebeamerPage<UserAccountEditPage> {

	private static final String EDIT_ACCOUNT_PAGE_PATH = "updateUser.spr?user_id=%s";

	private User user;

	@Component("Edit user profile form")
	private EditUserProfileFormComponent editUserProfileFormComponent;

	public UserAccountEditPage(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage);
		this.user = user;
		this.editUserProfileFormComponent = new EditUserProfileFormComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public UserAccountEditPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public UserAccountEditPage isActive() {
		assertUrl(formatPageUrl(), "Edit user account page should be the active page");
		return this;
	}

	public EditUserProfileFormComponent getEditUserProfileFormComponent() {
		return editUserProfileFormComponent;
	}

	public UserAccountEditPage editUserProfileFormComponent(Consumer<EditUserProfileFormComponent> formConsumer) {
		formConsumer.accept(editUserProfileFormComponent);
		return this;
	}

	@Action("Save")
	public UserAccountEditPageNavigation save() {
		editUserProfileFormComponent.getSaveButton().click();
		return new UserAccountEditPageNavigation(getCodebeamerPage(), this.user);
	}

	@Action("Cancel")
	public UserAccountEditPageNavigation cancel() {
		editUserProfileFormComponent.getCancelButton().click();
		return new UserAccountEditPageNavigation(getCodebeamerPage(), this.user);
	}

	@Action("Change Password")
	public UserAccountEditPageNavigation changePassword() {
		editUserProfileFormComponent.getChangePasswordButton().click();
		return new UserAccountEditPageNavigation(getCodebeamerPage(), this.user);
	}

	public UserAccountEditPage assertEditUserProfileFormComponent(Consumer<EditUserProfileFormAssertion> assertion) {
		assertion.accept(getEditUserProfileFormComponent().assertThat());
		return this;
	}

	private String formatPageUrl() {
		return EDIT_ACCOUNT_PAGE_PATH.formatted(Integer.valueOf(user.getId()));
	}
}
