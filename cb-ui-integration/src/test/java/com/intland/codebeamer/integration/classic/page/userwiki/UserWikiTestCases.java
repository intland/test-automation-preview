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

package com.intland.codebeamer.integration.classic.page.userwiki;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.child.wiki.component.UserWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "UserWikiTestCases")
public class UserWikiTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {	
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		
		switchToClassicUI(activeUser);
	}
	
	@Override
	protected void cleanUpDataAfterClass() throws Exception {

	}
	
	@Test(description = "User is able to create a new wiki page")
	public void userIsAbleToCreateNewWikiPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserMyWikiPage()
				.getUserDashboardActionbarComponent().createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
				.save()
				.redirectedToUserWikiPage();
	}
	
	@Test(description = "User is not able to create a new wiki page without filling out required fields")
	public void userIsAbleToSeeAnErrorAboutRequiredField() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserMyWikiPage()
				.getUserDashboardActionbarComponent().createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(StringUtils.EMPTY))
				.save()
				.redirectedToWikiNewChildDialog()
				.assertWikiNewChildDialogFormComponent(UserWikiNewChildDialogFormAssertions::isPageNameEmpty);
	}

	@Test(description = "User is able to create a new project wiki page with a child wiki page")
	public void createNewUserWikiPageWithAChild() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserMyWikiPage()
				.getUserDashboardActionbarComponent()
				.createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
				.save()
				.redirectedToUserWikiPage()
				.getActionbarComponent()
				.createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
				.save()
				.redirectedToUserWikiPage();
	}

	@Test(description = "User is able to create a new project dashboard page")
	public void createNewUserDashboardPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitUserMyWikiPage()
				.getUserDashboardActionbarComponent()
				.createNewDashBoard()
				.dashboardNewChildDialogFormComponent(form -> form.pageName(getRandomString()))
				.save()
				.redirectedToUserDashboardPage()
				.getActionbarComponent();
	}
}
