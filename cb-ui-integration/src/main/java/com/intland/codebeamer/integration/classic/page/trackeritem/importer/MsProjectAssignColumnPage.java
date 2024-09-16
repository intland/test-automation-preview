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
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsProjectAssignColumnFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsProjectAssignColumnFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("MsProjectAssignColumnPage")
public class MsProjectAssignColumnPage
		extends AssignColumnPage<MsProjectAssignColumnFormComponent, MsProjectAssignColumnFormAssertions> {

	public MsProjectAssignColumnPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, new MsProjectAssignColumnFormComponent(codebeamerPage), tracker);
	}

	@Override
	public MsProjectAssignColumnPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Ms Project assign column page should be the active page");
		return this;
	}
}
