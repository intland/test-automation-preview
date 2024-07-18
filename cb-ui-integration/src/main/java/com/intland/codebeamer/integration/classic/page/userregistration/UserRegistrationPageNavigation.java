package com.intland.codebeamer.integration.classic.page.userregistration;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.login.LoginPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UserRegistrationPageNavigation {

	private CodebeamerPage codebeamerPage;

	public UserRegistrationPageNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToUserRegistrationPage")
	public UserRegistrationPage redirectedToUserRegistrationPage() {
		return new UserRegistrationPage(codebeamerPage).isActive();
	}
	
	@Action("redirectedToUserMyWikiPage")
	public UserMyWikiPage redirectedToUserMyWikiPage() {
		return new UserMyWikiPage(codebeamerPage).isActive();
	}
	
	@Action("redirectedToLoginPage")
	public LoginPage redirectedToLoginPage() {
		return new LoginPage(codebeamerPage).assertLoginFormComponent(a -> a.isReady());
	}

}
