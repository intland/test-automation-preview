package com.intland.codebeamer.integration.classic.page.login;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.classic.page.login.component.LoginFormAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "LoginPage")
public class LoginPageTest extends AbstractIntegrationClassicNGTests {

	@Test(description = "User is able to visit login page")
	public void loginPageCanBeLoaded() {
		getClassicCodebeamerApplication()
			.visitLoginPage();
	}
	
	@Test(description = "User is able to use login form")
	public void loginFormIsReady() {
		getClassicCodebeamerApplication()
			.visitLoginPage()
			.assertLoginFormComponent(LoginFormAssertions::isReady);
	}
}
