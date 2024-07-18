package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectBrowserProjectListComponent
		extends AbstractCodebeamerComponent<ProjectBrowserProjectListComponent, ProjectBrowserProjectListAssertion> {

	public ProjectBrowserProjectListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#project-browser-tabs #project-categories");
	}

	public ProjectBrowserProjectListComponent dragAndDropProjectTo(String projectName, CodebeamerLocator target) {
		this.getProjectCardDragAndDropHandlerByName(projectName).drag(target, 10);
		this.waitForResponse(responseEndsWith("/projects/projectCategoryList.spr"), 200);
		return this;
	}

	public CodebeamerLocator getCategoryElementForProjectByName(String projectName) {
		return this.locator("div.set:has(a.project-card:has(span.project-name[title='%s']))".formatted(escapeApostrophe(projectName)));	
	}
	
	public CodebeamerLocator getProjectCardById(Integer projectId) {
		return this.locator("a.project-card[data-projectid='%s']".formatted(projectId));
	}
	
	public CodebeamerLocator getProjectCardByName(String projectName) {
		return this.locator("a.project-card:has(span.project-name[title='%s'])".formatted(escapeApostrophe(projectName)));		
	}

	public CodebeamerLocator getProjectCardDragAndDropHandlerByName(String projectName) {
		return this.locator("a.project-card:has(span.project-name[title='%s']) .knob".formatted(escapeApostrophe(projectName)));		
	}
	
	public CodebeamerLocator getProjectCardDragAndDropHandlerById(Integer projectId) {
		return this.locator("a.project-card[data-projectid='%s'] .knob".formatted(projectId));		
	}
	
	@Override
	public ProjectBrowserProjectListAssertion assertThat() {
		return new ProjectBrowserProjectListAssertion(this);
	}

}
