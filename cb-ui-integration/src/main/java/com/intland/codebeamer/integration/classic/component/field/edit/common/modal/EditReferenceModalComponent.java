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

package com.intland.codebeamer.integration.classic.component.field.edit.common.modal;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.reportselector.ReportSelectorComponent;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemsTableComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditReferenceModalComponent
		extends AbstractCodebeamerComponent<EditReferenceModalComponent, EditReferenceModalAssertions> {

	private static final String FRAME_LOCATOR = "#inlinedPopupIframe[src*='/proj/tracker/selectReference.spr']";

	@Component("Report selector")
	private final ReportSelectorComponent reportSelectorComponent;

	@Component("Tracker Items table")
	private final TrackerItemsTableComponent trackerItemsTable;

	public EditReferenceModalComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, FRAME_LOCATOR, ".insideInlinedPopup");
		this.reportSelectorComponent = new ReportSelectorComponent(getCodebeamerPage(), FRAME_LOCATOR);
		this.trackerItemsTable = new TrackerItemsTableComponent(getCodebeamerPage(), FRAME_LOCATOR);
	}

	@Override
	public EditReferenceModalAssertions assertThat() {
		return new EditReferenceModalAssertions(this);
	}

	public EditReferenceModalComponent reportSelector(Consumer<ReportSelectorComponent> consumer) {
		consumer.accept(reportSelectorComponent);
		return this;
	}

	public EditReferenceModalComponent trackerItemsTable(Consumer<TrackerItemsTableComponent> consumer) {
		consumer.accept(trackerItemsTable);
		return this;
	}

	protected CodebeamerLocator getEditReferenceModal() {
		return selfLocator();
	}

}
