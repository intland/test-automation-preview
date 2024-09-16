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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class StateTransitionDialogComponent extends AbstractCodebeamerComponent<StateTransitionDialogComponent, StateTransitionDialogAssertions> {

	public StateTransitionDialogComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	@Override
	public StateTransitionDialogAssertions assertThat() {
		return new StateTransitionDialogAssertions(this);
	}

	public StateTransitionDialogComponent setFrom(String from) {
		this.locator(".transitionEditor .fromStatus select[name='fromStatusId']").selectOption(from);
		return this;
	}

	public StateTransitionDialogComponent setTo(String from) {
		this.locator(".transitionEditor .toStatus select[name='toStatusId']").selectOption(from);
		return this;
	}

	public StateTransitionDialogComponent setName(String name) {
		this.locator(".transitionEditor input[name='name']").fill(name);
		return this;
	}

	public StateTransitionDialogComponent save() {
		this.locator("button:has-text('OK')").click();
		return this;
	}

}
