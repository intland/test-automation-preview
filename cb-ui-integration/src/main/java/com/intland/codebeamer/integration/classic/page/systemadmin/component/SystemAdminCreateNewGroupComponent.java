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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class SystemAdminCreateNewGroupComponent extends AbstractCodebeamerComponent<SystemAdminCreateNewGroupComponent, SystemAdminCreateNewGroupAssertions> {

	public SystemAdminCreateNewGroupComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".contentArea");
	}

	public SystemAdminCreateNewGroupFormBuilder fillOut() {
		return new SystemAdminCreateNewGroupFormBuilder(this);
	}

	public CodebeamerLocator getPageTitleElement() {
		return this.locator("span[class='page-title']");
	}

	public CodebeamerLocator getGroupNameSelector() {
		return this.locator("#groupName");
	}

	public CodebeamerLocator getGroupDescriptionSelector() {
		return this.locator("#groupDesc");
	}

	public CodebeamerLocator getLdapGroupNameSelector() {
		return this.locator("#ldapGroup");
	}

	public CodebeamerLocator getBasedOnDropdownSelector() {
		return this.locator("select[name='templateId']");
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input[value='Save']");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input.button.cancelButton");
	}

	public CodebeamerLocator getReviewAdministrationCheckbox(UserGroupPermission permission) {
		return this.locator("input[id='%s']".formatted(permission.getPermission()));
	}

	public CodebeamerLocator getReviewAdministrationLabel(UserGroupPermission permission) {
		return this.locator("label[for='%s']".formatted(permission.getPermission()));
	}

	public CodebeamerLocator getPermissionDescription(UserGroupPermission permission) {
		return this.locator("tr:has(label[for='%s']) > td:nth-of-type(3)".formatted(permission.getPermission()));
	}

	@Override
	public SystemAdminCreateNewGroupAssertions assertThat() {
		return new SystemAdminCreateNewGroupAssertions(this);
	}
}
