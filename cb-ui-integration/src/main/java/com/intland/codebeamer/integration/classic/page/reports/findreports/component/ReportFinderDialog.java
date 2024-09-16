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

package com.intland.codebeamer.integration.classic.page.reports.findreports.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class ReportFinderDialog extends AbstractCodebeamerDialog {

	@Component("Report Finder Dialog Component")
	private final ReportFinderDialogComponent reportFinderDialogComponent;

	public ReportFinderDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='/proj/queries/picker.spr']");
		this.reportFinderDialogComponent = new ReportFinderDialogComponent(codebeamerPage, this.getDialogLocator());
	}

	public ReportFinderDialog reportFinderDialogComponent(Consumer<ReportFinderDialogComponent> formConsumer) {
		formConsumer.accept(reportFinderDialogComponent);
		return this;
	}
}
