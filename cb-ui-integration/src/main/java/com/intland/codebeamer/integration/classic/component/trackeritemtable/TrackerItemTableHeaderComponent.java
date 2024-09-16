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

package com.intland.codebeamer.integration.classic.component.trackeritemtable;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.trackerheaderfilter.TrackerHeaderFilterComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemTableHeaderComponent extends
		AbstractCodebeamerComponent<TrackerItemTableHeaderComponent, TrackerItemTableHeaderAssertions> {

	public TrackerItemTableHeaderComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#trackerItems thead");
	}

	@Override
	public TrackerItemTableHeaderAssertions assertThat() {
		return new TrackerItemTableHeaderAssertions(this);
	}

	public String getHeaderIndexByFieldName(String fieldName) {
		List<CodebeamerLocator> headerItemLocators = getHeaderItemElements().all();
		for (CodebeamerLocator locator : headerItemLocators) {
			if (locator.getAttribute("title").equals(fieldName)) {
				return locator.getAttribute("data-fieldlayoutid");
			}
		}
		throw new IllegalStateException("Field has not found");
	}

	public TrackerHeaderFilterComponent openColumnPopup(String fieldName) {
		getFieldMoreMenuElement(fieldName).click();
		return new TrackerHeaderFilterComponent(getCodebeamerPage());
	}

	private CodebeamerLocator getHeaderItemElements() {
		return this.locator(".textData");
	}

	private CodebeamerLocator getFieldMoreMenuElement(String fieldName) {
		return this.locator("th[title='%s'] .menu-trigger".formatted(fieldName));
	}
}
