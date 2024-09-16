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

package com.intland.codebeamer.integration.classic.page.baseline.dialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.baseline.BaselineLandingPage;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.child.AbstractBaselineSelectDialogNavigation;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class BaselineSelectDialogNavigation extends AbstractBaselineSelectDialogNavigation {
	public BaselineSelectDialogNavigation(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	@Action("redirectedToBaselineLandingPage")
	public BaselineLandingPage redirectedToBaselinePage(Project project) {
		return new BaselineLandingPage(codebeamerPage, project).isActive();
	}

	@Override
	@Action("redirectedToBaselineLandingPage")
	public BaselineLandingPage redirectedToBaselinePage(Project project, Baseline leftBaseline, Baseline rightBaseline) {
		return new BaselineLandingPage(codebeamerPage, project, leftBaseline, rightBaseline).isActive();
	}
}
