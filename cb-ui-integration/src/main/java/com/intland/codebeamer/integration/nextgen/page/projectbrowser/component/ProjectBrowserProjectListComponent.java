package com.intland.codebeamer.integration.nextgen.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectBrowserProjectListComponent
		extends AbstractCodebeamerComponent<ProjectBrowserProjectListComponent, ProjectBrowserProjectListAssertion> {

	public ProjectBrowserProjectListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "p-panel[data-cy='project-item-container-panel']");
	}

	public ProjectBrowserProjectListComponent dragAndDropProjectTo(String projectName, CodebeamerLocator target) {
		this.getProjectCardByName(projectName).drag(target, 10);
		this.waitForPutApiResponse("/api/projects/projectCategory", 204);
		return this;
	}

	public CodebeamerLocator getProjectCardByName(String projectName) {
		return this.locator("div[data-cy='project-item'] span.project:text-is('%s')".formatted(escapeApostrophe(projectName)));		
	}

	public CodebeamerLocator getCategoryForProjectByName(String projectName) {
		return this.locator("div.category-name:has(+ div > div[data-cy='project-item'] span.project:text-is('%s'))".formatted(escapeApostrophe(projectName)));
	}
		  
	@Override
	public ProjectBrowserProjectListAssertion assertThat() {
		return new ProjectBrowserProjectListAssertion(this);
	}
	
}
