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

package com.intland.codebeamer.integration.classic.page.projectadmin.component.audittab;

import com.intland.codebeamer.integration.classic.page.projectadmin.component.AbstractProjectAdminPageAssertions;

public class ProjectAdminAuditTrailTabAssertions extends AbstractProjectAdminPageAssertions<ProjectAdminAuditTrailTabComponent, ProjectAdminAuditTrailTabAssertions> {

	protected ProjectAdminAuditTrailTabAssertions(ProjectAdminAuditTrailTabComponent component) {
		super(component);
	}

	public ProjectAdminAuditTrailTabAssertions isReady() {
		return assertAll("Project admin form is ready", () -> {
			assertThat(getComponent().getTrackerSelectorElement()).isEditable();
			assertThat(getComponent().getEntitySelectorElement()).isEditable();
			assertThat(getComponent().getActionDurationSelector()).isEditable();
			assertThat(getComponent().getGoButton()).isVisible();
		});
	}

	public ProjectAdminAuditTrailTabAssertions isSearchResultReady() {
		return assertAll("Search result is ready", () -> {
			assertThat(getComponent().getSearchResult()).isVisible();
		});
	}
}