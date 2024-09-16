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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.statetransition;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class PermittedDialogAssertions extends
		AbstractCodebeamerComponentAssert<PermittedDialogComponent, PermittedDialogAssertions> {

	protected PermittedDialogAssertions(PermittedDialogComponent component) {
		super(component);
	}

	public PermittedDialogAssertions isVisible() {
		return assertAll("The Permitted modal should be visible", () -> assertThat(getComponent().getLocator()).isVisible());
	}

	public PermittedDialogAssertions isHidden() {
		return assertAll("The Permitted modal should be hidden", () -> assertThat(getComponent().getLocator()).isHidden());
	}

}
