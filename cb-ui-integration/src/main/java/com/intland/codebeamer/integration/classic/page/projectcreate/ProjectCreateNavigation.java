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

package com.intland.codebeamer.integration.classic.page.projectcreate;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.classic.page.project.ProjectLandingPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectCreateNavigation {

	private CodebeamerPage codebeamerPage;

	public ProjectCreateNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToProjectCreatePage")
	public ProjectCreatePage redirectedToProjectCreatePage() {
		return new ProjectCreatePage(codebeamerPage).isActive();
	}

	@Action("redirectedToProjectCreateGeneralSettingsStep")
	public ProjectCreatePage redirectedToProjectCreateGeneralSettingsStep() {
		return new ProjectCreatePage(codebeamerPage).projectCreateGeneralSettingsFormComponent(
				c -> c.assertThat().isStepContentVisible());
	}

	@Action("redirectedToProjectLandingPage")
	public ProjectLandingPage redirectedToProjectLandingPage() {
		return new ProjectLandingPage(codebeamerPage, getProjectId()).isActive();
	}

	private ProjectId getProjectId() {
		codebeamerPage.waitForURL("/project/*");
		String pageUrl = codebeamerPage.getPageUrl();
		return new ProjectId(Integer
				.valueOf(Objects.requireNonNull(getIdFromUrl(pageUrl), "ID of project cannot be computed from %s".formatted(pageUrl)))
				.intValue());
	}

	private String getIdFromUrl(String pageUrl) {
		return StringUtils.substringAfterLast(pageUrl, "/");
	}

}
