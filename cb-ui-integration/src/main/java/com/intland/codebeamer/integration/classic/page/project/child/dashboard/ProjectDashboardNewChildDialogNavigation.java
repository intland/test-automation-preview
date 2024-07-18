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

package com.intland.codebeamer.integration.classic.page.project.child.dashboard;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.AbstractDashboardNewChildDialogNavigation;
import com.intland.codebeamer.integration.classic.page.project.ProjectDashboardPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectDashboardNewChildDialogNavigation extends AbstractDashboardNewChildDialogNavigation {

	public ProjectDashboardNewChildDialogNavigation(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	@Action("redirectedToWikiNewChildDialog")
	public ProjectDashboardNewChildDialog redirectedToDashboardNewChildDialog() {
		return new ProjectDashboardNewChildDialog(codebeamerPage);
	}

	@Action("redirectedToProjectDashboardPage")
	public ProjectDashboardPage redirectedToProjectDashboardPage() {
		return new ProjectDashboardPage(codebeamerPage).isActive();
	}
}
