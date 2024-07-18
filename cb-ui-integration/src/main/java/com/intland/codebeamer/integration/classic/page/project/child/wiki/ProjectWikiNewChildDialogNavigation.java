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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.project.ProjectWikiPage;
import com.intland.codebeamer.integration.classic.page.wiki.child.AbstractWikiNewChildDialogNavigation;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ProjectWikiNewChildDialogNavigation extends AbstractWikiNewChildDialogNavigation {

	public ProjectWikiNewChildDialogNavigation(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	@Action("redirectedToWikiNewChildDialog")
	public ProjectWikiNewChildDialog redirectedToWikiNewChildDialog() {
		return new ProjectWikiNewChildDialog(codebeamerPage);
	}

	@Action("redirectedToProjectWikiPage")
	public ProjectWikiPage redirectedToProjectWikiPage() {
		return new ProjectWikiPage(codebeamerPage).isActive();
	}

}
