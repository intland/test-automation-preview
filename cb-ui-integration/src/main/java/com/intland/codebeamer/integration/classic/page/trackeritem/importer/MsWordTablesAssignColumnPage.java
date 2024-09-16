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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordTablesAssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordTablesAssignColumnFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("MsWordTablesAssignColumnPage")
public class MsWordTablesAssignColumnPage
		extends AssignColumnPage<MsWordTablesAssignColumnFormComponent, MsWordTablesAssignColumnFormAssertions> {

	public MsWordTablesAssignColumnPage(CodebeamerPage page, Tracker tracker) {
		super(page, new MsWordTablesAssignColumnFormComponent(page), tracker);
	}

	@Override
	public MsWordTablesAssignColumnPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Ms Word tables assign column page should be the active page");
		return this;
	}
}
