package com.intland.codebeamer.integration.nextgen.page.projectbrowser.dialog.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectBrowserNewCategoryFormComponent
		extends AbstractCodebeamerComponent<ProjectBrowserNewCategoryFormComponent, ProjectBrowserNewCategoryFormAssertion> {

	public ProjectBrowserNewCategoryFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form");
	}

	public ProjectBrowserNewCategoryFormComponent createCategory(String categoryName) {
		getNewCategoryField().fill(categoryName);
		return this;
	}
	
	public CodebeamerLocator getNewCategoryField() {
		return this.locator("input[data-cy='category-name-input']");
	}
		
	@Override
	public ProjectBrowserNewCategoryFormAssertion assertThat() {
		return new ProjectBrowserNewCategoryFormAssertion(this);
	}

}
