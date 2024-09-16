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

package com.intland.codebeamer.integration.classic.page.baseline.dialog.child;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public abstract class AbstractBaselineSelectDialogNavigation<T> {
	protected final CodebeamerPage codebeamerPage;

	public AbstractBaselineSelectDialogNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToBaselinePage")
	public abstract T redirectedToBaselinePage(Project project);

	@Action("redirectedToBaselinePage")
	public abstract T redirectedToBaselinePage(Project project, Baseline leftBaseline, Baseline rightBaseline);
}
