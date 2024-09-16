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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.AbstractDashboardNewChildDialog;
import com.intland.codebeamer.integration.classic.page.project.child.dashboard.component.ProjectDashboardNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.project.child.dashboard.component.ProjectDashboardNewChildDialogFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class ProjectDashboardNewChildDialog
		extends
		AbstractDashboardNewChildDialog<ProjectDashboardNewChildDialogFormComponent, ProjectDashboardNewChildDialogFormAssertions> {

	@Component("New child wiki dialog")
	private final ProjectDashboardNewChildDialogFormComponent dashboardNewChildDialogFormComponent;

	public ProjectDashboardNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.dashboardNewChildDialogFormComponent = new ProjectDashboardNewChildDialogFormComponent(getCodebeamerPage(),
				this.getDialogLocator());
	}

	@Override
	public ProjectDashboardNewChildDialog dashboardNewChildDialogFormComponent(
			Consumer<ProjectDashboardNewChildDialogFormComponent> formConsumer) {
		formConsumer.accept(this.dashboardNewChildDialogFormComponent);
		return this;
	}

	@Override
	public ProjectDashboardNewChildDialog assertDashboardNewChildDialogFormComponent(
			Consumer<ProjectDashboardNewChildDialogFormAssertions> assertion) {
		assertion.accept(dashboardNewChildDialogFormComponent.assertThat());
		return this;
	}

	@Override
	public ProjectDashboardNewChildDialogNavigation save() {
		this.dashboardNewChildDialogFormComponent.save();
		return new ProjectDashboardNewChildDialogNavigation(getCodebeamerPage());
	}

}
