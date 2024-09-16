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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.useraccount.component.UserAccountPageAssertions;
import com.intland.codebeamer.integration.classic.page.useraccount.component.UserAccountFormComponent;
import com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar.UserAccountActionBarAssertions;
import com.intland.codebeamer.integration.classic.page.useraccount.component.actionbar.UserAccountActionBarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public abstract class AbstractAccountPage<P extends AbstractAccountPage<P>> extends AbstractCodebeamerPage<P> {

	@Component("User Account Form")
	protected UserAccountFormComponent userAccountFormComponent;

	@Component("Account Page ActionBar")
	protected UserAccountActionBarComponent userAccountActionBarComponent;

	protected AbstractAccountPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.userAccountFormComponent = new UserAccountFormComponent(codebeamerPage);
		this.userAccountActionBarComponent = new UserAccountActionBarComponent(codebeamerPage);
	}

	protected AbstractAccountPage(CodebeamerPage codebeamerPage, User user) {
		super(codebeamerPage);
		this.userAccountFormComponent = new UserAccountFormComponent(codebeamerPage);
		this.userAccountActionBarComponent = new UserAccountActionBarComponent(codebeamerPage, user);
	}

	public UserAccountFormComponent getUserAccountFormComponent() {
		return userAccountFormComponent;
	}

	public UserAccountActionBarComponent getUserAccountActionBarComponent() {
		return userAccountActionBarComponent;
	}

	public abstract P assertUserAccountFormComponent(Consumer<UserAccountPageAssertions> assertion);

	public abstract P assertUserAccountActionBarPageComponent(Consumer<UserAccountActionBarAssertions> assertion);
}
