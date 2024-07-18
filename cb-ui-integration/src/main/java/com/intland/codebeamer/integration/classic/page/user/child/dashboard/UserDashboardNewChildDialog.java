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

package com.intland.codebeamer.integration.classic.page.user.child.dashboard;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.AbstractDashboardNewChildDialog;
import com.intland.codebeamer.integration.classic.page.user.child.dashboard.component.UserDashboardNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.user.child.dashboard.component.UserDashboardNewChildDialogFormComponent;

public class UserDashboardNewChildDialog
		extends
		AbstractDashboardNewChildDialog<UserDashboardNewChildDialogFormComponent, UserDashboardNewChildDialogFormAssertions> {

	private final UserDashboardNewChildDialogFormComponent dashboardNewChildDialogFormComponent;

	public UserDashboardNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.dashboardNewChildDialogFormComponent = new UserDashboardNewChildDialogFormComponent(getCodebeamerPage(),
				this.getDialogLocator());
	}

	@Override
	public UserDashboardNewChildDialog dashboardNewChildDialogFormComponent(
			Consumer<UserDashboardNewChildDialogFormComponent> formConsumer) {
		formConsumer.accept(this.dashboardNewChildDialogFormComponent);
		return this;
	}

	@Override
	public UserDashboardNewChildDialog assertDashboardNewChildDialogFormComponent(
			Consumer<UserDashboardNewChildDialogFormAssertions> assertion) {
		assertion.accept(this.dashboardNewChildDialogFormComponent.assertThat());
		return this;
	}

	@Override
	public UserDashboardNewChildDialogNavigation save() {
		this.dashboardNewChildDialogFormComponent.save();
		return new UserDashboardNewChildDialogNavigation(getCodebeamerPage());
	}

}
