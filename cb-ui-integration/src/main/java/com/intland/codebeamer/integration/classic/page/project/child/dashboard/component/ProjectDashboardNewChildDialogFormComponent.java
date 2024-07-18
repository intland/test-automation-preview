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

package com.intland.codebeamer.integration.classic.page.project.child.dashboard.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.component.AbstractDashboardNewChildDialogFormComponent;

public class ProjectDashboardNewChildDialogFormComponent
		extends AbstractDashboardNewChildDialogFormComponent {

	public ProjectDashboardNewChildDialogFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator);
	}

	@Override
	public ProjectDashboardNewChildDialogFormAssertions assertThat() {
		return new ProjectDashboardNewChildDialogFormAssertions(this);
	}

}
