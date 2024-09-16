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

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("BaselineLandingPage")
public class BaselineLandingPage extends AbstractBaselinePage<BaselineLandingPage> {

	private static final String BASELINE_PAGE_URL = ".*/project/%s/baselines";

	public BaselineLandingPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, project);
	}

	public BaselineLandingPage(CodebeamerPage codebeamerPage, Project project, Baseline leftBaseline, Baseline rightBaseline) {
		super(codebeamerPage, project, leftBaseline, rightBaseline);
	}

	protected Pattern getPath() {
		return Pattern.compile(BASELINE_PAGE_URL.formatted(project.id().id()));
	}
}
