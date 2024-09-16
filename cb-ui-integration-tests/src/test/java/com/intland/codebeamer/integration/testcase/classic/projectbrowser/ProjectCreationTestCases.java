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

package com.intland.codebeamer.integration.testcase.classic.projectbrowser;

import static com.intland.codebeamer.integration.common.usergroup.UserGroupPermission.SYSTEM_PROJECT_CREATE;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.common.usergroup.UserGroupPermission;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "ProjectCreationTestCases")
public class ProjectCreationTestCases extends AbstractIntegrationClassicNGTests {

    private ApiUser apiUser;

    private UserGroupApiService userGroupApiService;

    private UserApiService userApiService;

    @Override
    protected void generateDataBeforeClass() {
        apiUser = getApplicationConfiguration().getApiUser();
        userGroupApiService = getDataManagerService().getUserGroupApiService(apiUser);
        userApiService = getDataManagerService().getUserApiService(apiUser);
    }

    @Override
    protected void cleanUpDataAfterClass() {
    }

    @TestCase(link = "https://codebeamer.com/cb/issue/13248726")
    @Test(description = "User without project creation permission is not able to see new project button")
    public void verifyCreateProjectButtonVisibilityWithUserWithOutCreateProjectPermission() {
        // GIVEN
        String usersWithOutCreateProjectPermission = "usersWithOutCreateProjectPermission";
        UserGroupPermission[] permissions = UserGroupPermission.getRegularUserGroupExcluding(SYSTEM_PROJECT_CREATE);
        userGroupApiService.createUserGroup(usersWithOutCreateProjectPermission, permissions);

        User activeUser = userApiService.createUser()
                .addToGroup(usersWithOutCreateProjectPermission)
                .build();

        // WHEN THEN
        try {
            getClassicCodebeamerApplication(activeUser)
                    .visitProjectBrowserPage()
                    .getActionBarComponent().assertThat().isNewProjectButtonHidden();
        } finally {
            userApiService.disableUser(activeUser);
        }
    }

    @TestCase(link = "https://codebeamer.com/cb/issue/13248729")
    @Test(description = "User with project creation permission is able to see a new project button")
    public void verifyCreateProjectButtonVisibilityWithUserWithCreateProjectPermission() {
        // GIVEN
        String usersWithCreateProjectPermission = "usersWithCreateProjectPermission";
        UserGroupPermission[] permissions = UserGroupPermission.getRegularUserGroupIncluding(SYSTEM_PROJECT_CREATE);
        userGroupApiService.createUserGroup(usersWithCreateProjectPermission, permissions);

        User activeUser = userApiService.createUser()
                .addToGroup(usersWithCreateProjectPermission)
                .build();

        // WHEN THEN
        try {
            getClassicCodebeamerApplication(activeUser)
                    .visitProjectBrowserPage()
                    .getActionBarComponent().assertThat().isNewProjectButtonVisible();
        } finally {
            userApiService.disableUser(activeUser);
        }
    }


}