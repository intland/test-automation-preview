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
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class SystemAdminCreateNewGroupFormBuilder {

	@Component("Create new group form")
	private SystemAdminCreateNewGroupComponent systemAdminCreateNewGroupComponent;

	public SystemAdminCreateNewGroupFormBuilder(SystemAdminCreateNewGroupComponent systemAdminCreateNewGroupComponent) {
		this.systemAdminCreateNewGroupComponent = systemAdminCreateNewGroupComponent;
	}

	public SystemAdminCreateNewGroupFormBuilder groupName(String groupName) {
		this.systemAdminCreateNewGroupComponent.getGroupNameSelector().fill(groupName);
		return this;
	}

	public SystemAdminCreateNewGroupFormBuilder description(String description) {
		this.systemAdminCreateNewGroupComponent.getGroupDescriptionSelector().fill(description);
		return this;
	}

	public SystemAdminCreateNewGroupFormBuilder ldapGroupName(String ldapName) {
		this.systemAdminCreateNewGroupComponent.getLdapGroupNameSelector().fill(ldapName);
		return this;
	}

	public SystemAdminCreateNewGroupFormBuilder basedOn(String basedOn) {
		this.systemAdminCreateNewGroupComponent.getBasedOnDropdownSelector().selectOption(basedOn);
		return this;
	}

	public SystemAdminCreateNewGroupFormBuilder selectReviewAdministrationCheckbox(UserGroupPermission permission) {
		this.systemAdminCreateNewGroupComponent.getReviewAdministrationCheckbox(permission).check(true);
		return this;
	}
}
