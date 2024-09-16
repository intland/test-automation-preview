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

package com.intland.codebeamer.integration.classic.page.systemadmin;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminCreateNewGroupAssertions;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminDashboardAssertions;
import com.intland.codebeamer.integration.classic.page.systemadmin.component.SystemAdminUserGroupsAssertions;
import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "SystemAdminDashboardPageTest")
public class SystemAdminDashboardPageTest extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		//@formatter:off
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToDefaultSystemAdministratorGroup()
				.build();
		//formatter:on
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "User is able to navigate to system admin page dashboard")
	public void systemAdminDashboardPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminDashboardPage();
	}

	@Test(description = "User is able to navigate to user groups page using link on dashboard")
	public void navigateToUserGroupsPageUsingLink() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminDashboardPage()
				.getSystemAdminDashboardComponent()
				.visitUserGroupPage();
	}

	@Test(description = "System admin page user group link is ready")
	public void systemAdminDashboardPageIsReady() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminDashboardPage()
				.assertSystemAdminDashboardComponent(SystemAdminDashboardAssertions::isReady);
	}

	@Test(description = "User is able to navigate to system admin user groups page")
	public void systemAdminUserGroupsPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminUserGroupsPage();
	}

	@Test(description = "User is able to navigate to create new group page using new group button")
	public void navigateToCreateNewGroupPageUsingNewGroupButton() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminUserGroupsPage()
				.getSystemAdminUserGroupsComponent()
				.visitNewUserGroupPage();
	}

	@Test(description = "System admin page new group link is ready")
	public void systemAdminUserGroupPageIsReady() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminUserGroupsPage()
				.assertSystemAdminUserGroupsComponent(SystemAdminUserGroupsAssertions::isReady);
	}

	@Test(description = "User is able to navigate to system admin create new group page")
	public void systemAdminCreateNewGroupPageCanBeLoaded() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminCreateNewGroupPage();
	}

	@Test(description = "System admin create new group page is ready")
	public void systemAdminCreateNewGroupPageIsReady() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminCreateNewGroupPage()
				.assertSystemAdminCreateNewGroupComponent(SystemAdminCreateNewGroupAssertions::isReady);
	}

	@Test(description = "Verify permission with its description")
	public void verifyPermissionWithItsDescription() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminCreateNewGroupPage()
				.assertSystemAdminCreateNewGroupComponent(a -> a.hasDescription(UserGroupPermission.REVIEW_ADMINISTRATION,
						"Allows access to the Review Administration Page."));
	}

	@Test(description = "User is able to create new group")
	public void userIsAbleToCreateNewGroup() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminCreateNewGroupPage()
				.systemAdminCreateNewGroupComponent(form -> form.fillOut()
						.groupName(getRandomString())
						.description("test description")
						.ldapGroupName("test1")
						.basedOn("System Administrator")
						.selectReviewAdministrationCheckbox(UserGroupPermission.REVIEW_ADMINISTRATION))
				.save()
				.redirectedToUserGroupsPage();
	}

	@Test(description = "User is able to cancel new group creation")
	public void userIsAbleToCancelNewGroupCreation() {
		getClassicCodebeamerApplication(activeUser)
				.visitSystemAdminCreateNewGroupPage()
				.cancel()
				.redirectedToUserGroupsPage();
	}
}
