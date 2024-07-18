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

import java.util.List;

import org.testng.Assert;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditReferenceComponent extends AbstractCodebeamerComponent<EditReferenceComponent, EditReferenceAssertions> {

	private EditReferenceDialogComponent editReferenceDialogComponent;

	public EditReferenceComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.editReferenceDialogComponent = new EditReferenceDialogComponent(codebeamerPage);
	}

	public EditReferenceComponent select(String referenceName) {
		getValueField().doubleClick().type(referenceName);
		this.editReferenceDialogComponent.select(referenceName);
		return this;
	}

	public EditReferenceComponent select(String referenceName, int position) {
		int selectedItemsCount = getSelectedValues().count();
		Assert.assertTrue((selectedItemsCount >= position), "Selected position should be lower than the list length");
		
		getValueField()
			.doubleClick()
			.pressControlLeft((selectedItemsCount - (position + 1)) * 2)
			.type(referenceName);
		this.editReferenceDialogComponent.select(referenceName);
		return this;
	}

	public CodebeamerLocator getSelectedValues() {
		return this.locator(" + td.fieldValue ul.token-input-list-facebook li");
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}

	public CodebeamerLocator getFieldReferenceHiddenInput() {
		return this.locator(
				" + td.fieldValue [id^='dynamicChoice_references']:not([id^='dynamicChoice_references_autocomplete_editor'])");
	}

	public String getFieldReferenceHiddenInputValue() {
		return this.getFieldReferenceHiddenInput().getAttribute("value");
	}

	@Override
	public EditReferenceAssertions assertThat() {
		return new EditReferenceAssertions(this);
	}

	protected String createTrackerItemAssertionRegex(List<TrackerItem> trackerItems) {
		StringBuilder assertString = new StringBuilder();
		for (int i = 0; i < trackerItems.size(); ++i) {
			assertString.append(ArtifactType.TRACKER_ITEM.getValue()).append("-").append(trackerItems.get(i).id().id());
			assertString.append((i < trackerItems.size() - 1) ? ".*," : ".*");
		}
		return assertString.toString();
	}
}
