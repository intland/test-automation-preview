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

package com.intland.codebeamer.integration.classic.page.systemadmin.component;

import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class SystemAdminCreateNewGroupAssertions extends AbstractCodebeamerComponentAssert<SystemAdminCreateNewGroupComponent, SystemAdminCreateNewGroupAssertions> {

	protected SystemAdminCreateNewGroupAssertions(SystemAdminCreateNewGroupComponent component) {
		super(component);
	}

	public SystemAdminCreateNewGroupAssertions hasDescription(UserGroupPermission permission, String expectedDescription) {
		return assertAll("Verify permission description", () -> {
			assertThat(getComponent().getPermissionDescription(permission)).containsText(expectedDescription);
		});
	}

	public SystemAdminCreateNewGroupAssertions isReady() {
		return assertAll("System admin create new group page is ready", () -> {
			assertThat(getComponent().getReviewAdministrationCheckbox(UserGroupPermission.REVIEW_ADMINISTRATION)).not().isChecked();
			assertThat(getComponent().getReviewAdministrationLabel(UserGroupPermission.REVIEW_ADMINISTRATION)).isVisible();
			assertThat(getComponent().getPageTitleElement()).isVisible();
			assertThat(getComponent().getGroupNameSelector()).isEditable();
			assertThat(getComponent().getGroupDescriptionSelector()).isEditable();
			assertThat(getComponent().getLdapGroupNameSelector()).isEditable();
			assertThat(getComponent().getBasedOnDropdownSelector()).isEditable();
		});
	}
}
