/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.projectadmin;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.projectadmin.component.audittab.ProjectAdminAuditTrailTabComponent;
import com.intland.codebeamer.integration.classic.page.projectadmin.component.generaltab.ProjectAdminGeneralTabComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("ProjectAdminPage")
public class ProjectAdminPage extends AbstractCodebeamerPage<ProjectAdminPage> {

	private static final String PROJECT_ADMIN_PAGE_URL = "project/%s/admin";

	private final Project project;

	@Component("Project admin general component")
	private ProjectAdminGeneralTabComponent generalTabComponent;

	@Component("Project admin audit component")
	private ProjectAdminAuditTrailTabComponent auditTrailTabComponent;

	@Component("Overlay message component")
	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	public ProjectAdminPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.generalTabComponent = new ProjectAdminGeneralTabComponent(codebeamerPage, project);
		this.auditTrailTabComponent = new ProjectAdminAuditTrailTabComponent(codebeamerPage, project);
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
	}

	public ProjectAdminPage changeToProjectAdminGeneralTab() {
		generalTabComponent.activateTab();
		return this;
	}

	public ProjectAdminPage projectAdminGeneralTab(Consumer<ProjectAdminGeneralTabComponent> formConsumer) {
		changeToProjectAdminGeneralTab();
		formConsumer.accept(generalTabComponent);
		return this;
	}

	public ProjectAdminPage projectAdminAuditTab(Consumer<ProjectAdminAuditTrailTabComponent> formConsumer) {
		changeToProjectAdminAuditTrailTab();
		formConsumer.accept(auditTrailTabComponent);
		return this;
	}

	public ProjectAdminPage changeToProjectAdminAuditTrailTab() {
		auditTrailTabComponent.activateTab();
		return this;
	}

	public ProjectAdminPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public OverlayMessageBoxComponent getOverlayMessageBoxComponent() {
		return overlayMessageBoxComponent;
	}

	@Override
	public ProjectAdminPage isActive() {
		assertUrl(formatProjectAdminLandingPageUrl(), "Project admin page should be the active page");
		return this;
	}

	@Action("Visit")
	public ProjectAdminPage visit() {
		navigate(formatProjectAdminLandingPageUrl());
		return isActive();
	}

	private String formatProjectAdminLandingPageUrl() {
		return PROJECT_ADMIN_PAGE_URL.formatted(project.id().id());
	}
}