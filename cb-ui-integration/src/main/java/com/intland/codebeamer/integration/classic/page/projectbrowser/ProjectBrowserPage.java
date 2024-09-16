package com.intland.codebeamer.integration.classic.page.projectbrowser;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserActionBarComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserAvailableToJoinComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserCategoryListComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserCompactListComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserProjectGroupsComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserProjectListComponent;
import com.intland.codebeamer.integration.classic.page.projectbrowser.component.ProjectBrowserProjectTreeComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectBrowserPage")
public class ProjectBrowserPage extends AbstractCodebeamerPage<ProjectBrowserPage> {

	private static final String USER_WIKI_PAGE_PATH = "projects/browse.spr";

	@Component("Overlay Messages")
	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	@Component("Action bar")
	private ProjectBrowserActionBarComponent projectBrowserActionBarComponent;

	@Component("Edit categories")
	private ProjectBrowserCategoryListComponent projectBrowserCategoryListComponent;

	@Component("Project list")
	private ProjectBrowserProjectListComponent projectBrowserProjectListComponent;

	@Component("Project browser project tree")
	private ProjectBrowserProjectTreeComponent projectBrowserProjectTreeComponent;

	@Component("Project browser available to join")
	private ProjectBrowserAvailableToJoinComponent projectBrowserAvailableToJoinComponent;

	@Component("Project browser compact list")
	private ProjectBrowserCompactListComponent projectBrowserCompactListComponent;

	@Component("Project browser project groups")
	private ProjectBrowserProjectGroupsComponent projectBrowserProjectGroupsComponent;

	public ProjectBrowserPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.projectBrowserActionBarComponent = new ProjectBrowserActionBarComponent(getCodebeamerPage());
		this.projectBrowserCategoryListComponent = new ProjectBrowserCategoryListComponent(getCodebeamerPage());
		this.projectBrowserProjectListComponent = new ProjectBrowserProjectListComponent(getCodebeamerPage());
		this.projectBrowserProjectTreeComponent = new ProjectBrowserProjectTreeComponent(getCodebeamerPage());
		this.projectBrowserAvailableToJoinComponent = new ProjectBrowserAvailableToJoinComponent(getCodebeamerPage());
		this.projectBrowserCompactListComponent = new ProjectBrowserCompactListComponent(getCodebeamerPage());
		this.projectBrowserProjectGroupsComponent = new ProjectBrowserProjectGroupsComponent(getCodebeamerPage());
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

	public ProjectBrowserActionBarComponent getActionBarComponent() {
		return projectBrowserActionBarComponent;
	}

	public ProjectBrowserPage actionBarComponent(Consumer<ProjectBrowserActionBarComponent> formConsumer) {
		formConsumer.accept(projectBrowserActionBarComponent);
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

	public ProjectBrowserProjectTreeComponent getProjectTreeComponent() {
		return projectBrowserProjectTreeComponent;
	}

	public ProjectBrowserPage projectTreeComponent(Consumer<ProjectBrowserProjectTreeComponent> formConsumer) {
		formConsumer.accept(projectBrowserProjectTreeComponent);
		return this;
	}

	public ProjectBrowserPage projectTreeComponent(BiConsumer<ProjectBrowserPage, ProjectBrowserProjectTreeComponent> formConsumer) {
		formConsumer.accept(this, projectBrowserProjectTreeComponent);
		return this;
	}

	public ProjectBrowserAvailableToJoinComponent getAvailableToJoinComponent() {
		return projectBrowserAvailableToJoinComponent;
	}

	public ProjectBrowserPage availableToJoinComponent(Consumer<ProjectBrowserAvailableToJoinComponent> formConsumer) {
		formConsumer.accept(projectBrowserAvailableToJoinComponent);
		return this;
	}

	public ProjectBrowserPage availableToJoinComponent(BiConsumer<ProjectBrowserPage, ProjectBrowserAvailableToJoinComponent> formConsumer) {
		formConsumer.accept(this, projectBrowserAvailableToJoinComponent);
		return this;
	}

	public ProjectBrowserCompactListComponent getCompactListComponent() {
		return projectBrowserCompactListComponent;
	}

	public ProjectBrowserPage compactListComponent(Consumer<ProjectBrowserCompactListComponent> formConsumer) {
		formConsumer.accept(projectBrowserCompactListComponent);
		return this;
	}

	public ProjectBrowserPage compactListComponent(BiConsumer<ProjectBrowserPage, ProjectBrowserCompactListComponent> formConsumer) {
		formConsumer.accept(this, projectBrowserCompactListComponent);
		return this;
	}

	public ProjectBrowserProjectGroupsComponent getProjectGroupsComponent() {
		return projectBrowserProjectGroupsComponent;
	}

	public ProjectBrowserPage projectGroupsComponent(Consumer<ProjectBrowserProjectGroupsComponent> formConsumer) {
		formConsumer.accept(projectBrowserProjectGroupsComponent);
		return this;
	}

	public ProjectBrowserPage projectGroupsComponent(BiConsumer<ProjectBrowserPage, ProjectBrowserProjectGroupsComponent> formConsumer) {
		formConsumer.accept(this, projectBrowserProjectGroupsComponent);
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

	public ProjectBrowserPage openProjectGroups() {
		projectBrowserProjectGroupsComponent.activateTab();
		return this;
	}

	public ProjectBrowserPage openProjectList() {
		projectBrowserProjectListComponent.activateTab();
		return this;
	}

	public ProjectBrowserPage openProjectTree() {
		projectBrowserProjectTreeComponent.activateTab();
		return this;
	}

	public ProjectBrowserPage openCompactList() {
		projectBrowserCompactListComponent.activateTab();
		return this;
	}

	public ProjectBrowserPage openAvailableToJoin() {
		projectBrowserAvailableToJoinComponent.activateTab();
		return this;
	}
}
