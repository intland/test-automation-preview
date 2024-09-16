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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EscalationTableComponent extends AbstractCodebeamerComponent<EscalationTableComponent, EscalationTableAssertions> {

	private final TrackerFieldApiService trackerFieldApiService;

	private final Tracker tracker;

	public EscalationTableComponent(CodebeamerPage codebeamerPage, String escalationView, Tracker tracker) {
		super(codebeamerPage, "li.escalation:has(a:has-text('%s'))".formatted(escalationView));
		this.trackerFieldApiService = codebeamerPage.getDataManagerService().getTrackerFieldApiService();
		this.tracker = tracker;
	}

	public EscalationTableComponent setOffset(int level, String offset) {
		getOffsetElement(level).fill(offset);
		return this;
	}

	public EscalationTableComponent selectUnit(int level, EscalationUnit unit) {
		getUnitElement(level).selectOption(unit.getValue());
		return this;
	}

	public EscalationTableComponent selectAnchor(int level, EscalationAnchor anchor) {
		getAnchorElement(level).selectOption(anchor.name());
		return this;
	}

	public EscalationTableComponent selectField(int level, String field) {
		getFieldElement(level).selectOption(field);
		return this;
	}

	public EscalationTableComponent selectStatus(int level, String status) {
		getStatusElement(level).selectOption(status);
		return this;
	}

	public EscalationTableComponent addNewEscalationRule() {
		this.locator(".addRule").click();
		return this;
	}

	public EscalationTableComponent addAfterLastEscalationRule() {
		this.locator(".addLastRule").click();
		return this;
	}

	public EscalationTableComponent deleteEscalationView() {
		this.locator(".deleteRule").click();
		return this;
	}

	public CodebeamerLocator getStatusElement(int level) {
		return getTableRowElement(level).concat("select[name=status]");
	}

	@Override
	public EscalationTableAssertions assertThat() {
		return new EscalationTableAssertions(this);
	}

	protected int resolveStatusIdByName(String status) {
		return trackerFieldApiService.resolveChoiceOptionId(tracker, "Status", status);
	}

	private CodebeamerLocator getOffsetElement(int level) {
		return getTableRowElement(level).concat("input[name=offset]");
	}

	private CodebeamerLocator getUnitElement(int level) {
		return getTableRowElement(level).concat("select[name=unit]");
	}

	private CodebeamerLocator getAnchorElement(int level) {
		return getTableRowElement(level).concat("select[name=anchor]");
	}

	private CodebeamerLocator getFieldElement(int level) {
		return getTableRowElement(level).concat("select[name=field]");
	}

	private CodebeamerLocator getTableRowElement(int level) {
		return this.locator("tr:has(td.level:has-text('%d'))".formatted(Integer.valueOf(level)));
	}
}
