package com.intland.codebeamer.integration.nextgen.page.login;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.ProjectBrowserPage;
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

	@Action("redirectedToUserProjectBrowserPage")
	public ProjectBrowserPage redirectedToProjectBrowserPage() {
		return new ProjectBrowserPage(codebeamerPage).isActive();
	}

}
