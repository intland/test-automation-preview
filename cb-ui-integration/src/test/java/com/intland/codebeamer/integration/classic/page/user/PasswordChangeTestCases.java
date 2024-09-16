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

package com.intland.codebeamer.integration.classic.page.user;

import org.testng.annotations.Test;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.user.component.PasswordChangeFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import net.datafaker.Faker;

@Test(groups = "PasswordChangeTestCases")
public class PasswordChangeTestCases extends AbstractIntegrationClassicNGTests {

	private static final String DEFAULT_PASSWORD = "007";
	private User activeUser;
	private String newPassword;
	private String wrongPassword;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		Faker faker = new Faker();
		newPassword = getRandomText(faker.internet().password());
		wrongPassword = getRandomText(faker.internet().password());
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@Test(description = "User is not able to change password when entered different 'New password' and 'New password again' field value")
	public void userEnteredInvalidOldPassword() {
		getClassicCodebeamerApplication(activeUser.getName(), DEFAULT_PASSWORD)
				.visitChangePasswordPage()
				.passwordChangeFormComponent(form -> form
						.setOldPassword(DEFAULT_PASSWORD)
						.setNewPassword(newPassword)
						.setNewPasswordAgain(wrongPassword))
				.save()
				.redirectedToUserChangePasswordPage()
				.assertPasswordChangeFormComponent(PasswordChangeFormAssertions::newPasswordNotMatched);
	}

	@Test(description = "User is not able to change password when entered wrong 'Old Password' field value")
	public void userEnteredDifferentNewPassword() {
		getClassicCodebeamerApplication(activeUser.getName(), DEFAULT_PASSWORD)
				.visitChangePasswordPage()
				.passwordChangeFormComponent(form -> form
						.setOldPassword(wrongPassword)
						.setNewPassword(newPassword)
						.setNewPasswordAgain(newPassword))
				.save()
				.redirectedToUserChangePasswordPage()
				.assertPasswordChangeFormComponent(PasswordChangeFormAssertions::wrongCurrentPassword);
	}

	@Test(description = "User is able to authenticate when user credentials are valid")
	public void userIsAbleToChangePassword() {
		getClassicCodebeamerApplication(activeUser.getName(), DEFAULT_PASSWORD)
				.visitChangePasswordPage()
				.passwordChangeFormComponent(form -> form
						.setOldPassword(DEFAULT_PASSWORD)
						.setNewPassword(newPassword)
						.setNewPasswordAgain(newPassword))
				.save()
				.redirectedToLoginPage();

		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent()
				.login(activeUser.getName(), newPassword)
				.redirectedToUserMyWikiPage();
	}
}
