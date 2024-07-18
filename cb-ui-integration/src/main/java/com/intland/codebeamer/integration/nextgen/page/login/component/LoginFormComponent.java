package com.intland.codebeamer.integration.nextgen.page.login.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.login.LoginPageNavigation;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class LoginFormComponent extends AbstractCodebeamerComponent<LoginFormComponent, LoginFormAssertions>  {

	private LoginPageNavigation loginPageNavigation;
	
	public LoginFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "[class=\"login-card\"] form");
		this.loginPageNavigation = new LoginPageNavigation(codebeamerPage);
	}

	@Action("Login")
	public LoginPageNavigation login(String username, String password) {
		getUsernameField().fill(username);
		getPasswordField().fill(password);
		getLoginButton().click();		
		return this.loginPageNavigation;
	}

	public CodebeamerLocator getErrorMessageElement() {
		return this.locator("#error-message");
	}

	public CodebeamerLocator getUsernameField() {
		return this.locator("input[data-cy='login-username-input-field']");
	}

	public CodebeamerLocator getPasswordField() {
		return this.locator("input[data-cy=\"login-password-input-field\"]");
	}

	public CodebeamerLocator getForgotPasswordLink() {
		return this.locator("a[href*=\"forgottenPassword\"]");
	}

	public CodebeamerLocator getLoginButton() {
		return this.locator("button#btnLogin");
	}

	public CodebeamerLocator getUserRegistrationLink() {
		return this.locator("button#btnRegister");
	}

	@Override
	public LoginFormAssertions assertThat() {
		return new LoginFormAssertions(this);
	}

}
