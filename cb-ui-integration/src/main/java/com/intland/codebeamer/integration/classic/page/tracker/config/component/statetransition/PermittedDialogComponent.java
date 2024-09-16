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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCheckboxFieldComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class PermittedDialogComponent extends AbstractCodebeamerComponent<PermittedDialogComponent, PermittedDialogAssertions> {

	// UI-AUTOMATION: add proper data-testid to both of these selectors
	public static final String PARTICIPANT_CHECKBOX_SELECTOR = "ul:has(a:has-text('Participants')) li.field label:has(span:has-text('%s'))";
	public static final String ROLE_CHECKBOX_SELECTOR = "ul:has(a:has-text('Roles')) li.role label:has(span:has-text('%s'))";

	protected PermittedDialogComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	public PermittedDialogComponent selectAll() {
		// UI-AUTOMATION: add proper data-testid to it
		this.locator("a.ui-multiselect-all:has-text('All')").click();
		return this;
	}

	public PermittedDialogComponent selectNoOne() {
		// UI-AUTOMATION: add proper data-testid to it
		this.locator("a.ui-multiselect-none:has-text('No one')").click();
		return this;
	}

	public PermittedDialogComponent save() {
		// UI-AUTOMATION: add proper data-testid to it
		this.locator("button:has-text('OK')").click();
		return this;
	}

	public PermittedDialogComponent cancel() {
		// UI-AUTOMATION: add proper data-testid to it
		this.locator("button:has-text('Cancel')").click();
		return this;
	}

	public PermittedDialogComponent close() {
		// UI-AUTOMATION: add proper data-testid to it
		this.locator("a.ui-multiselect-close").click();
		return this;
	}

	public PermittedDialogComponent selectParticipant(String participant) {
		new EditCheckboxFieldComponent(getCodebeamerPage(), PARTICIPANT_CHECKBOX_SELECTOR.formatted(participant)).select(true);
		return this;
	}

	public PermittedDialogComponent deselectParticipant(String participant) {
		new EditCheckboxFieldComponent(getCodebeamerPage(), PARTICIPANT_CHECKBOX_SELECTOR.formatted(participant)).select(false);
		return this;
	}

	public PermittedDialogComponent selectRole(String role) {
		new EditCheckboxFieldComponent(getCodebeamerPage(), ROLE_CHECKBOX_SELECTOR.formatted(role)).select(true);
		return this;
	}

	public PermittedDialogComponent deselectRole(String role) {
		new EditCheckboxFieldComponent(getCodebeamerPage(), ROLE_CHECKBOX_SELECTOR.formatted(role)).select(false);
		return this;
	}

	public CodebeamerLocator getLocator() {
		return this.selfLocator();
	}

	@Override
	public PermittedDialogAssertions assertThat() {
		return new PermittedDialogAssertions(this);
	}
}
