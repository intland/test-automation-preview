package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ProjectBrowserCategoryListComponent
		extends AbstractCodebeamerComponent<ProjectBrowserCategoryListComponent, ProjectBrowserCategoryListAssertion> {

	public ProjectBrowserCategoryListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#left-content");
	}

	public ProjectBrowserCategoryListComponent createCategory(String categoryName) {
		getNewCategoryField().fill(categoryName);
		getNewCategoryButton().click();
		return this;
	}
	
	public ProjectBrowserCategoryListComponent deleteCategory(String categoryName) {
		getCategoryMoreActionButtonByName(categoryName).scrollIntoView().click();
		getDeleteActionForCategory(categoryName).click();
		return this;
	}
	
	public CodebeamerLocator getNewCategoryButton() {
		return this.locator(".project-hiearchy-view-controls div.new-category-container .create-new-category-button");		
	}
	
	public CodebeamerLocator getNewCategoryField() {
		return this.locator(".project-hiearchy-view-controls div.new-category-container input[name=categoryName]");		
	}
	
	public CodebeamerLocator getCategoryMoreActionButtonByName(String categoryName) {
		return this.locator("li.category-element:has(input.category-name-field[value='%s']) .edit-category .menuArrowDown.actionBarIcon".formatted(categoryName));		
	}
	
	public CodebeamerLocator getDeleteActionForCategory(String categoryName) {
		return this.locator("li.category-element:has(input.category-name-field[value='%s']) li.ui-menu-item a[onclick*='deleteProjectCategoryWithConfirmation']".formatted(categoryName));		
	}

	public CodebeamerLocator getCategoryById(Integer categoryId) {
		return this.locator("li.category-element[data-id='%s']".formatted(categoryId));		
	}
	
	public CodebeamerLocator getCategoryByName(String categoryName) {
		return this.locator("li.category-element:has(input.category-name-field[value='%s'])".formatted(categoryName));		
	}
		
	@Override
	public ProjectBrowserCategoryListAssertion assertThat() {
		return new ProjectBrowserCategoryListAssertion(this);
	}

}
