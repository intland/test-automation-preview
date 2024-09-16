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

package com.intland.codebeamer.integration.classic.page.baseline;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class BaselineNavigation {

	private final CodebeamerPage codebeamerPage;

	private Project project;

	private Baseline leftBaseline;

	private Baseline rightBaseline;

	public BaselineNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public BaselineNavigation(CodebeamerPage codebeamerPage, Project project, Baseline leftBaseline, Baseline rightBaseline) {
		this.codebeamerPage = codebeamerPage;
		this.project = project;
		this.leftBaseline = leftBaseline;
		this.rightBaseline = rightBaseline;
	}

	@Action("redirectedToBaselineComparePage")
	public BaselineComparePage redirectedToBaselineComparePage() {
		return new BaselineComparePage(codebeamerPage, project, leftBaseline, rightBaseline).isActive();
	}
}
