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
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordCenterPaneAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordCenterPaneComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordLeftTreeAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordLeftTreeComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordRightPaneFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.component.MsWordRightPaneFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("MsWordImportPage")
public class MsWordImportPage extends AbstractCodebeamerPage<MsWordImportPage> {

	@Component(value = "Ms Word import left tree", details = "Work item structure (parent-child relation) tree")
	private final MsWordLeftTreeComponent msWordLeftTreeComponent;

	@Component(value = "Ms Word import center pane", details = "Document View-like list of the work items to be imported")
	private final MsWordCenterPaneComponent msWordCenterPaneComponent;

	@Component(value = "Ms Word import right pane form", details = "Import rules and statistics")
	private final MsWordRightPaneFormComponent msWordRightPaneFormComponent;

	public MsWordImportPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.msWordLeftTreeComponent = new MsWordLeftTreeComponent(codebeamerPage);
		this.msWordCenterPaneComponent = new MsWordCenterPaneComponent(codebeamerPage);
		this.msWordRightPaneFormComponent = new MsWordRightPaneFormComponent(codebeamerPage);
	}

	@Override
	public MsWordImportPage isActive() {
		assertUrl(IMPORT_ISSUE_PAGE_PATTERN, "Ms Word import page should be the active page");
		return this;
	}

	public MsWordImportPage leftTreeComponent(Consumer<MsWordLeftTreeComponent> consumer) {
		consumer.accept(msWordLeftTreeComponent);
		return this;
	}

	public MsWordImportPage assertLeftTreeComponent(Consumer<MsWordLeftTreeAssertions> assertions) {
		assertions.accept(msWordLeftTreeComponent.assertThat());
		return this;
	}

	public MsWordImportPage centerPaneComponent(Consumer<MsWordCenterPaneComponent> consumer) {
		consumer.accept(msWordCenterPaneComponent);
		return this;
	}

	public MsWordImportPage assertCenterPaneComponent(Consumer<MsWordCenterPaneAssertions> assertions) {
		assertions.accept(msWordCenterPaneComponent.assertThat());
		return this;
	}

	public MsWordImportPage rightPaneFormComponent(Consumer<MsWordRightPaneFormComponent> formConsumer) {
		formConsumer.accept(msWordRightPaneFormComponent);
		return this;
	}

	public MsWordImportPage assertRightPaneFormComponent(Consumer<MsWordRightPaneFormAssertions> assertions) {
		assertions.accept(msWordRightPaneFormComponent.assertThat());
		return this;
	}

	public void save() {
		msWordRightPaneFormComponent.save();
	}
}
