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

package com.intland.codebeamer.integration.classic.page.projectadmin.component.audittab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCheckboxFieldComponent;
import com.intland.codebeamer.integration.classic.page.projectadmin.AuditEntity;
import com.intland.codebeamer.integration.classic.page.projectadmin.component.AbstractProjectAdminPageTab;

public class ProjectAdminAuditTrailTabComponent extends AbstractProjectAdminPageTab<ProjectAdminAuditTrailTabComponent, ProjectAdminAuditTrailTabAssertions> {

	public static final String DATE_PATTERN = "yyyy/MM/dd";

	public static final String TRACKERS_CHECKBOX_SELECTOR = "ul:has(a:has-text('%s')) label:has(span:has-text('%s'))";

	public static final String ENTITY_CHECKBOX_SELECTOR = "ul.ui-multiselect-checkboxes label:has(span:has-text('%s'))";

	private final Project project;

	public ProjectAdminAuditTrailTabComponent(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage, "#project-admin");
		this.project = project;
	}

	public CodebeamerLocator getTrackerSelectorElement() {
		return this.locator("button#queryWidgetTrackerSelector_ms");
	}

	public CodebeamerLocator getEntitySelectorElement() {
		return this.locator("button#actionsEntityTypesSelector_ms");
	}

	public CodebeamerLocator getActionDurationSelector() {
		return this.locator("#actionsDuration");
	}

	public CodebeamerLocator getGoButton() {
		return this.locator("#actionsSubmit");
	}

	public ProjectAdminAuditTrailTabComponent selectEntityDropdown() {
		getEntitySelectorElement().click();
		return this;
	}

	public ProjectAdminAuditTrailTabComponent submit() {
		getGoButton().click();
		return new ProjectAdminAuditTrailTabComponent(getCodebeamerPage(), project);
	}

	public ProjectAdminAuditTrailTabComponent selectTrackers(Tracker tracker) {
		selectTrackerType();
		new EditCheckboxFieldComponent(getCodebeamerPage(), TRACKERS_CHECKBOX_SELECTOR.formatted(project.name(), tracker.name())).select(true);
		selectTrackerType();
		return this;
	}

	public ProjectAdminAuditTrailTabComponent deselectEntity(AuditEntity entityType) {
		selectEntityDropdown();
		new EditCheckboxFieldComponent(getCodebeamerPage(), ENTITY_CHECKBOX_SELECTOR.formatted(entityType.getValue())).select(false);
		selectEntityDropdown();
		return this;
	}

	public ProjectAdminAuditTrailTabComponent userSpecifiedAfterDate(Date date) {
		userSpecificAfterDateSelector(date);
		return this;
	}

	public ProjectAdminAuditTrailTabComponent userSpecifiedBeforeDate(Date date) {
		userSpecificBeforeDateSelector(date);
		return this;
	}

	public CodebeamerLocator getSearchResult() {
		return this.locator("div.tracker-configuration-history-content");
	}

	@Override
	public ProjectAdminAuditTrailTabAssertions assertThat() {
		return new ProjectAdminAuditTrailTabAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#project-admin-audit-trails-tab";
	}

	private ProjectAdminAuditTrailTabComponent userSpecificAfterDateSelector(Date date) {
		this.locator("input[name='actionsFrom']").fill(formatDate(date));
		return this;
	}

	private ProjectAdminAuditTrailTabComponent userSpecificBeforeDateSelector(Date date) {
		this.locator("input[name='actionsTo']").fill(formatDate(date));
		return this;
	}

	private ProjectAdminAuditTrailTabComponent selectTrackerType() {
		getTrackerSelectorElement().click();
		return this;
	}

	private String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
		return dateFormat.format(date);
	}
}