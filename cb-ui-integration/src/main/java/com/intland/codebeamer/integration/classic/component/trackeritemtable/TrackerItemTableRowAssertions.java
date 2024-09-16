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

import static com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemTableRowComponent.DATA_TT_ID;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemTableRowAssertions
		extends AbstractCodebeamerComponentAssert<TrackerItemTableRowComponent, TrackerItemTableRowAssertions> {

	public TrackerItemTableRowAssertions(TrackerItemTableRowComponent component) {
		super(component);
	}

	public TrackerItemTableRowAssertions is(TrackerItem trackerItem) {
		return assertAll("Given tracker item is not: %s"
						.formatted(trackerItem.name()),
				() -> {
					assertThat(getComponent().getLocator()).hasAttribute(DATA_TT_ID, String.valueOf(trackerItem.id().id()));
					assertThat(getComponent().getSummaryElement())
							.containsText("-%s] %s".formatted(trackerItem.id().id(), trackerItem.name()));
				});
	}

}
