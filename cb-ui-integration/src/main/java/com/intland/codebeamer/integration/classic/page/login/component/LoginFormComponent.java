package com.intland.codebeamer.integration.classic.page.login.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.login.LoginPageNavigation;
import com.intland.codebeamer.integration.classic.page.userregistration.UserRegistrationPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class LoginFormComponent extends AbstractCodebeamerComponent<LoginFormComponent, LoginFormAssertions>  {

	private LoginPageNavigation loginPageNavigation;
	
	private UserRegistrationPage userRegistrationPage;

	public LoginFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "form#loginForm");
		this.loginPageNavigation = new LoginPageNavigation(codebeamerPage);
		this.userRegistrationPage = new UserRegistrationPage(codebeamerPage);
	}

	@Action("Login")
	public LoginPageNavigation login(String username, String password) {
		getUsernameField().fill(username);
		getPasswordField().fill(password);
		getLoginButton().click();
		return this.loginPageNavigation;
	}
	
	@Action("Visit user registration page") // TODO Something better?
	public UserRegistrationPage visitUserRegistration() {
		getUserRegistrationLink().click();
		return this.userRegistrationPage.visit();
	}
	
	public CodebeamerLocator getErrorMessageElement() {
		return this.locator("div.error");
	}
	
	public CodebeamerLocator getUsernameField() {
		return this.locator("input#user");
	}

	public CodebeamerLocator getPasswordField() {
		return this.locator("input#password");
	}

	public CodebeamerLocator getForgotPasswordLink() {
		return this.locator("a[href*='lostPassword.spr']");
	}

	public CodebeamerLocator getLoginButton() {
		return this.locator("input.login_button");
	}

	public CodebeamerLocator getUserRegistrationLink() {
		return this.locator("a[href*='createUser.spr']");
	}
	
	@Override
	public LoginFormAssertions assertThat() {
		return new LoginFormAssertions(this);
	}

}
