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
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
@Page("ScmRepositoryItemPage")
public class ScmRepositoryItemPage extends AbstractCodebeamerPage<ScmRepositoryItemPage> {

	private static final String SCM_REPOSITORY_ITEM_PATH = "repository.spr" ;

	private static final Pattern SCM_REPOSITORY_ITEM_PATTERN = Pattern.compile(".*/repository/.*");

	public ScmRepositoryItemPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Visit")
	public ScmRepositoryItemPage visit() {
		navigate(String.format(SCM_REPOSITORY_ITEM_PATH));
		return isActive();
	}

	@Override
	public ScmRepositoryItemPage isActive() {
		assertUrl(SCM_REPOSITORY_ITEM_PATTERN, "Scm Repositories Item page should be the active page");
		return this;
	}

}
