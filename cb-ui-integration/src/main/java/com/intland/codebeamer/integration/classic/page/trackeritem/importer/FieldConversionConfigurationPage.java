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

import static com.intland.codebeamer.integration.classic.page.trackeritem.importer.AssignColumnPage.IMPORT_ISSUE_PAGE_PATTERN;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.FieldConversionConfigurationFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.FieldConversionConfigurationFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("FieldConversionConfigurationPage")
public class FieldConversionConfigurationPage extends AbstractCodebeamerPage<FieldConversionConfigurationPage> {

	@Component("Field conversion form")
	private final FieldConversionConfigurationFormComponent fieldConversionConfigurationFormComponent;

	private final Tracker tracker;

	public FieldConversionConfigurationPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.tracker = tracker;
		fieldConversionConfigurationFormComponent = new FieldConversionConfigurationFormComponent(codebeamerPage);
	}

	@Override
	public FieldConversionConfigurationPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Field conversion configuration page should be the active page");
		return this;
	}

	public FieldConversionConfigurationPage fieldConversionConfigurationFormComponent(
			Consumer<FieldConversionConfigurationFormComponent> formConsumer) {
		formConsumer.accept(fieldConversionConfigurationFormComponent);
		return this;
	}

	public FieldConversionConfigurationPage assertFieldConversionConfigurationFormComponent(
			Consumer<FieldConversionConfigurationFormAssertions> assertions) {
		assertions.accept(fieldConversionConfigurationFormComponent.assertThat());
		return this;
	}

	public TrackerItemImportPage back() {
		fieldConversionConfigurationFormComponent.getBackLocator().click();
		return new TrackerItemImportPage(getCodebeamerPage(), tracker);
	}

	public TrackerItemImportPreviewPage next() {
		fieldConversionConfigurationFormComponent.getNextLocator().click();
		return new TrackerItemImportPreviewPage(getCodebeamerPage());
	}
}
