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
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class StateTransitionComponent extends AbstractCodebeamerComponent<StateTransitionComponent, StateTransitionAssertions> {

	@Component("permitted selector dialog")
	private PermittedDialogComponent permittedDialog;
	private String from;
	private String to;
	private static final String SELECTOR = "tr.stateTransition.even:has(.fromStatus:has-text('%s')):has(.toStatus:has-text('%s'))";

	public StateTransitionComponent(CodebeamerPage codebeamerPage, String from, String to) {
		super(codebeamerPage, SELECTOR.formatted(from, to));
		this.permittedDialog = new PermittedDialogComponent(codebeamerPage, "div.ui-multiselect-menu.transition-permission");
		this.from = from;
		this.to = to;
	}

	public PermittedDialogComponent openPermittedDialog() {
		this.locator("td.permissions span").click();
		return this.permittedDialog;
	}

	public PermittedDialogComponent getPermittedDialog() {
		return this.permittedDialog;
	}

	public StateTransitionAssertions assertThat() {
		return new StateTransitionAssertions(this);
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getPermittedValue() {
		return this.locator("td.permissions span").text();
	}

	public String getNameValue() {
		return this.locator("a.transitionLink").text();
	}
}
