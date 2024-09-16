package com.intland.codebeamer.integration.nextgen.page.projectbrowser;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.component.ProjectBrowserCategoryListComponent;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.component.ProjectBrowserProjectListComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastAssertions;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastComponent;

@Page("ProjectBrowserPage")
public class ProjectBrowserPage extends AbstractCodebeamerPage<ProjectBrowserPage> {

	private static final String PROJECT_BROWSER_PAGE_PATH = "/#/projects/list";

	@Component("Edit categories")
	private ProjectBrowserCategoryListComponent projectBrowserCategoryListComponent;

	@Component("Project list")
	private ProjectBrowserProjectListComponent projectBrowserProjectListComponent;

	private ToastComponent toastComponent;

	public ProjectBrowserPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.projectBrowserCategoryListComponent = new ProjectBrowserCategoryListComponent(getCodebeamerPage());
		this.projectBrowserProjectListComponent = new ProjectBrowserProjectListComponent(getCodebeamerPage());
		this.toastComponent = new ToastComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ProjectBrowserPage visit() {
		navigate(PROJECT_BROWSER_PAGE_PATH);
		return isActive();
	}

	@Override
	public ProjectBrowserPage isActive() {
		assertUrl(PROJECT_BROWSER_PAGE_PATH, "User project browser page should be the active page");
		return this;
	}
	
	public ProjectBrowserCategoryListComponent getCategoryListComponent() {
		return projectBrowserCategoryListComponent;
	}

	public ProjectBrowserPage categoryListComponent(Consumer<ProjectBrowserCategoryListComponent> formConsumer) {
		formConsumer.accept(projectBrowserCategoryListComponent);
		return this;
	}
	
	public ProjectBrowserPage toastComponent(Consumer<ToastComponent> formConsumer) {
		formConsumer.accept(toastComponent);
		return this;
	}
	
	public ProjectBrowserProjectListComponent getProjectListComponent() {
		return projectBrowserProjectListComponent;
	}

	public ProjectBrowserPage projectListComponent(Consumer<ProjectBrowserProjectListComponent> formConsumer) {
		formConsumer.accept(projectBrowserProjectListComponent);
		return this;
	}
	
	public ProjectBrowserPage projectListComponent(BiConsumer<ProjectBrowserPage, ProjectBrowserProjectListComponent> formConsumer) {
		formConsumer.accept(this, projectBrowserProjectListComponent);
		return this;
	}
	
	public ProjectBrowserPage assertToastComponent(Consumer<ToastAssertions> assertion) {
		assertion.accept(toastComponent.assertThat());
		return this;
	}
	
	public ProjectBrowserPage page(Consumer<ProjectBrowserPage> pageConsumer) {
		pageConsumer.accept(this);
		return this;
	}
	
	@Override
	public ProjectBrowserPage assertPage(Consumer<ProjectBrowserPage> assertion) {
		assertion.accept(this);
		return this;
	}

}
