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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class MsWordRightPaneFormAssertions
		extends AbstractCodebeamerComponentAssert<MsWordRightPaneFormComponent, MsWordRightPaneFormAssertions> {

	MsWordRightPaneFormAssertions(MsWordRightPaneFormComponent component) {
		super(component);
	}

	/**
	 * @param trackerId <ul>
	 *                  <li>0 to ignore</li>
	 *                  <li>1 to import into the current tracker</li>
	 *                  <li>2 to append to parent</li>
	 *                  <li>otherwise, the ID of the tracker</li>
	 *                  </ul>
	 * @param count     value of the counter
	 */
	public MsWordRightPaneFormAssertions hasStatistic(int trackerId, int count) {
		return assertAll("Statistics row with id '%d' and value '%d', should be visible)".formatted(Integer.valueOf(trackerId),
						Integer.valueOf(count)),
				() -> assertThat(getComponent().getStatisticRowById(trackerId)).hasText(Integer.toString(count)));
	}
}
