package com.intland.codebeamer.integration.nextgen.page.projectbrowser.dialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.ProjectBrowserPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectBrowserNewCategoryDialogNavigation {

	private CodebeamerPage codebeamerPage;

	public ProjectBrowserNewCategoryDialogNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToUserWikiNewChildDialog")
	public ProjectBrowserNewCategoryDialog stayOnProjectBrowserNewCategoryDialog() {
		return new ProjectBrowserNewCategoryDialog(codebeamerPage); // .isActive(); we need to check that is it visible
	}

	@Action("redirectedToProjectBrowserPage")
	public ProjectBrowserPage redirectedToProjectBrowserPage() {
		return new ProjectBrowserPage(codebeamerPage).isActive();
	}
	
}
