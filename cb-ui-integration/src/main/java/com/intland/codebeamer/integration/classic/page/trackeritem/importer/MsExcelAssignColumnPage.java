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
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsExcelAssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsExcelAssignColumnFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("MsExcelAssignColumnPage")
public class MsExcelAssignColumnPage
		extends AssignColumnPage<MsExcelAssignColumnFormComponent, MsExcelAssignColumnFormAssertions> {

	public MsExcelAssignColumnPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, new MsExcelAssignColumnFormComponent(codebeamerPage), tracker);
	}

	@Override
	public MsExcelAssignColumnPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Ms Excel assign column page should be the active page");
		return this;
	}
}