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

package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import static org.testng.Assert.assertTrue;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemActionBarAssertions extends AbstractCodebeamerComponentAssert<TrackerItemActionBarComponent, TrackerItemActionBarAssertions> {

	protected TrackerItemActionBarAssertions(TrackerItemActionBarComponent component) {
		super(component);
	}

	public TrackerItemActionBarAssertions isEmpty() {
		return assertAll("Should not contain any elements",
				() -> assertTrue(getComponent().hasNoChild())
		);
	}

	public TrackerItemActionBarAssertions editButtonIsHidden() {
		return assertAll("Edit button should not be visible",
				() -> assertThat(getComponent().getEditItemLink()).isHidden());
	}
}
