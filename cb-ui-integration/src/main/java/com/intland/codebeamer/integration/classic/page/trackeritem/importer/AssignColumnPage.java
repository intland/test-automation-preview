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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.AssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.AssignColumnFormComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public abstract class AssignColumnPage<C extends AssignColumnFormComponent<C, A>, A extends AssignColumnFormAssertions<C, A>>
		extends AbstractCodebeamerPage<AssignColumnPage<C, A>> {

	private final C formComponent;

	private final Tracker tracker;

	public static final Pattern IMPORT_ISSUE_PAGE_PATTERN = Pattern.compile(".*/importIssue.spr\\?execution=.*");

	public AssignColumnPage(CodebeamerPage codebeamerPage, C formComponent, Tracker tracker) {
		super(codebeamerPage);
		this.formComponent = formComponent;
		this.tracker = tracker;
	}

	public AssignColumnPage<C, A> assignColumnFormComponent(Consumer<C> formConsumer) {
		formConsumer.accept(formComponent);
		return this;
	}

	public AssignColumnPage<C, A> assertAssignColumnFormComponent(Consumer<A> assertions) {
		assertions.accept(formComponent.assertThat());
		return this;
	}

	public TrackerItemImportPage back() {
		formComponent.selectBack();
		return new TrackerItemImportPage(getCodebeamerPage(), tracker);
	}

	public FieldConversionConfigurationPage next() {
		formComponent.selectNext();
		return new FieldConversionConfigurationPage(getCodebeamerPage(), tracker);
	}
}
