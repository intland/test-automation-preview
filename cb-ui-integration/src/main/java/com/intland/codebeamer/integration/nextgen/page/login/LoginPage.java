package com.intland.codebeamer.integration.nextgen.page.login;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.login.component.LoginFormAssertions;
import com.intland.codebeamer.integration.nextgen.page.login.component.LoginFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastAssertions;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastComponent;

import java.util.function.Consumer;

@Page("LoginPage")
public class LoginPage extends AbstractCodebeamerPage<LoginPage> {
    
	private static final String LOGIN = "#/login";
	
	@Component("Login form")
	private LoginFormComponent loginFormComponent;
	@Component("Toast message")
	private ToastComponent toastComponent;
	
	public LoginPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.loginFormComponent = new LoginFormComponent(codebeamerPage);
		this.toastComponent = new ToastComponent(codebeamerPage);
	}

	@Action("Visit")
	public LoginPage visit() {
		navigate(LOGIN);
		getCodebeamerPage().waitForNetworkidle();
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

	public ToastComponent getToastComponent() {
		return toastComponent;
	}
	
	public LoginPage assertLoginFormComponent(Consumer<LoginFormAssertions> assertion) {
		assertion.accept(getLoginFormComponent().assertThat());
		return this;
	}
	
	public LoginPage assertToastComponent(Consumer<ToastAssertions> assertion) {
		assertion.accept(getToastComponent().assertThat());
		return this;
	}
}
