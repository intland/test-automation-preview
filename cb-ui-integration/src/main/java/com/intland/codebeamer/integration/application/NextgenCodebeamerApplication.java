package com.intland.codebeamer.integration.application;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.reviewhub.AllReviewPage;
import com.intland.codebeamer.integration.nextgen.page.login.LoginPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.ProjectBrowserPage;
import com.intland.codebeamer.integration.sitemap.annotation.Application;

@Application("Codebeamer")
public class NextgenCodebeamerApplication {

	private CodebeamerPage codebeamerPage;

	public NextgenCodebeamerApplication(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public LoginPage visitLoginPage() {
		return new LoginPage(codebeamerPage).visit();
	}

	public ProjectBrowserPage visitProjectBrowserPage() {
		return new ProjectBrowserPage(codebeamerPage).visit();
	}

	public AllReviewPage visitAllReviewPage() {
		return new AllReviewPage(codebeamerPage).visit();
	}
}
