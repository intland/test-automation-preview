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

package com.intland.codebeamer.integration.classic.page.project.child.wiki;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.project.child.wiki.component.ProjectWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.project.child.wiki.component.ProjectWikiNewChildDialogFormComponent;
import com.intland.codebeamer.integration.classic.page.wiki.child.AbstractWikiNewChildDialog;

public class ProjectWikiNewChildDialog
		extends AbstractWikiNewChildDialog<ProjectWikiNewChildDialogFormComponent, ProjectWikiNewChildDialogFormAssertions> {

	private final ProjectWikiNewChildDialogFormComponent wikiNewChildDialogFormComponent;

	public ProjectWikiNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.wikiNewChildDialogFormComponent = new ProjectWikiNewChildDialogFormComponent(getCodebeamerPage(),
				this.getDialogLocator());
	}

	@Override
	public ProjectWikiNewChildDialog wikiNewChildDialogFormComponent(
			Consumer<ProjectWikiNewChildDialogFormComponent> formConsumer) {
		formConsumer.accept(this.wikiNewChildDialogFormComponent);
		return this;
	}

	@Override
	public ProjectWikiNewChildDialog assertWikiNewChildDialogFormComponent(
			Consumer<ProjectWikiNewChildDialogFormAssertions> assertion) {
		assertion.accept(wikiNewChildDialogFormComponent.assertThat());
		return this;
	}

	@Override
	public ProjectWikiNewChildDialogNavigation save() {
		this.wikiNewChildDialogFormComponent.save();
		return new ProjectWikiNewChildDialogNavigation(getCodebeamerPage());
	}

}
