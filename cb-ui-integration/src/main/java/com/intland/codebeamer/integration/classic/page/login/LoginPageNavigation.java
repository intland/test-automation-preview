package com.intland.codebeamer.integration.classic.page.login;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class LoginPageNavigation {
	
	private CodebeamerPage codebeamerPage;

	public LoginPageNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToLoginPage")
	public LoginPage redirectedToLoginPage() {
		return new LoginPage(codebeamerPage).isActive();
	}

	@Action("redirectedToUserMyWikiPage")
	public UserMyWikiPage redirectedToUserMyWikiPage() {
		return new UserMyWikiPage(codebeamerPage).isActive();
	}

}
