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

package com.intland.codebeamer.integration.classic.page.userregistration;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.classic.page.userregistration.component.UserRegistrationFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

import net.datafaker.Faker;

@Test(groups = "UserRegistrationTestCases")
public class UserRegistrationTestCases extends AbstractIntegrationClassicNGTests {

	@Test(description = "User is able to navigate to UserRegistrationPage")
	public void userRegistrationRageCanBeLoaded() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent().visitUserRegistration();
	}

	@Test(description = "User is able to use user registration form")
	public void userRegistrationPageIsReady() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent().visitUserRegistration()
				.assertUserRegistrationFormComponent(UserRegistrationFormAssertions::isReady);
	}
		
	@Test(description = "User is able to cancel a new account creation")
	public void userIsAbleToCancelANewAccountCreation() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent()
				.visitUserRegistration()
				.cancel()
				.redirectedToLoginPage();
	}
	
	@Test(description = "User is able to see warning about required fields")
	public void userIsAbleToSeeWarningAboutRequiredFields() {
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent()
				.visitUserRegistration()
				.userRegistrationFormComponent(form -> form.fillOut().empty())
				.save()
				.redirectedToUserRegistrationPage()
				.assertUserRegistrationFormComponent(form -> form
						.hasUsernameError()
						.hasPasswordError());
	}
	
	@Test(description = "User is able to register a new account")
	public void userIsAbleToRegisterANewAccount() {
		Faker faker = new Faker();
		String password = faker.internet().password(5, 10, true, false);
				
		getClassicCodebeamerApplication()
				.visitLoginPage()
				.getLoginFormComponent()
				.visitUserRegistration()
				.userRegistrationFormComponent(form -> form.fillOut()
						.username(faker.internet().username() + getRandomString())
						.password(password)
						.confirmPassword(password)
						.firstName(faker.name().firstName())
						.lasttName(faker.name().lastName())
						.email(faker.internet().safeEmailAddress(getRandomString()))
						.company(faker.company().industry()))
				.save()
				.redirectedToUserMyWikiPage();
	}
	
}
