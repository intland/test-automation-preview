package com.intland.codebeamer.integration.classic.page.login;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.login.component.LoginFormAssertions;
import com.intland.codebeamer.integration.classic.page.login.component.LoginFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("LoginPage")
public class LoginPage extends AbstractCodebeamerPage<LoginPage> {

	private static final String LOGIN = "login.spr";
	
	@Component("Login form")
	private LoginFormComponent loginFormComponent;

	public LoginPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.loginFormComponent = new LoginFormComponent(codebeamerPage);
	}

	@Action("Visit")
	public LoginPage visit() {
		navigate(LOGIN);
		return isActive();
	}

	@Override
	public LoginPage isActive() {
		assertUrl(LOGIN, "Login page should be the active page");
		return this;
	}
	
	public LoginFormComponent getLoginFormComponent() {
		return loginFormComponent;
	}

	public LoginPage assertLoginFormComponent(Consumer<LoginFormAssertions> assertion) {
		assertion.accept(getLoginFormComponent().assertThat());
		return this;
	}

}
