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

package com.intland.codebeamer.integration.classic.page.systemadmin.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class SystemAdminDashboardAssertions extends AbstractCodebeamerComponentAssert<SystemAdminDashboardComponent, SystemAdminDashboardAssertions> {

	protected SystemAdminDashboardAssertions(SystemAdminDashboardComponent component) {
		super(component);
	}

	public SystemAdminDashboardAssertions isReady() {
		return assertAll("System admin user groups link is ready", () -> {
			assertThat(getComponent().getUserGroupElement()).isVisible();
		});
	}
}
