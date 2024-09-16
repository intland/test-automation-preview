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

package com.intland.codebeamer.integration.classic.page.tracker.view.document.center;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerDocumentViewTableRowAssertions extends
		AbstractCodebeamerComponentAssert<TrackerDocumentViewTableRowComponent, TrackerDocumentViewTableRowAssertions> {

	protected TrackerDocumentViewTableRowAssertions(TrackerDocumentViewTableRowComponent component) {
		super(component);
	}

	public TrackerDocumentViewTableRowAssertions descriptionIs(final String description) {
		return assertAll("Description of the item should be '%s'".formatted(description),
				() -> assertThat(getComponent().getDescriptionInput()).hasText(description));
	}

	public TrackerDocumentViewTableRowAssertions nameIs(final String name) {
		return assertAll("Name of the item should be '%s'".formatted(name),
				() -> assertThat(getComponent().getNameInput()).hasText(name));
	}
}
