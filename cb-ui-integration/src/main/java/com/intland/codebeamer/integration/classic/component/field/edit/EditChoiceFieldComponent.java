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

package com.intland.codebeamer.integration.classic.component.field.edit;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditChoiceFieldComponent extends AbstractCodebeamerComponent<EditChoiceFieldComponent, EditChoiceFieldAssertions> {

	private TrackerFieldApiService trackerFieldApiService;

	private Tracker tracker;

	private String fieldName;

	public EditChoiceFieldComponent(DataManagerService dataManagerService, CodebeamerPage codebeamerPage, Tracker tracker, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.trackerFieldApiService = dataManagerService.getTrackerFieldApiService();
		this.tracker = tracker;
		this.fieldName = fieldName;
	}

	public EditChoiceFieldComponent selectOption(String value) {
		getValueSelector().selectOption(String.valueOf(resolveIdByName(value)));
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue select[name$='.choiceFieldValues']");
	}
	
	public CodebeamerLocator getValueSelector() {
		return this.locator(" + td.fieldValue select[name$='.choiceFieldValues']");
	}

	@Override
	public EditChoiceFieldAssertions assertThat() {
		return new EditChoiceFieldAssertions(this);
	}
	
	protected int resolveIdByName(String value) {
		return trackerFieldApiService.resolveChoiceOptionId(this.tracker, this.fieldName, value);
	}

}
