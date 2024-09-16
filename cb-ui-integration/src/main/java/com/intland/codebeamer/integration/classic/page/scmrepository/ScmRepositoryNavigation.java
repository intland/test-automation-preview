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

package com.intland.codebeamer.integration.classic.page.scmrepository;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public class ScmRepositoryNavigation {

	private CodebeamerPage codebeamerPage;

	public ScmRepositoryNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToRepositoryCreatePage")
	public ScmRepositoryCreatePage redirectedToRepositoryCreatePage(Project project) {
		return new ScmRepositoryCreatePage(codebeamerPage, project).isActive();
	}

	@Action("redirectedToRepositoryItemPage")
	public ScmRepositoryItemPage redirectedToRepositoryItemPage() {
		return new ScmRepositoryItemPage(codebeamerPage).isActive();
	}

}
