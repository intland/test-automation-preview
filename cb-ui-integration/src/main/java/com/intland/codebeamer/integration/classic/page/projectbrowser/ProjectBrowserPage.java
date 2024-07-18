package com.intland.codebeamer.integration.classic.page.projectbrowser;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserCategoryListComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserProjectListComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectBrowserPage")
public class ProjectBrowserPage extends AbstractCodebeamerPage<ProjectBrowserPage> {

	private static final String USER_WIKI_PAGE_PATH = "projects/browse.spr";

	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	private ProjectBrowserCategoryListComponent projectBrowserCategoryListComponent;

	private ProjectBrowserProjectListComponent projectBrowserProjectListComponent;
	
	public ProjectBrowserPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.projectBrowserCategoryListComponent = new ProjectBrowserCategoryListComponent(getCodebeamerPage());
		this.projectBrowserProjectListComponent = new ProjectBrowserProjectListComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ProjectBrowserPage visit() {
		navigate(USER_WIKI_PAGE_PATH);
		return isActive();
	}

	@Override
	public ProjectBrowserPage isActive() {
		assertUrl(USER_WIKI_PAGE_PATH, "User wiki page should be the active page");
		this.sleep(2); // Todo we need some class to be added when animation is ended
		return this;
	}

	public ProjectBrowserPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}
	
	public OverlayMessageBoxComponent getOverlayMessageBoxComponent() {
		return overlayMessageBoxComponent;
	}
	
	public ProjectBrowserCategoryListComponent getCategoryListComponent() {
		return projectBrowserCategoryListComponent;
	}

	public ProjectBrowserPage categoryListComponent(Consumer<ProjectBrowserCategoryListComponent> formConsumer) {
		formConsumer.accept(projectBrowserCategoryListComponent);
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
