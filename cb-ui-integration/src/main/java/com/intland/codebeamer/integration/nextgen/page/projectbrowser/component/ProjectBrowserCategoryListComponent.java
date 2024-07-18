package com.intland.codebeamer.integration.nextgen.page.projectbrowser.component;

import java.util.Optional;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.dialog.ProjectBrowserNewCategoryDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.intland.codebeamer.integration.ui.nextgen.component.ContextMenuComponent;
import com.intland.codebeamer.integration.ui.nextgen.dialog.confirmation.ConfirmationDialog;

public class ProjectBrowserCategoryListComponent
		extends AbstractCodebeamerComponent<ProjectBrowserCategoryListComponent, ProjectBrowserCategoryListAssertion> {

	private ProjectBrowserNewCategoryDialog projectBrowserNewCategoryDialog;

	private ConfirmationDialog confirmationDialog;
	
	private ContextMenuComponent contextMenuComponent;
	
	public ProjectBrowserCategoryListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "[data-cy='project-category-panel']");
		this.projectBrowserNewCategoryDialog = new ProjectBrowserNewCategoryDialog(codebeamerPage);
		this.confirmationDialog = new ConfirmationDialog(codebeamerPage);
		this.contextMenuComponent = new ContextMenuComponent(codebeamerPage);
	}

	public ProjectBrowserNewCategoryDialog createNewCategory() {
		getNewCategoryButton().click();
		return this.projectBrowserNewCategoryDialog;
	}

	public ProjectBrowserNewCategoryDialog showAllCategories() {
		getShowMoreButton().ifPresent(CodebeamerLocator::click);
		return this.projectBrowserNewCategoryDialog;
	}
	
	public ProjectBrowserCategoryListComponent deleteCategory(String categoryName) {
		showAllCategories();
		getCategoryByName(categoryName).scrollIntoView().hover();
		
		getCategoryMenuByName(categoryName).click();
		contextMenuComponent.getMenuItemByName("Delete").click();
		confirmationDialog.getYesButton().click();
		return this;
	}
		
	public CodebeamerLocator getCategoryByName(String categoryName) {
		return this.locator(".category span.p-element:text-is('%s')".formatted(categoryName));
	}
	
	public CodebeamerLocator getCategoryMenuByName(String categoryName) {
		return this.locator(".category:has(span.p-element:text-is('%s')) button[data-cy='my-workspace-toggle-category-context-menu']".formatted(categoryName));
	}
	
	public CodebeamerLocator getNewCategoryButton() {
		return this.locator("button[data-cy='add-project-category-button']");
	}

	public Optional<CodebeamerLocator> getShowMoreButton() {
		return this.locatorIfPresent("div.category-show-more-less i:text-is('expand_more')");
	}
	
	public Optional<CodebeamerLocator> getShowLessButton() {
		return this.locatorIfPresent("div.category-show-more-less i:text-is('expand_less')");
	}
	
	@Override
	public ProjectBrowserCategoryListAssertion assertThat() {
		return new ProjectBrowserCategoryListAssertion(this);
	}

}
