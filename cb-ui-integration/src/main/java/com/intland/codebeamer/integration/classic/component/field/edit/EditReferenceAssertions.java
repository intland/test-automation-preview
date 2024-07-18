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

import com.beust.jcommander.internal.Lists;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditReferenceAssertions extends AbstractCodebeamerComponentAssert<EditReferenceComponent, EditReferenceAssertions> {

	protected EditReferenceAssertions(EditReferenceComponent component) {
		super(component);
	}
	
	public EditReferenceAssertions is(TrackerItem... trackerItems) {
		return is(Lists.newArrayList(trackerItems));
	}
	
	public EditReferenceAssertions is(List<TrackerItem> trackerItems) {
		String expectedHiddenInputValueRegex = getComponent().createTrackerItemAssertionRegex(trackerItems);
		return assertAll("Reference Editor value should match with the following pattern: %s".formatted(expectedHiddenInputValueRegex),
				() -> {
					assertThat(getComponent().getFieldReferenceHiddenInput()).not().isEmpty();
					Assert.assertTrue(getComponent().getFieldReferenceHiddenInputValue().matches(expectedHiddenInputValueRegex));
				});
	}
}
