package com.intland.codebeamer.integration.classic.page.login;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.classic.page.userregistration.component.UserRegistrationFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "UserRegistrationPage")
public class UserRegistrationPageTest extends AbstractIntegrationClassicNGTests {
		
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
}
