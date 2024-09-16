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
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class TrackerItemImportPageNavigation {

	private final CodebeamerPage codebeamerPage;

	private final Tracker tracker;

	public TrackerItemImportPageNavigation(CodebeamerPage codebeamerPage, Tracker tracker) {
		this.codebeamerPage = codebeamerPage;
		this.tracker = tracker;
	}

	@Action("redirectedToMsExcelImport")
	public MsExcelAssignColumnPage redirectedToMsExcelImport() {
		return new MsExcelAssignColumnPage(codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToMsExcelCsvImport")
	public MsExcelAssignColumnPage redirectedToMsExcelCsvImport() {
		return new MsExcelAssignColumnPage(codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToMsWordTablesImport")
	public MsWordTablesAssignColumnPage redirectedToMsWordTablesImport() {
		return new MsWordTablesAssignColumnPage(codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToMsProjectImport")
	public MsProjectAssignColumnPage redirectedToMsProjectImport() {
		return new MsProjectAssignColumnPage(codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToMsWordImport")
	public MsWordImportPage redirectedToMsWordImport() {
		return new MsWordImportPage(codebeamerPage, tracker).isActive();
	}
}
